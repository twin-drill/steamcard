import java.util.ArrayList;

public class SteamCard {

    private static ArrayList<SteamApp> apps;

    public static void main(String[] args) {
        readCache();
        System.out.println(apps);
    }

    public static void createCache() {
        Connection booster = new Connection("https://www.steamcardexchange.net/api/request.php?GetBoosterPrices");
        Parser parser = new Parser();
        booster.open();
        apps = parser.createBoosterDict(booster.get());
        booster.close();
        Cache.buildCache(apps);
    }
    public static void readCache() {
        apps = Cache.readCache();
    }
}
