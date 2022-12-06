import java.io.FileNotFoundException;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dataset {

    private static final Logger log = LogManager.getLogger("Dataset");

    public HashMap<Integer, SteamApp> apps;

    public Dataset() {
        apps = new HashMap<>();
    }

    public List<SearchResult> nameSearch(String query) {
        ArrayList<SearchResult> results = new ArrayList<>();
        apps.forEach((a, b) -> {
            if (b.name.toLowerCase().contains(query)) {
                results.add(new SearchResult(b));
            }
        });
        return results;
    }

    public List<SearchResult> threeCardSort(boolean low) {
        ArrayList<SearchResult> results = new ArrayList<>();
        ArrayList<SteamApp> sortable = new ArrayList<>(apps.values());
        sortable.sort(Comparator.comparingInt(a -> a.threeCardRatio));
        if (!low) Collections.reverse(sortable);
        sortable.forEach(a -> results.add(new SearchResult(a)));
        return results;
    }

    public List<SearchResult> gemSort(boolean low) {
        ArrayList<SearchResult> results = new ArrayList<>();
        ArrayList<SteamApp> sortable = new ArrayList<>(apps.values());
        sortable.sort(Comparator.comparingInt(a -> a.gemRatio));
        if (low) Collections.reverse(sortable);
        sortable.forEach(a -> results.add(new SearchResult(a)));
        return results;
    }

    public List<SearchResult> boosterSort(boolean low) {
        ArrayList<SearchResult> results = new ArrayList<>();
        ArrayList<SteamApp> sortable = new ArrayList<>(apps.values());
        sortable.sort(Comparator.comparingInt(a -> a.gemRatio + a.threeCardRatio));
        if (!low) Collections.reverse(sortable);
        sortable.forEach(a -> results.add(new SearchResult(a)));
        return results;
    }

    public void save() {
        Cache.buildCache(apps);
        log.debug("Cache saved");
    }

    public void fetch() {
        log.trace("fetching data");
        Parser p = load_silent() ? new Parser(apps) : new Parser();
        Connection[] c = new Connection[] {
                new Connection("https://www.steamcardexchange.net/api/request.php?GetBoosterPrices"),
                new Connection("https://www.steamcardexchange.net/api/request.php?GetFoilBadgePrices_Member"),
                new Connection("https://www.steamcardexchange.net/api/request.php?GetBadgePrices_Member") };

        for (Connection conn : c) { conn.open();}
        apps = p.parseAll(c[0].get(), c[1].get(), c[2].get());
        for (Connection conn : c) { conn.close();}
        save();
    }

    public boolean load_silent() {
        log.trace("silent load");
        try {
            apps = Cache.readCache();
            return true;
        }
        catch(FileNotFoundException e) {
            return false;
        }
    }

    public void load() {
        log.trace("loading data");
        try {
            apps = Cache.readCache();
        }
        catch(FileNotFoundException e) {
            out.outb("cannot find cache file. aborting...");
        }
    }

    public int size() {
        return apps.size();
    }
}
