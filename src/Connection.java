import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/*
Since urls are all hard-coded exceptions should be handled in this class itself.
 */
public class Connection {

    private static final Logger log = LogManager.getLogger("Connection");
    private HttpURLConnection conn;
    private final URL url;
    BufferedReader content;

    public Connection(String url) {
        log.trace("Connection to " + url + " instantiated.");
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String get() {
        log.trace("getting contents of " + url);
        try {
            content = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            return content.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        conn.disconnect();
        log.debug("Connection to " + url + " closed.");
    }

    public void open() {
        log.debug("Connection to " + url + " opened.");
        try {
            this.conn = (HttpURLConnection) this.url.openConnection();
            this.conn.setRequestMethod("GET");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
