import java.util.ArrayList;
import java.util.List;

public class Inventory {
    List<Potion> potions = new ArrayList<Potion>();
    private final int maxWeight;
    private int cash = 50;

    public Inventory(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    void addPotion (Potion potion) {
        potions.add(potion);
    }

    void eliminatePotion(Potion potion) {
        potions.remove(potion);
    }

    void useHealthPotion(Character character) {
        for (Potion potion : potions) {
            if(potion instanceof HealthPotion) {
                potion.usePotion(character);
                eliminatePotion(potion);
                break;
            }
        }
    }

    void useManaPotion(Character character) {
        for (Potion potion : potions) {
            if(potion instanceof ManaPotion) {
                potion.usePotion(character);
                eliminatePotion(potion);
                break;
            }
        }
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
