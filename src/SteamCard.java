import java.util.HashMap;

public class SteamCard {

    private static HashMap<Integer, SteamApp> apps;

    public static void main(String[] args) {

    }
    public static void createFullCache() {
        Parser p = new Parser();
        Connection[] c = new Connection[] {
                new Connection("https://www.steamcardexchange.net/api/request.php?GetBoosterPrices"),
                new Connection("https://www.steamcardexchange.net/api/request.php?GetFoilBadgePrices_Member"),
                new Connection("https://www.steamcardexchange.net/api/request.php?GetBadgePrices_Member") };

        for (Connection conn : c) { conn.open();}
        apps = p.parseAll(c[0].get(), c[1].get(), c[2].get());
        for (Connection conn : c) { conn.close();}

        Cache.buildCache(apps);
    }
}
