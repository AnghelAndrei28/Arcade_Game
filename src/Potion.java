public interface Potion {
    void usePotion(Character character);
    int getPrice();
    int regenerationValue();
    int getWeight();
}

class HealthPotion implements Potion{

    private final int price = 10;
    private final int weight = 8;
    private final int value = 20;

    @Override
    public void usePotion(Character character) {
        character.regenHealth(value);
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int regenerationValue() {
        return value;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "HealthPotion{" +
                "price=" + price +
                ", weight=" + weight +
                ", value=" + value +
                '}';
    }
}

class ManaPotion implements Potion{

    private final int price = 20;
    private final int weight = 10;
    private final int value = 20;

    @Override
    public void usePotion(Character character) {
        character.regenMana(value);
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int regenerationValue() {
        return value;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "ManaPotion{" +
                "price=" + price +
                ", weight=" + weight +
                ", value=" + value +
                '}';
    }
}
