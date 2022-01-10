import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    List<Spell> spells = new ArrayList<Spell>();
    int currentHealth;
    int maxHealth;
    int currentMana;
    int maxMana;
    boolean fire = false;
    boolean ice = false;
    boolean earth = false;

    void regenHealth(int health) {
        currentHealth += health;
        if(currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }

    void regenMana(int mana) {
        currentMana += mana;
        if(currentMana > maxMana) {
            currentMana = maxMana;
        }
    }

    void useSpell(Spell spell, Entity enemy) {
        if(currentMana >= spell.manaPrice) {
            currentMana -= spell.manaPrice;
            getDamage(spell, enemy);
        } else {
            System.out.println("Not enough mana. Normal attack!");
            getDamage(null, enemy);
            //Todo: Adaugat atac normal
        }
    }

    void addSpell(Spell spell) {
        spells.add(spell);
    }

    abstract void receiveDamage(int health);
    abstract void getDamage(Spell spell, Entity enemy);

}
