import java.io.*;
import java.util.ArrayList;

public class Cache {
    public static void buildCache(ArrayList<SteamApp> arr) {
        try {
            FileOutputStream fos = new FileOutputStream("cache.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(arr);
            oos.flush();
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static ArrayList<SteamApp> readCache() {
        try {
            FileInputStream fileInputStream = new FileInputStream("cache.dat");
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            ArrayList<SteamApp> apps = (ArrayList<SteamApp>) ois.readObject();
            ois.close();
            fileInputStream.close();
            return apps;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
