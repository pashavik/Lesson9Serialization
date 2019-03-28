import java.io.*;
import java.util.Map;

public class CacheSerialazier {
    public static void  serialize(Map<Args, Object> cache, String fileName) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
        outputStream.writeObject(cache);
    }
    public static Map<Args, Object> deSerialize(String fileName) throws ClassNotFoundException, IOException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Map<Args, Object>) inputStream.readObject();
        }
    }
}