import java.io.*;
import java.util.HashMap;

public class Cache {
    public static void buildCache(HashMap<Integer, SteamApp> map) {
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

    }
    public static HashMap<Integer, SteamApp> readCache() {
        try {
            FileInputStream fileInputStream = new FileInputStream("cache.dat");
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            HashMap<Integer, SteamApp> apps = (HashMap<Integer, SteamApp>) ois.readObject();
            ois.close();
            fileInputStream.close();
            return apps;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
