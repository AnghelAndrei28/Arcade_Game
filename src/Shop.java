import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shop implements CellElement{
    List<Potion> potionList = new ArrayList<Potion>();

    Shop() {
        Random r = new Random();
        int numberOfPotions = r.nextInt(4 - 1) + 2;
        for (int i = 1; i <= numberOfPotions; i++) {
            if(r.nextBoolean()) {
                potionList.add(new HealthPotion());
            } else {
                potionList.add(new ManaPotion());
            }
        }
    }

    @Override
    public String toCharacter() {
        return "S ";
    }

    Potion getPotion (int index) {
        Potion chosenPotion = potionList.get(index);
        potionList.remove(index);
        return  chosenPotion;
    }

    void printPotionList() {
        int i = 1;
        System.out.println("Shop items");
        for (Potion potion : potionList) {
            System.out.println(i + ". " + potion);
            i++;
        }
        System.out.println(i + ". Leave");
    }
}
