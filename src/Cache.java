import java.io.*;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class Cache {

    private static final Logger log = LogManager.getLogger("Cache");
    public static void buildCache(HashMap<Integer, SteamApp> map) {
        log.trace("Building Cache...");
        try {
            FileOutputStream fos = new FileOutputStream("cache.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Created Cache, Loaded Data.");
        out.outb("cache created successfully, data loaded.");
    }
    public static HashMap<Integer, SteamApp> readCache() throws FileNotFoundException {
        log.trace("Reading Cache");
        out.outb("building map...");
        FileInputStream fileInputStream = new FileInputStream("cache.dat");
        try {
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            HashMap<Integer, SteamApp> apps = (HashMap<Integer, SteamApp>) ois.readObject();
            ois.close();
            fileInputStream.close();
            out.outb("data loaded.");
            log.info("Loaded Data from Cache.");
            return apps;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
