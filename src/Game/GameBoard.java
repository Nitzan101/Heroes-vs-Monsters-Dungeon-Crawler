package Game;

import Tiles.Tile;

public class GameBoard {

    private char[][] board;
    private Tile[][] tiles;

    public GameBoard(char[][] board) {
        this.board = board;
        tiles = new Tile[board.length][board[0].length];
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length)
            return null;
        return tiles[x][y];
    }

    public void setChar (int x, int y, char ch) {
        board[x][y] = ch;
    }
    public void setTile(int x, int y, Tile tile) {
        tiles[x][y] = tile;
        board[x][y] = tile.getTile();
        tile.getPosition().setPosition(x, y);
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

}
