import java.util.ArrayList;
import java.io.Serializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SteamApp implements Serializable {

    private static final Logger log = LogManager.getLogger("SteamApp");

    public int id;
    public String name = "uninitialized";
    public int numCards = 0;
    public double boosterPrice = 0;
    public int threeCardRatio = 0;
    public double threeCard = 0;
    public int gemRatio = 0;
    public double gemPrice = 0;
    public double cardPrice = 0;
    public double foilCardPrice = 0;
    public long lastUpdated = 0;
    public ArrayList<SteamAppPropertyDiffCollection> updateHistory = new ArrayList<>();

    public SteamApp(SteamAppUpdate pack) {
        log.debug("Creating app " + pack.id);
        this.id = pack.id;
        this.update(pack);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SteamApp) {
            return ((SteamApp) other).id == this.id;
        }
        if (other instanceof SteamAppUpdate) {
            return ((SteamAppUpdate) other).id == this.id;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nName: " + name +
                "\n\nNumber of Cards: " + numCards +
                "\n\nBooster Price: $" + boosterPrice +
                "\n\tMarket Average for 3 Cards: $" + threeCard + " (" + String.format("%.2f", ((threeCard / boosterPrice) * 100)) + "%)" +
                "\n\tGem Price for Booster: $" + gemPrice + " (" + String.format("%.2f", ((gemPrice / boosterPrice) * 100)) + "%)" +
                "\n\n Price for 5 Cards: $" + cardPrice +
                "\nPrice for 5 Foil Cards: $" + foilCardPrice +
                "\nLast Updated: " + lastUpdated +
                "\n\nChangelog:\n" + updateHistory.toString().replaceAll("\\[|]", "").replaceAll(", ", "\n");
    }

    public void update(SteamAppUpdate update) {

        log.debug("Updating app " + id);

        ArrayList<SteamAppPropertyDiff> diffs = new ArrayList<>();
        SteamAppPropertyDiffCollection coll = null;
        SteamAppPropertyDiff temp;

        for (SteamAppProperty p : update.properties) {
            if (p.specifier == SteamAppAttribute.LAST_UPDATED) {
                coll = new SteamAppPropertyDiffCollection((long) p.value);
            }
        }

        for (SteamAppProperty property : update.properties) {
            temp = new SteamAppPropertyDiff(property.specifier);
            temp.setCurr(property.value);
            switch(property.specifier) {
                case NAME:
                    if (!property.value.equals(this.name)) {
                        temp.setOld(this.name);
                        this.name = (String) property.value;
                    }
                    break;
                case NUM_CARDS:
                    if (this.numCards != (int) property.value) {
                        temp.setOld(this.numCards);
                        this.numCards = (int) property.value;
                    }
                    break;
                case BOOSTER_PRICE:
                    if (this.boosterPrice != (double) property.value) {
                        temp.setOld(this.boosterPrice);
                        this.boosterPrice = (double) property.value;
                    }
                    break;
                case THREE_CARD_RATIO:
                    if (this.threeCardRatio != (int) property.value) {
                        temp.setOld(this.threeCardRatio);
                        this.threeCardRatio = (int) property.value;
                    }
                    break;
                case THREE_CARD:
                    if (this.threeCard != (double) property.value) {
                        temp.setOld(this.threeCard);
                        this.threeCard = (double) property.value;
                    }
                    break;
                case GEM_PRICE:
                    if (this.gemPrice != (double) property.value) {
                        temp.setOld(this.gemPrice);
                        this.gemPrice = (double) property.value;
                    }
                    break;
                case GEM_RATIO:
                    if (this.gemRatio != (int) property.value) {
                        temp.setOld(this.gemRatio);
                        this.gemRatio = (int) property.value;
                    }
                    break;
                case CARD_PRICE:
                    if (this.cardPrice != (double) property.value) {
                        temp.setOld(this.cardPrice);
                        this.cardPrice = (double) property.value;
                    }
                    break;
                case FOIL_PRICE:
                    if (this.foilCardPrice != (double) property.value) {
                        temp.setOld(this.foilCardPrice);
                        this.foilCardPrice = (double) property.value;
                    }
                    break;
                case LAST_UPDATED:
                        if (this.lastUpdated < (long) property.value) {
                            this.lastUpdated = (long) property.value;
                        }
                        break;
                default:
                    break;
            }

            if (temp.old != null) {
                diffs.add(temp);
            }
        }

        coll.setDiffs(diffs.toArray(new SteamAppPropertyDiff[0]));
        updateHistory.add(0, coll);
    }

}
