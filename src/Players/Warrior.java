package Players;

import Enemies.Enemy;
import Tiles.Bar;
import Tiles.Position;
import Tiles.Tile;

import java.lang.foreign.AddressLayout;
import java.util.List;
import java.util.Random;

public class Warrior extends Player {

    private int abilityCooldown;
    private int remainingCooldown;

    public Warrior(String name, Position position, int attack, int defense, int health, int abilityCooldown) {
        super(name, position, attack, defense, health);
        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
    }


    @Override
    public void gameTick() {
        if (remainingCooldown > 0) {remainingCooldown--;}
    }


    public void personalLevelUp() {
        remainingCooldown = 0;
        setHealthPool(getHealthPool() + (5 * getLevel()));
        setAttack(getAttack() + (2 * getLevel()));
        setDefense(getDefense() + getLevel());
        System.out.println("Level up!");
    }

    public void abilityCast(List<Enemy> enemies) {
        if (remainingCooldown == 0){
            System.out.println(getName() + " cast Avenger’s Shield.");
            remainingCooldown = abilityCooldown;
            setHealthAmount(Math.min(getHealthAmount() + (10 * getDefense()), getHealthPool()));
            Random random = new Random();
            int index = random.nextInt(enemies.size());
            hit(enemies.get(index));
        }
        else System.out.println("Yoy can't cast your ability!");
    }

    private void hit(Enemy enemy){
        Random random = new Random();
        int attackValue = (int) (getHealthAmount() * 0.1);
        int defenseValue = random.nextInt(enemy.getDefense() + 1);
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

    public int getRange(){ return 3;}

    public int getAbilityCooldown() {return abilityCooldown;}
    public int getRemainingCooldown() {return remainingCooldown;}

    public String toString(){
        return getName() + "              Health: " + getHealthAmount() +"/" + getHealthPool() +
                "           Attack: " + getAttack() + "           Defense: " + getDefense() +
                "           Level: " + getLevel() + "           Experience: " + getExperience() + "/" + getExperienceForLevel() +
                "           Cooldown: " + getRemainingCooldown() + "/" + getAbilityCooldown();
    }
}
