public abstract class Spell {
    int damage;
    int manaPrice;
}

class Ice extends Spell{
    Ice () {
        damage = 100;
        manaPrice = 20;
    }

    @Override
    public String toString() {
        return "Ice {" +
                "damage: " + damage +
                ", manaPrice " + manaPrice +
                "}";
    }
}

class Fire extends Spell {
    Fire() {
        damage = 120;
        manaPrice = 30;
    }

    @Override
    public String toString() {
        return "Fire {" +
                "damage: " + damage +
                ", manaPrice " + manaPrice +
                "}";
    }
}

class Earth extends Spell{
    Earth() {
        damage = 70;
        manaPrice = 10;
    }

    @Override
    public String toString() {
        return "Earth {" +
                "damage: " + damage +
                ", manaPrice " + manaPrice +
                "}";
    }
}
