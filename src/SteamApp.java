import java.io.Serializable;

public class SteamApp implements Serializable {

    public int id = -1;
    public String name = "";
    public int numCards = -1;
    public double boosterPrice = -1;
    public int boosterRatio = -1;
    public double threeCard = -1;
    public int gemRatio = -1;
    public double gemPrice = -1;
    public double cardPrice = -1;
    public double foilCardPrice = -1;
    public long lastUpdated;

    @Override
    public String toString() {
        return "id : " + id;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SteamApp)) return false;
        return ((SteamApp) other).id == this.id;
    }

    public void update(SteamApp newApp) {
        boolean newer = this.lastUpdated < newApp.lastUpdated;

        if (name.isBlank() || (newer && !newApp.name.isBlank())) {
            this.name = newApp.name;
        }

        if (numCards == -1 || (newer && newApp.numCards != -1)) {
            this.numCards = newApp.numCards;
        }

        if (boosterPrice == -1 || (newer && newApp.boosterPrice != -1)) {
            this.boosterPrice = newApp.boosterPrice;
        }

        if (boosterRatio == -1 || (newer && newApp.boosterRatio != -1)) {
            this.boosterRatio = newApp.boosterRatio;
        }

        if (threeCard == -1 || (newer && newApp.threeCard != -1)) {
            this.threeCard = newApp.threeCard;
        }

        if (gemRatio == -1 || (newer && newApp.gemRatio != -1)) {
            this.gemRatio = newApp.gemRatio;
        }

        if (gemPrice == -1 || (newer && newApp.gemPrice != -1)) {
            this.gemPrice = newApp.gemPrice;
        }

        if (foilCardPrice == -1 || (newer && newApp.foilCardPrice != -1)) {
            this.foilCardPrice = newApp.foilCardPrice;
        }

        if (cardPrice == -1 || (newer && newApp.cardPrice != -1)) {
            this.cardPrice = newApp.cardPrice;
        }

        if (newer) {
            this.lastUpdated = newApp.lastUpdated;
        }
    }

}