package Players;
import Enemies.Enemy;
import Tiles.Bar;
import Tiles.Position;
import Tiles.Tile;
import Tiles.Unit;

import java.util.List;
import java.util.Random;

public abstract class Player extends Unit {

    private int experience;
    private int experienceForLevel;
    protected int level;

    public Player(String name, Position position, int attack, int defense, int health) {
        super(name, '@', position, attack, defense, health);
        this.experience = 0;
        this.experienceForLevel = 50;
        this.level = 1;
    }

    public void gainExperience(int experience) {
        this.experience += experience;
        while (this.experience >= this.experienceForLevel * level) {
            levelUp();
        }
    }
    public void levelUp(){
        setExperience(getExperience() - (50 * level));
        level++;
        setHealthPool(getHealthPool() + (10 * level));
        setHealthAmount(getHealthPool());
        setAttack(getAttack() + (4 * level));
        setDefense(getDefense() + level);
        personalLevelUp();
    }
    public abstract void personalLevelUp();

    public abstract void abilityCast(List<Enemy> enemies);

    public boolean visit(Tile tile) { return tile.accept(this);}
    public boolean accept (Enemy enemy){ return enemy.visit(this);}
    public boolean visit(Player player) { return false;}
    public boolean accept (Player player) { return false;}

    public boolean visit(Enemy enemy) {
        System.out.println(getName() + " engaged in combat with " + enemy.getName());
        System.out.println(this);
        System.out.println(enemy);
        Random rand = new Random();
        int attackValue = rand.nextInt(getAttack() + 1);
        int defenseValue = rand.nextInt(enemy.getDefense() + 1);
        System.out.println(getName() + " rolles " + attackValue + " attack points");
        System.out.println(enemy.getName() + " rolles " + defenseValue + " defense points");
        if(attackValue > defenseValue) {
            int damage = attackValue - defenseValue;
            System.out.println(getName() + " dealt " + damage + " damage to " + enemy.getName());
            enemy.setHealthAmount(enemy.getHealthAmount() - damage);
            if (enemy.getHealthAmount() <= 0) {
                gainExperience(enemy.getExperienceValue());
                return true;
            }
        }
        return false;
    }

    public abstract void gameTick();

    public abstract int getRange();

    public int getLevel() {return level;}
    public int getExperience() {return experience;}
    public void setExperience(int experience) {this.experience = experience;}
    public int getExperienceForLevel() {return experienceForLevel;}

    public abstract String toString();

}
