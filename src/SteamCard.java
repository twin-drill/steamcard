import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SteamCard {

    private static final Logger log = LogManager.getLogger("SteamCard");
    public static void main(String[] args) {
        log.atLevel(Level.DEBUG);

        log.trace("Execution started.");
        TextInterface.data = new Dataset();
        TextInterface.mainloop();
    }

}
