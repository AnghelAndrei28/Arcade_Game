public abstract class Spell {
    int damage;
    int manaPrice;
}

class Ice extends Spell{
    Ice () {
        damage = 50;
        manaPrice = 20;
    }
}

class Fire extends Spell {
    Fire() {
        damage = 80;
        manaPrice = 40;
    }
}

class Earth extends Spell{
    Earth() {
        damage = 20;
        manaPrice = 5;
    }
}
