public class SearchResult {
    public String name;
    public int id;

    public SearchResult(SteamApp app) {
        this.name = app.name;
        this.id = app.id;
    }

    @Override
    public String toString() {
        return "( " + id + "\t)" + " " + name;
    }

}
