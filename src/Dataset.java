import java.io.FileNotFoundException;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

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

    public void save() {
        Cache.buildCache(apps);
        log.debug("Cache saved");
    }

    public void fetch() {
        log.trace("fetching data");
        Parser p = new Parser();
        Connection[] c = new Connection[] {
                new Connection("https://www.steamcardexchange.net/api/request.php?GetBoosterPrices"),
                new Connection("https://www.steamcardexchange.net/api/request.php?GetFoilBadgePrices_Member"),
                new Connection("https://www.steamcardexchange.net/api/request.php?GetBadgePrices_Member") };

        for (Connection conn : c) { conn.open();}
        apps = p.parseAll(c[0].get(), c[1].get(), c[2].get());
        for (Connection conn : c) { conn.close();}
        save();
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
