package Enemies;

import Players.Mage;
import Players.Player;
import Tiles.Bar;
import Tiles.Position;
import Tiles.Tile;
import Tiles.Unit;

import java.util.Random;

public class Monster extends Enemy {

    private int visionRange;

    public Monster(String name, char tile, Position position, int attack, int desfense, int health, int vision, int experienceValue) {
        super(name, tile, position, attack, desfense, health, experienceValue);
        this.visionRange = vision;
    }


    @Override
    public Position gameTick(Player player) {
        double distance = Position.distanse(player.getPosition(), getPosition());
        int x =getPosition().getX();
        int y = getPosition().getY();
        if (distance < visionRange) {
            int dx = player.getPosition().getX() - x;
            int dy = player.getPosition().getY() - y;
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    return new Position(x + 1, y);
                }
                else {
                    return new Position(x - 1, y);
                }
            }
            else{
                if (dy > 0) {
                    return new Position(x, y + 1);
                }
                else {
                    return new Position(x, y - 1);
                }
            }
        }
        else{
            Random rand = new Random();
            int direction = rand.nextInt(4);
            switch (direction) {
                case 0:
                    return new Position(x - 1, y);
                case 1:
                    return new Position(x + 1, y);
                case 2:
                    return new Position(x, y + 1);
                case 3:
                    return new Position(x, y - 1);
            }
        }
        return null;
    }

    public String toString(){
        return getName() + "              Health: " + getHealthAmount() +"/" + getHealthPool() +
                "           Attack:         " + getAttack() + "         Defense: " + getDefense() +
                "           ExperienceValue:         " + getExperienceValue() +
                "           Vision Range:         " + visionRange;
    }
}
