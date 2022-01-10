package handler;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import model.PropertyKey;
import model.exception.ResourceNotFoundException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

@AllArgsConstructor
public class PropertyHandler {

    private Properties applicationProperties;

    private static final String HDFS_FILE_SYSTEM_PREFIX = "hdfs://";

    public static PropertyHandler getPropertiesHandlerFromFile(@NonNull String propertyFilePath){
        Properties properties= new Properties();
        try (InputStream propertyStream = getPropertyFileInputStream(propertyFilePath)){
            properties.load(propertyStream);
        } catch (IOException e) {
            throw new ResourceNotFoundException("Can't open property file at" + propertyFilePath,e);
        }
        return new PropertyHandler(properties);
    }

    public String getProperty(@NonNull PropertyKey propertyKey){
        return Objects.requireNonNull(applicationProperties.getProperty(propertyKey.value), "Missing property key :" + propertyKey);
    }

    private static InputStream getPropertyFileInputStream(@NonNull String propertyFilePath) throws IOException {
        FileSystem fileSystem = getFileSystem();
        if (propertyFilePath.startsWith(HDFS_FILE_SYSTEM_PREFIX)){
            return fileSystem.open(new Path(propertyFilePath));
        }
        return new FileInputStream(new File(propertyFilePath));
    }

    private static FileSystem getFileSystem() {
        try {
            return FileSystem.get(new Configuration());
        }catch (IOException e){
            throw new IllegalStateException("A problem occured while getting file system", e);
        }
    }
}
