package Players;

import Enemies.Enemy;
import Tiles.Bar;
import Tiles.Position;
import Tiles.Tile;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;
import java.util.List;
import java.util.Random;

public class Rogue extends Player {

    int cost;
    int currentEnergy;

    public Rogue(String name, Position position, int attack, int defense, int health, int cost) {
        super(name, position,attack, defense, health);
        this.cost = cost;
        this.currentEnergy = 100;
    }


    @Override
    public void gameTick() {currentEnergy = Math.min(currentEnergy + 10, 100);
    }

    public void personalLevelUp() {
        currentEnergy = 100;
        setAttack(getAttack() + (3 * level));
        System.out.println("Level up!");
    }

    public void abilityCast(List<Enemy> enemies) {
        if (currentEnergy >= cost){
            System.out.println(getName() + " cast Fan of Knives.");
            currentEnergy -= cost;
            for (Enemy enemy: enemies) {
                hit(enemy);
            }
        }
        else System.out.println("You can't cast your ability!");
    }

    private void hit(Enemy enemy){
        Random rand = new Random();
        int attackValue = getAttack();
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

    public int getRange(){ return 2;}

    public int getCurrentEnergy() {return currentEnergy;}
    public int getCost(){return cost;}
    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    public String toString(){
        return getName() + "              Health: " + getHealthAmount() +"/" + getHealthPool() +
                "           Attack: " + getAttack() + "           Defense: " + getDefense() +
                "           Level: " + getLevel() + "           Experience: " + getExperience() + "/" + getExperienceForLevel() +
                "           Energy: " + getCurrentEnergy() + "/" + getCost();
    }
}
