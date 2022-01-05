public class Character extends Entity{

    String characterName;
    int x, y;
    Inventory inventory;
    int exp;
    int level;
    int strength;
    int charisma;
    int dexterity;

    public Character (String characterName, int exp, int level, Inventory inventory, String element) {
        currentHealth = maxHealth = 200;
        currentMana = maxMana = 20;
        this.characterName = characterName;
        this.exp = exp;
        this.level = level;
        this.inventory = inventory;
        switch (element) {
            case "fire":
                super.fire = true;
            case "ice":
                super.ice = true;
            case "earth":
                super.earth = true;
        }
    }

    @Override
    void receiveDamage(int health) {
        super.currentHealth -= health;
    }

    @Override
    void getDamage(Spell spell, Character enemy) {}

    void buy (Potion potion) {
        if (inventory.getCash() > potion.getPrice() && inventory.weightRemained() > potion.getWeight()) {
            inventory.addPotion(potion);
        }
    }
}

class Warrior extends Character {

    Warrior(String characterName, int exp, int level) {
        super(characterName, exp, level, new Inventory(50), "fire");
    }

    @Override
    void receiveDamage(int health) {
        super.receiveDamage(health);
    }

    @Override
    void getDamage(Spell spell, Character enemy) {
        enemy.receiveDamage(spell.damage);
    }
}

class Mage extends Character {

    Mage(String characterName, int exp, int level) {
        super(characterName, exp, level, new Inventory(25), "ice");
    }

    @Override
    void receiveDamage(int health) {
        super.receiveDamage(health);
    }

    @Override
    void getDamage(Spell spell, Character enemy) {
        enemy.receiveDamage(spell.damage * 2);
    }
}

class Rogue extends Character {

    Rogue(String characterName, int exp, int level) {
        super(characterName, exp, level, new Inventory(40), "earth");
    }

    @Override
    void receiveDamage(int health) {
        super.receiveDamage(health);
    }

    @Override
    void getDamage(Spell spell, Character enemy) {
        enemy.receiveDamage(spell.damage * 2);
    }
}
