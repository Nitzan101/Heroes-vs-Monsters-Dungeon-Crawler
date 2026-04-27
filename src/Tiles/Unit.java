package Tiles;

import Enemies.Enemy;
import Players.Player;

import java.util.Random;

public abstract class Unit extends Tile {

    private String name;
    private Bar health;
    private int attackPoints;
    private int defensePoints;


    public Unit(String name, char tile, Position position, int attackPoints, int defensePoints, int health) {
        super(tile, position);
        this.name = name;
        this.attackPoints = attackPoints;
        this.defensePoints = defensePoints;
        this.health = new Bar(health);
    }

    public abstract boolean visit(Tile tile);
    public abstract boolean visit(Enemy enemy);
    public boolean visit(Empty empty) {return true; }
    public boolean visit(Wall wall) {return false; }

    public String getName() {return name;}
    public int getHealthPool(){return health.getPool();}
    public void setHealthPool(int pool){health.setPool(pool);}
    public int getHealthAmount(){return health.getAmount();}
    public void setHealthAmount(int amount){health.setAmount(amount);}
    public int getAttack() {return attackPoints;}
    public void setAttack(int attack) {this.attackPoints = attack;}
    public int getDefense() {return defensePoints;}
    public void setDefense(int defense) {this.defensePoints = defense;}
}
