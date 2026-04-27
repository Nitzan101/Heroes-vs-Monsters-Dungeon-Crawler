package Tiles;

import Enemies.Enemy;
import Players.Player;

public class Wall extends Tile {


    public Wall(Position position) {
        super('#', position);
    }

    @Override
    public boolean accept(Player player) { return false; }

    @Override
    public boolean accept(Enemy enemy) { return false;}

    @Override
    public boolean visit(Player player) {
        return false;
    }

}
