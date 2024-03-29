import java.util.Random;

public class Character extends Entity{

    String characterName;
    int x, y;
    Inventory inventory;
    int exp;
    int upgradeExp;
    int level;
    int strength;
    int charisma;
    int dexterity;
    int basicDamage;

    public Character (String characterName, int exp, int level, Inventory inventory, String element) {
        currentHealth = maxHealth = 1500;
        currentMana = maxMana =100;
        this.characterName = characterName;
        this.exp = exp;
        this.level = level;
        this.inventory = inventory;
        upgradeExp = 200;
        switch (element) {
            case "fire":
                fire = true;
                strength = 5;
                dexterity = 2;
                charisma = 1;
                basicDamage = 60;
                break;
            case "ice":
                ice = true;
                dexterity = 5;
                strength = 2;
                charisma = 3;
                basicDamage = 30;
                break;
            case "earth":
                earth = true;
                charisma = 5;
                strength = 3;
                dexterity = 3;
                basicDamage = 45;
        }
        Random random = new Random();
        for (int i = 1; i <= 3; i++) {
            int rand = random.nextInt(3) + 1;
            switch (rand) {
                case 1:
                    addSpell(new Fire());
                    break;
                case 2:
                    addSpell(new Ice());
                    break;
                case 3:
                    addSpell(new Earth());
            }
        }
    }
    //strength - 0.03/level
    //dexterity - 0.02/level
    //charisma - 0.01/level
    @Override
    void receiveDamage(int health) {
        currentHealth -= health;
        if(currentHealth < 0) {
            currentHealth = 0;
        }
    }

    @Override
    void getDamage(Spell spell, Entity enemy) {}

    boolean buy (Potion potion) {
        if (inventory.getCash() >= potion.getPrice() && inventory.weightRemained() >= potion.getWeight()) {
            inventory.addCash(-potion.getPrice());
            inventory.addPotion(potion);
            return true;
        } else {
            System.out.println("Not enough money");
            return false;
        }
    }

    //Exp needed to level up increases with 10 exp/level
    void levelUp () {
        System.out.println("Character levelled up");
        exp -= upgradeExp;
        upgradeExp = upgradeExp + 10;
        level++;
    }

    @Override
    public String toString() {
        return "Character{" +
                "characterName='" + characterName + '\'' +
                ", exp=" + exp +
                ", level=" + level +
                '}';
    }

}

class Warrior extends Character {

    Warrior(String characterName, int exp, int level) {
        super(characterName, exp, level, new Inventory(50), "fire");
    }

    @Override
    void receiveDamage(int health) {
        Random random = new Random();
        double dodgeChance = 0.01 * charisma * level + 0.02 * dexterity * level;
        if((random.nextInt(100 - 1) + 1) <= dodgeChance) {
            System.out.println("Dodged because of the attributes");
        } else {
            super.receiveDamage(health);
        }
    }

    @Override
    void getDamage(Spell spell, Entity enemy) {
        Random random = new Random();
        double doubleChance = 0.03 * strength * level;
        if((random.nextInt(100 - 1) + 1) <= doubleChance) {
            if (spell != null) {
                 if((spell instanceof Fire && !enemy.fire) || (spell instanceof Ice && !enemy.ice) || (spell instanceof Earth && !enemy.earth)) {
                     enemy.receiveDamage(2 * (spell.damage + level * 3));
                 } else {
                     System.out.println("Protected against " + spell.getClass());
                 }
            } else {
                enemy.receiveDamage(2 * (basicDamage + level));
            }
            System.out.println("Double damage because of the attributes");
        } else {
            if (spell != null) {
                if((spell instanceof Fire && !enemy.fire) || (spell instanceof Ice && !enemy.ice) || (spell instanceof Earth && !enemy.earth)){
                    enemy.receiveDamage((spell.damage + level * 3));
                } else {
                    System.out.println("Protected against " + spell.getClass());
                }
            } else {
                enemy.receiveDamage(basicDamage + level);
                regenMana(10);
            }
        }

    }

