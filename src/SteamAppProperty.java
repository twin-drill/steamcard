public class SteamAppProperty <T> {
    public SteamAppAttribute specifier;
    public T value;

    public SteamAppProperty(SteamAppAttribute specifier, T value) {
        this.specifier = specifier;
        this.value = value;
    }

    public SteamAppProperty(SteamAppAttribute specifier) {
        this.specifier = specifier;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
