package Players;

import Enemies.Enemy;
import Tiles.Position;

import java.util.List;
import java.util.Random;

public class Hunter extends Player {

    private int range;
    private int arrowsCount;
    private int tickCount;

    public Hunter(String name, Position position, int attack, int defense, int health, int range, int arrowsCount) {
        super(name, position, attack, defense, health);
        this.range = range;
        this.arrowsCount = arrowsCount;
        this.tickCount = 0;
    }

    @Override
    public void personalLevelUp() {
        arrowsCount = arrowsCount + (10 * getLevel());
        setAttack(getAttack() + (2 * getLevel()));
        setDefense(getDefense() + getLevel());
    }

    @Override
    public void abilityCast(List<Enemy> enemies) {
        if (arrowsCount > 0) {
            Enemy closest = findClosestEnemy(enemies);
            hit(closest);
            arrowsCount--;
        }
        else System.out.println("You can't cast your ability!");
    }

    private Enemy findClosestEnemy(List<Enemy> enemies) {
        Enemy closest = null;
        double closestDistance = Double.MAX_VALUE;
        for (int i = 0; i < enemies.size(); i++) {
            double range = Position.distanse(getPosition(), enemies.get(i).getPosition());
            if (range < closestDistance) {
                closestDistance = range;
                closest = enemies.get(i);
            }
        }
        return closest;
    }

    private void hit(Enemy enemy) {
        Random rand = new Random();
        int attackValue = getAttack();
        int defenseValue = rand.nextInt(enemy.getDefense() + 1);
        System.out.println(getName() + " cast Shoot.");
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


    @Override
    public void gameTick() {
        if (tickCount == 10){
            arrowsCount = arrowsCount + getLevel();
            tickCount = 0;
        }
        else
            tickCount++;
    }
    public int getArrowsCount() {
        return arrowsCount;
    }


    @Override
    public int getRange() {
        return range;
    }

    @Override
    public String toString(){
        return getName() + "              Health: " + getHealthAmount() +"/" + getHealthPool() +
                "           Attack: " + getAttack() + "           Defense: " + getDefense() +
                "           Level: " + getLevel() + "           Experience: " + getExperience() + "/" + getExperienceForLevel() +
                "           Arrows count: " + arrowsCount + "           Ticks count " + tickCount;
    }
}
