package Enemies;

import Players.Player;
import Tiles.Bar;
import Tiles.Position;
import Tiles.Tile;

public class Trap extends Enemy {

    private int visibilityTime;
    private int invisibilityTime;
    private int ticksCount;
    private boolean visible;
    private char regularTile;

    public Trap(String name, char tile, Position position, int attack, int defense, int health, int experienceValue, int visibilityTime, int invisibilityTime) {
        super(name, tile, position, attack, defense, health, experienceValue);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
        this.visible = true;
        this.regularTile = tile;
    }
    @Override
    public Position gameTick(Player player) {
        if (ticksCount == (visibilityTime + invisibilityTime)) {
            ticksCount = 0;
        }

        visible = ticksCount < visibilityTime;

        if (visible) {
            setTile(regularTile);
        } else {
            setTile('.');
        }

        ticksCount++;

        double distance = Position.distanse(getPosition(), player.getPosition());
        if (distance < 2) {
            visit(player);
        }
        return getPosition();
    }



    public String toString(){
        return getName() + "              Health: " + getHealthAmount() +"/" + getHealthPool() +
                "           Attack:         " + getAttack() + "         Defense: " + getDefense() +
                "           ExperienceValue:         " + getExperienceValue();
    }
}
