import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Parser {
    private ArrayList<SteamApp> apps;

    public Parser() {
        apps = new ArrayList<SteamApp>();
    }

    public ArrayList<SteamApp> createBoosterDict(String data) {
        SteamApp temp;
        Scanner s = new Scanner(data).useLocale(Locale.US);

        s.useDelimiter("\\[\\[");
        s.next();
        while (s.hasNext()) {
            temp = new SteamApp();
            // Change delimiter to inside-app delimiter
            s.useDelimiter("\"");
            // Discard brackets
            s.next();

            // Parse data, throw away midsegments
            temp.id = s.nextInt();
            s.next();
            temp.name = s.next();
            s.next();
            temp.numCards = s.nextInt();
            s.next();
            temp.boosterPrice = Double.parseDouble(s.next().substring(1));
            temp.boosterRatio = Integer.parseInt(s.next().substring(2).replaceFirst(".$", ""));
            temp.threeCard = s.nextDouble();
            temp.gemRatio = Integer.parseInt(s.next().substring(3).replaceFirst(".$", ""));
            temp.gemPrice = s.nextDouble();
            s.useDelimiter("\\]");
            s.next();
            temp.lastUpdated = Long.parseLong(s.next().substring(2).replaceFirst(".$", ""));
            s.useDelimiter("\\[\\[");
            apps.add(temp);
            s.next();
            System.out.print("Parsed app " + apps.size() + "\r");
        }
        System.out.println();
        return apps;
    }

    public ArrayList<SteamApp> updateFoilValues(String data) {

    }

    //TODO write code for foil and badge data



}
