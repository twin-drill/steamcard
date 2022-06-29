import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
Since urls are all hard-coded exceptions should be handled in this class itself.
 */
public class Connection {

    private HttpURLConnection conn;
    private URL url;
    BufferedReader content;

    public Connection(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String get() {
        try {
            content = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            return content.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        conn.disconnect();
    }

    public void open() {
        try {
            this.conn = (HttpURLConnection) this.url.openConnection();
            this.conn.setRequestMethod("GET");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
