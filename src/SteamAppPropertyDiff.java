import java.io.Serializable;

public class SteamAppPropertyDiff implements Serializable {
    public Object old;
    public Object curr;
    public SteamAppAttribute specifier;

    public SteamAppPropertyDiff(SteamAppAttribute specifier, Object old, Object curr) {
        this.specifier = specifier;
        this.old = old;
        this.curr = curr;
    }
    public SteamAppPropertyDiff(SteamAppAttribute specifier) {
        this.specifier = specifier;
    }

    public void setOld(Object old) {
        this.old = old;
    }

    public void setCurr(Object curr) {
        this.curr = curr;
    }

    public void setSpecifier(SteamAppAttribute specifier) {
        this.specifier = specifier;
    }

    @Override
    public String toString() {
        return this.specifier.name() + ": " + old.toString() + " » " + curr.toString();
    }
}
