package Tiles;

import Enemies.Enemy;
import Players.Player;

public abstract class Tile {

    private char tile;
    private Position position;

    public Tile(char tile, Position position) {
        this.tile = tile;
        this.position = position;
    }

    public abstract boolean accept(Player player);
    public abstract boolean accept(Enemy enemy);
    public abstract boolean visit(Player player);

    public char getTile() { return tile;}
    public void setTile(char tile) { this.tile = tile;}
    public Position getPosition() { return position;}
}
