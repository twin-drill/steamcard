import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class TextInterface {

    private static final Logger log = LogManager.getLogger("TextInterface");
    private static Scanner s = new Scanner(System.in);
    public static Dataset data;

    public static void mainloop() {

        log.trace("main loop");

        out.outi(".Menu 1 Search for App" +
                "\n2 Update Definitions" +
                "\n3 Show Top" +
                "\n4 Random" +
                "\n5 Options" +
                "\n0 Quit");

        switch(s.nextInt()) {

            default:
                break;

            case 0:
                log.trace("exiting");
                System.exit(0);
                break;

            case 1:
                log.trace("searching");
                search();
                break;


            case 2:
                log.trace("updating");
                out.outi(".Upd 1 From Local Cache\n2 From Internet");

                switch(s.nextInt()) {
                    case 1:
                        data.load();
                        break;
                    case 2:
                        data.fetch();
                        break;
                }
                break;

        }

        mainloop();
    }

    public static void search() {

        out.outi(".Find 1 Lookup by AppID" +
                "\n2 Search by Name" +
                "\n3 Back");

        switch(s.nextInt()) {
            case 1:
                out.outi("- Enter AppID");
                out.outb(data.apps.get(s.nextInt()).toString());
                break;
            case 2:
                out.outi("- Enter search query");
                s.nextLine();
                List<SearchResult> results = data.nameSearch(s.nextLine().trim().toLowerCase());
                for (SearchResult sr : results) {
                    out.outb(sr.toString());
                }
                search();
                break;

            case 3:
            default:
                break;
        }
    }

}
