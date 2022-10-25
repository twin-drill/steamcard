public class SearchResult {


    private String name;
    private int id;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public SearchResult(SteamApp app) {
        this.name = app.name;
        this.id = app.id;
    }

    @Override
    public String toString() {
        return "( " + id + "\t)" + " " + name;
    }

}
