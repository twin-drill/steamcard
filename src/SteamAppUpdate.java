public class SteamAppUpdate {
    public SteamAppProperty[] properties;
    public int id;

    public SteamAppUpdate(SteamAppProperty[] properties) {
        this.properties = properties;
    }

    public SteamAppUpdate(int id) {
        this.id = id;
    }

    public void setProperties(SteamAppProperty[] properties) {
        this.properties = properties;
    }

    public void setProperties(SteamAppProperty property) {
        this.properties = new SteamAppProperty[] { property };
    }
}
