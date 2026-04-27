package Players;

import Enemies.Enemy;
import Tiles.Bar;
import Tiles.Position;
import Tiles.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mage extends Player {

    int manaPool;
    int currentMana;
    int manaCost;
    int spellPower;
    int hitsCount;
    int abilityRange;

    public Mage(String name, Position position, int attack, int defense, int health, int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange) {
        super(name, position, attack, defense, health);
        this.manaPool = manaPool;
        this.currentMana = manaPool / 4;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abilityRange = abilityRange;
    }


    @Override
    public void gameTick() {
        currentMana = Math.min(manaPool, currentMana + getLevel());
    }



    public int getManaPool() {return manaPool;}
    public int getCurrentMana() {return currentMana;}
    public int getSpellPower() {return spellPower;}
    public void setCurrentMana(int mana){currentMana = mana;}


    @Override
    public void abilityCast(List<Enemy> enemies) {
        if (currentMana >= manaCost) {
            System.out.println(getName() + " cast Blizzard.");
            currentMana -= manaCost;
            int hits = 0;

            List<Enemy> modifiableEnemies = new ArrayList<>(enemies);

            while (hits < hitsCount && !modifiableEnemies.isEmpty()) {
                Random random = new Random();
                int index = random.nextInt(modifiableEnemies.size());
                Enemy enemy = modifiableEnemies.get(index);
                hit(enemy);
                if (enemy.getHealthAmount() <= 0) {
                    modifiableEnemies.remove(index);
                }
                hits++;
            }
        } else {
            System.out.println("You can't cast your ability!");
        }
    }


    private void hit(Enemy enemy) {
        Random rand = new Random();
        int attackValue = rand.nextInt(getAttack() + 1);
        int defenseValue = rand.nextInt(enemy.getDefense() + 1);
        System.out.println(enemy.getName() + " rolled " + defenseValue + " defense points");
        if(attackValue > defenseValue) {
            int damage = attackValue - defenseValue;
            System.out.println(getName() + " hit " + enemy.getName() + " for " + damage + " ability damage.");
            enemy.setHealthAmount(enemy.getHealthAmount() - damage);
            if (enemy.getHealthAmount() <= 0) {
                gainExperience(enemy.getExperienceValue());
            }
        }
    }

    public void personalLevelUp() {
        manaPool = manaPool + (25 * getLevel());
        currentMana = Math.min(currentMana + (manaPool / 4), manaPool);
        spellPower = spellPower + (10 * getLevel());
        System.out.println("Level up!");
    }

    public int getRange(){ return abilityRange;}

    public String toString(){
        return getName() + "              Health: " + getHealthAmount() +"/" + getHealthPool() +
                "           Attack: " + getAttack() + "           Defense: " + getDefense() +
                "           Level: " + getLevel() + "           Experience: " + getExperience() + "/" + getExperienceForLevel() +
                "           Mana: " + getCurrentMana() + "/" + getManaPool() + "           Spell Power: " + getSpellPower();
    }
}
