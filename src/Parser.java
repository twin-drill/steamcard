import java.util.ArrayList;
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
        updateValues(foilData, SteamAppAttribute.FOIL_PRICE);
        updateValues(badgeData, SteamAppAttribute.CARD_PRICE);
        return apps;
    }

    @SuppressWarnings("unchecked")
    public void updateBoosterDict(String data) {
        SteamAppUpdate temp;
        Scanner s = new Scanner(data).useLocale(Locale.US);
        ArrayList<SteamAppProperty> properties;

        s.useDelimiter("\\[\\[");
        s.next();
        while (s.hasNext()) {

            properties = new ArrayList<>() {{
                add(new SteamAppProperty<String>(SteamAppAttribute.NAME));
                add(new SteamAppProperty<Integer>(SteamAppAttribute.NUM_CARDS));
                add(new SteamAppProperty<Double>(SteamAppAttribute.BOOSTER_PRICE));
                add(new SteamAppProperty<Integer>(SteamAppAttribute.THREE_CARD_RATIO));
                add(new SteamAppProperty<Double>(SteamAppAttribute.THREE_CARD));
                add(new SteamAppProperty<Integer>(SteamAppAttribute.GEM_RATIO));
                add(new SteamAppProperty<Double>(SteamAppAttribute.GEM_PRICE));
                add(new SteamAppProperty<Long>(SteamAppAttribute.LAST_UPDATED));

            }};

            // Change delimiter to inside-app delimiter
            s.useDelimiter("\"");
            // Discard brackets
            s.next();

            // Parse data, throw away midsegments
            temp = new SteamAppUpdate(s.nextInt());
            s.next();
            properties.get(0).setValue(s.next());
            s.next();
            properties.get(1).setValue(s.nextInt());
            s.next();
            properties.get(2).setValue(Double.parseDouble(s.next().substring(1)));
            properties.get(3).setValue(Integer.parseInt(s.next().replaceAll("\\D+", "")));
            properties.get(4).setValue(s.nextDouble());
            properties.get(5).setValue(Integer.parseInt(s.next().replaceAll("\\D+", "")));
            properties.get(6).setValue(s.nextDouble());
            s.useDelimiter("]");
            s.next();
            properties.get(7).setValue(Long.parseLong(s.next().replaceAll("\\D+", "")));
            s.useDelimiter("\\[\\[");

            temp.setProperties(properties.toArray(new SteamAppProperty[0]));

            if (apps.containsKey(temp.id)) {
                apps.get(temp.id).update(temp);
            }
            else {
                apps.put(temp.id, new SteamApp(temp));
            }

            s.next();
        }
    }

    public void updateValues(String data, SteamAppAttribute toGet) {
        SteamAppUpdate temp;
        SteamAppProperty<Double> p;
        SteamAppProperty<Long> u;
        String a;
        Scanner s = new Scanner(data).useLocale(Locale.US);

        s.useDelimiter("\\[\\[");
        s.next();
        while (s.hasNext()) {
            p = new SteamAppProperty<>(toGet);
            u = new SteamAppProperty<>(SteamAppAttribute.LAST_UPDATED);
            s.useDelimiter("\"");
            a = s.next().replaceAll("\\D+", "");
            if (a.isBlank()) break;
            temp = new SteamAppUpdate(Integer.parseInt(a));
            s.next();
            s.next();
            p.setValue(Double.parseDouble(s.next().substring(1)));
            s.next();
            u.setValue(Long.parseLong(s.next()));
            s.useDelimiter("\\[\\[");
            temp.setProperties(new SteamAppProperty[] {p, u});

            if (apps.containsKey(temp.id)) {
                apps.get(temp.id).update(temp);
            }
            else {
                apps.put(temp.id, new SteamApp(temp));
            }
        }
    }
}