    @Override
    public String toString() {
        return "Warrior{" +
                "characterName='" + characterName + '\'' +
                ", exp=" + exp +
                ", level=" + level +
                '}';
    }
}

class Mage extends Character {

    Mage(String characterName, int exp, int level) {
        super(characterName, exp, level, new Inventory(25), "ice");
    }

    @Override
    void receiveDamage(int health) {
        Random random = new Random();
        double dodgeChance = 0.03 * strength * level + 0.02 * dexterity * level;
        if((random.nextInt(100 - 1) + 1) <= dodgeChance) {
            System.out.println("Dodged because of the attributes");
        } else {
            super.receiveDamage(health);
        }
    }

    @Override
    void getDamage(Spell spell, Entity enemy) {
        Random random = new Random();
        double doubleChance = 0.01 * charisma * level;
        if((random.nextInt(100 - 1) + 1) <= doubleChance) {
            if (spell != null) {
                if((spell instanceof Fire && !enemy.fire) || (spell instanceof Ice && !enemy.ice) || (spell instanceof Earth && !enemy.earth)) {
                    enemy.receiveDamage(2 * (spell.damage + level * 3));
                } else {
                    System.out.println("Protected against " + spell.getClass());
                }
            } else {
                enemy.receiveDamage(2 * (basicDamage + level));
            }
            System.out.println("Double damage because of the attributes");
        } else {
            if (spell != null) {
                if((spell instanceof Fire && !enemy.fire) || (spell instanceof Ice && !enemy.ice) || (spell instanceof Earth && !enemy.earth)){
                    enemy.receiveDamage((spell.damage + level * 3));
                } else {
                    System.out.println("Protected against " + spell.getClass());
                }
            } else {
                enemy.receiveDamage(basicDamage + level);
                regenMana(10);
            }
        }
    }
    @Override
    public String toString() {
        return "Mage{" +
                "characterName='" + characterName + '\'' +
                ", exp=" + exp +
                ", level=" + level +
                '}';
    }
}

class Rogue extends Character {

    Rogue(String characterName, int exp, int level) {
        super(characterName, exp, level, new Inventory(40), "earth");
    }

    @Override
    void receiveDamage(int health) {
        Random random = new Random();
        double dodgeChance = 0.01 * charisma * level + 0.03 * strength * level;
        if((random.nextInt(100 - 1) + 1) <= dodgeChance) {
            System.out.println("Dodged because of the attributes");
        } else {
            super.receiveDamage(health);
        }
    }

    @Override
    void getDamage(Spell spell, Entity enemy) {
        Random random = new Random();
        double doubleChance = 0.02 * strength * level;
        if((random.nextInt(100 - 1) + 1) <= doubleChance) {
            if (spell != null) {
                if((spell instanceof Fire && !enemy.fire) || (spell instanceof Ice && !enemy.ice) || (spell instanceof Earth && !enemy.earth)) {
                    enemy.receiveDamage(2 * (spell.damage + level * 3));
                } else {
                    System.out.println("Protected against " + spell.getClass());
                }
            } else {
                enemy.receiveDamage(2 * (basicDamage + level));
            }
            System.out.println("Double damage because of the attributes");
        } else {
            if (spell != null) {
                if((spell instanceof Fire && !enemy.fire) || (spell instanceof Ice && !enemy.ice) || (spell instanceof Earth && !enemy.earth)){
                    enemy.receiveDamage((spell.damage + level * 3));
                } else {
                    System.out.println("Protected against " + spell.getClass());
                }
            } else {
                enemy.receiveDamage(basicDamage + level);
                regenMana(10);
            }
        }
    }
    @Override
    public String toString() {
        return "Rogue{" +
                "characterName='" + characterName + '\'' +
                ", exp=" + exp +
                ", level=" + level +
                '}';
    }
}
