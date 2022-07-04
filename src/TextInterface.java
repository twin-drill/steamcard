import java.util.HashMap;
import java.util.Scanner;

public class TextInterface {
    private static HashMap<Integer, SteamApp> apps;
    private static Scanner s = new Scanner(System.in);

    public static void mainloop() {
        System.out.print("Menu \\ 1 \\ Search for App" +
                "\n\t \\ 2 \\ Update Definitions" +
                "\n\t \\ 3 \\ Quit" +
                "\n\t \\ ");
        switch(s.nextInt()) {
            case 1:
                System.out.print("\t \\ - \\ Enter AppID" +
                        "\n\t \\ ");
                System.out.println(apps.get(s.nextInt()));
                break;
            case 2:
                System.out.print("Upd\t \\ 1 \\ From Local Cache" +
                        "\n\t \\ 2 \\ From Internet" +
                        "\n\t \\ ");
                switch(s.nextInt()) {
                    case 1:
                        apps = Cache.readCache();
                        break;
                    case 2:
                        createFullCache();
                        break;
                }
                break;
            case 3:
                System.exit(0);
        }

        mainloop();
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
