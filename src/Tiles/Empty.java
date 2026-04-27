package Tiles;

import Enemies.Enemy;
import Players.Player;

public class Empty extends Tile {

    public Empty(Position position) {
        super('.', position);
    }

    @Override
    public boolean accept(Player player) { return true;}

    @Override
    public boolean accept(Enemy enemy) { return true;}

    @Override
    public boolean visit(Player player) {
        return true;
    }


}