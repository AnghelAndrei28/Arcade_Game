import java.util.ArrayList;
import java.util.List;

public class Inventory {
    List<Potion> potions = new ArrayList<Potion>();
    private final int maxWeight;
    private int cash;

    public Inventory(int maxWeight) {
        this.maxWeight = maxWeight;
        this.cash = 50;
    }

    void addPotion (Potion potion) {
        potions.add(potion);
    }

    void eliminatePotion(Potion potion) {
        potions.remove(potion);
    }

    void usePotion (Character character, Potion potion) {
            potion.usePotion(character);
            eliminatePotion(potion);
    }


    int weightRemained() {
        int weight = 0;
        for (Potion potion: potions) {
            weight += potion.getWeight();
        }
        return maxWeight - weight;
    }

    void addCash(int cash) {
        this.cash += cash;
    }

    public int getCash() {
        return cash;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "potions=" + potions +
                ", maxWeight=" + maxWeight +
                ", cash=" + cash +
                '}';
    }
}
