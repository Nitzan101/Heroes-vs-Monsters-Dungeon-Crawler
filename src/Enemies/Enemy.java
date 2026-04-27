package Enemies;

import Players.Player;
import Tiles.Bar;
import Tiles.Position;
import Tiles.Tile;
import Tiles.Unit;

import java.util.Random;

public abstract class Enemy extends Unit {

    int experienceValue;

    public Enemy(String name, char tile, Position position, int attack, int defense, int health, int experienceValue) {
        super(name, tile, position, attack, defense, health);
        this.experienceValue = experienceValue;
    }

    public int getExperienceValue() { return experienceValue;}

    public boolean visit(Player player){
        Random rand = new Random();
        int attackValue = rand.nextInt(getAttack() + 1);
        int defenseValue = rand.nextInt(player.getDefense() + 1);
        System.out.println(getName() + " rolles " + attackValue + " attack points");
        System.out.println(player.getName() + " rolles " + defenseValue + " defense points");
        if(attackValue > defenseValue) {
            int damage = attackValue - defenseValue;
            System.out.println(getName() + " dealt " + damage + " damage to " + player.getName());
            player.setHealthAmount(player.getHealthAmount() - damage);
            if (player.getHealthAmount() <= 0) {
                return true;
            }
        }
        return false;
    }


    public abstract Position gameTick(Player player);

    public boolean visit(Tile tile){ return tile.accept(this); }
    public boolean visit(Enemy enemy) { return false; }
    public boolean accept(Enemy enemy) { return  false;}
    public boolean accept(Player player) { return player.visit(this); }

}
