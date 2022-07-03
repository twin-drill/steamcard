import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class Parser {
    private HashMap<Integer, SteamApp> apps;

    public Parser() {
        apps = new HashMap<>();
    }
    public HashMap<Integer, SteamApp> getMap() {
        return apps;
    }

    public HashMap<Integer, SteamApp> parseAll(String boosterData, String foilData, String badgeData) {
        updateBoosterDict(boosterData);
        updateValues(foilData, AppAttribute.FOIL_PRICE);
        updateValues(badgeData, AppAttribute.CARD_PRICE);
        return apps;
    }
    public void updateBoosterDict(String data) {
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
            temp.boosterRatio = Integer.parseInt(s.next().replaceAll("\\D+", ""));
            temp.threeCard = s.nextDouble();
            temp.gemRatio = Integer.parseInt(s.next().replaceAll("\\D+", ""));
            temp.gemPrice = s.nextDouble();
            s.useDelimiter("]");
            s.next();
            temp.lastUpdated = Long.parseLong(s.next().substring(2).replaceFirst(".$", ""));
            s.useDelimiter("\\[\\[");

            if (apps.containsKey(temp.id)) {
                apps.get(temp.id).update(temp);
            }
            else {
                apps.put(temp.id, temp);
            }

            s.next();
        }
    }

    public void updateValues(String data, AppAttribute toGet) {
        SteamApp temp;
        Scanner s = new Scanner(data).useLocale(Locale.US);

        s.useDelimiter("\\[\\[");
        s.next();
        while (s.hasNext()) {
            temp = new SteamApp();
            s.useDelimiter("\"");
            temp.id = Integer.parseInt(s.next().replaceAll("\\D+", ""));
            s.next();
            s.next();
            switch(toGet) {
                case FOIL_PRICE:
                    temp.foilCardPrice = Double.parseDouble(s.next().substring(1));
                    break;
                case CARD_PRICE:
                    temp.cardPrice = Double.parseDouble(s.next().substring(1));
                    break;
            }
            s.useDelimiter("\\[\\[");
            s.next();

            if (apps.containsKey(temp.id)) {
                apps.get(temp.id).update(temp);
            }
            else {
                apps.put(temp.id, temp);
            }
        }
    }
}
