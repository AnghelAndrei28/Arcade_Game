import java.util.Random;

public class Enemy extends Entity implements CellElement {

    public Enemy () {
        Random r = new Random();
        int lowHealth = 300;
        int highHealth = 400;
        int lowMana = 20;
        int highMana = 60;
        currentHealth = maxHealth = r.nextInt(highHealth - lowHealth) + lowHealth;
        currentMana = maxMana = r.nextInt(highMana - lowMana) + lowMana;
        int numberOfSpells = r.nextInt(4 - 2) + 2;
        for (int i = 1; i <= numberOfSpells; i++) {
            int spellNumber = r.nextInt(3 - 1) + 1;
            switch (spellNumber) {
                case 1:
                    spells.add(new Fire());
                    break;
                case 2:
                    spells.add(new Ice());
                    break;
                case 3:
                    spells.add(new Earth());
            }
        }
        for (int i = 1; i <= 3; i++) {
            switch (i) {
                case 1:
                    fire = r.nextBoolean();
                case 2:
                    ice = r.nextBoolean();
                case 3:
                    earth = r.nextBoolean();
            }
        }
    }

    @Override
    public String toCharacter() {
        return "E ";
    }

    @Override
    void receiveDamage(int health) {
        Random r = new Random();
        if(r.nextBoolean()) {
            currentHealth -= health;
        } else {
            System.out.println("Dodge!!!!!!");
        }
    }

    @Override
    void getDamage(Spell spell, Entity enemy) {
        if(spell != null) {
            if((spell instanceof Fire && !enemy.fire) || (spell instanceof Ice && !enemy.ice) || (spell instanceof Earth && !enemy.earth)) {
                Random r = new Random();
                if (r.nextBoolean()) {
                    enemy.receiveDamage(spell.damage * 2);
                    System.out.println("Critical hit!");
                    System.out.println("Damage: " + spell.damage + "from" + spell.getClass());
                } else {
                    enemy.receiveDamage(spell.damage);
                    System.out.println("Damage" + spell.damage + spell.getClass());
                }
            } else {
                System.out.println("Protected against the spell " + spell.getClass());
            }
        } else {
            Random r = new Random();
            System.out.println("Basic attack");
            if (r.nextBoolean()) {
                enemy.receiveDamage(20 * 2);
                System.out.println("Critical hit!");
            } else {
                enemy.receiveDamage(20);
            }
        }
    }
}
