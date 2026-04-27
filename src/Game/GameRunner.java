package Game;

import Enemies.Enemy;
import Enemies.Monster;
import Enemies.Trap;
import Players.*;
import Tiles.Empty;
import Tiles.Position;
import Tiles.Tile;
import Tiles.Wall;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class GameRunner {

    private List<Path> levels;
    private int level;
    private GameBoard gameBoard;
    private Position position;
    private Player player;
    private ArrayList<Monster> monsters;
    private ArrayList<Trap> traps;
    private Scanner scanner = new Scanner(System.in);
    boolean gameOver = false;
    boolean winner = true;

    public GameRunner() {
        monsters = new ArrayList<>();
        traps = new ArrayList<>();
        position = new Position(0, 0);
        level = 0;
    }

    public void loadLevel(String directoryPath) {
        try {
            choosePlayer();
            levels =Files.list(Paths.get(directoryPath)).filter(Files::isRegularFile)
                    .filter(file -> file.toString().endsWith(".txt"))
                    .sorted().toList();
            if (levels.isEmpty()) {
                System.out.println("Error: No valid .txt files found.");
                return;
            }
            readFile(levels.get(level));
            loop();
        }
        catch (IOException e) {
            System.out.println("Error listing files: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void readFile(Path filePath) {
        System.out.println();
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file " + filePath.getFileName() + ": " + e.getMessage());
        }
        initializeBoard(lines);
    }

    private void initializeBoard(List<String> lines) {
        int rows = lines.size();
        int columns = lines.get(0).length();

        char[][] board = new char[rows][columns];
        for (int i = 0; i < rows; i++) {
            char[] line = lines.get(i).toCharArray();
            board[i] = line;
            int pos = lines.get(i).indexOf('@');
            if (pos != -1) {
                this.position = new Position(pos, i);
            }
        }
        this.gameBoard = new GameBoard(board);

        Tile tile;
        Position position;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                position = new Position(row, column);
                char c = board[row][column];
                if (c == '@') { tile = player; }
                else if (c == '#') { tile = new Wall(position); }
                else if (c == 'C'){
                    Monster monster = new Monster("Queen Cersei", 'C', position, 10, 10, 100, 1, 1000);
                    tile = monster;
                    monsters.add(monster);
                    }
                else if (c == 's') {
                    Monster monster = new Monster("Lannister Solider", 's', position, 8, 3, 80, 3, 25);
                    tile = monster;
                    monsters.add(monster);
                }
                else if (c == 'z') {
                    Monster monster = new Monster("Wright", 'z', position, 30, 15, 600, 3, 100);
                    tile = monster;
                    monsters.add(monster);
                }
                else if (c == 'k') {
                    Monster monster = new Monster("Lannister Knight", 'k', position, 14, 8, 200, 4, 50);
                    tile = monster;
                    monsters.add(monster);
                }
                else if (c == 'q') {
                    Monster monster = new Monster("Queen’s Guard", 'q', position, 20, 15, 400, 5, 100);
                    tile = monster;
                    monsters.add(monster);
                }
                else if (c == 'b') {
                    Monster monster = new Monster("Bear-Wright ", 'b', position, 75, 30, 1000, 4, 250);
                    tile = monster;
                    monsters.add(monster);
                }
                else if (c == 'g') {
                    Monster monster = new Monster("Giant-Wright", 'g', position, 100, 40, 1500, 5, 500);
                    tile = monster;
                    monsters.add(monster);
                }
                else if (c == 'w') {
                    Monster monster = new Monster("White Walker", 'w', position, 150, 50, 2000, 6, 1000);
                    tile = monster;
                    monsters.add(monster);
                }
                else if (c == 'M') {
                    Monster monster = new Monster("The Mountain", 'M', position, 60, 25, 1000, 6, 500);
                    tile = monster;
                    monsters.add(monster);
                }
                else if (c == 'K') {
                    Monster monster = new Monster("Night’s King ", 'K', position, 300, 150, 5000, 8, 5000);
                    tile = monster;
                    monsters.add(monster);
                }
                else if (c == 'B') {
                    Trap trap = new Trap("Bonus Trap", 'B', position, 1, 1, 1, 250, 1, 5);
                    tile = trap;
                    traps.add(trap);
                }
                else if (c == 'Q') {
                    Trap trap = new Trap("Queen’s Trap", 'Q', position, 50, 10, 250, 100, 3, 7);
                    tile = trap;
                    traps.add(trap);
                }
                else if (c == 'D') {
                    Trap trap = new Trap("Death Trap", 'D', position, 100, 20, 500, 250, 1, 10);
                    tile = trap;
                    traps.add(trap);
                }
                else { tile = new Empty(position); }
                gameBoard.setTile(row, column, tile);
            }
        }
    }

    private void choosePlayer() {

            System.out.println("Choose your character:\n");

            System.out.printf("%-3s %-20s %-10s %-8s %-8s %-8s %-12s %-10s %-10s %-6s\n",
                    "#", "Name (Class)", "Health", "Attack", "Defense", "Cooldown", "ManaPool", "ManaCost", "SpellPower", "Other");

            System.out.printf("%-3d %-20s %-10d %-8d %-8d %-8d %-12s %-10s %-10s %-6s\n",
                    1, "Jon Snow (Warrior)", 300, 30, 4, 3, "-", "-", "-", "-");
            System.out.printf("%-3d %-20s %-10d %-8d %-8d %-8d %-12s %-10s %-10s %-6s\n",
                    2, "The Hound (Warrior)", 400, 20, 6, 5, "-", "-", "-", "-");
            System.out.printf("%-3d %-20s %-10d %-8d %-8d %-8s %-12d %-10d %-10d %-6s\n",
                    3, "Melisandre (Mage)", 100, 5, 1, "-", 300, 30, 15, "5 hits, 6 rng");
            System.out.printf("%-3d %-20s %-10d %-8d %-8d %-8s %-12d %-10d %-10d %-6s\n",
                    4, "Thoros (Mage)", 250, 25, 4, "-", 150, 20, 20, "3 hits, 4 rng");
            System.out.printf("%-3d %-20s %-10d %-8d %-8d %-8s %-12s %-10s %-10s %-6s\n",
                    5, "Arya Stark (Rogue)", 150, 40, 2, "-", "-", "-", "-", "20 cost");
            System.out.printf("%-3d %-20s %-10d %-8d %-8d %-8s %-12s %-10s %-10s %-6s\n",
                    6, "Bronn (Rogue)", 250, 35, 3, "-", "-", "-", "-", "50 cost");
            System.out.printf("%-3d %-20s %-10d %-8d %-8d %-8s %-12s %-10s %-10s %-6s\n",
                    7, "Ygritte (Hunter)", 220, 30, 2, "-", "-", "-", "-", "10 arrows");

            System.out.print("\nEnter your number here: ");

        char ans = scanner.next().charAt(0);
        boolean correct = false;
        while (!correct) {
            if (ans == '1') {
                correct = true;
                player = new Warrior("Jon Snow", position, 30, 4, 300, 3);
                System.out.println("You have selected:\n" + "Jon Snow");
            } else if (ans == '2') {
                correct = true;
                player = new Warrior("The Hound", position, 20, 6, 400, 5);
                System.out.println("You have selected:\n" + "The Hound");
            } else if (ans == '3') {
                correct = true;
                player = new Mage("Melisandre", position, 5, 1, 100, 300, 30, 15, 5, 6);
                System.out.println("You have selected:\n" + "Melisandre");
            } else if (ans == '4') {
                correct = true;
                player = new Mage("Thoros of Myr", position, 25, 4, 250, 150, 20, 20, 3, 4);
                System.out.println("You have selected:\n" + "Thoros of Myr");
            } else if (ans == '5') {
                correct = true;
                player = new Rogue("Arya Stark", position, 40, 2, 150, 20);
                System.out.println("You have selected:\n" + "Arya Stark");
            } else if (ans == '6') {
                correct = true;
                player = new Rogue("Bronn", position, 35, 3, 250, 50);
                System.out.println("You have selected:\n" + "Bronn");
            } else if (ans == '7'){
                correct = true;
                player = new Hunter("Ygritte", position, 30, 2, 220, 6, 10);
                System.out.println("You have selected:\n" + "Ygritte");
            } else {
                System.out.println("pick a valid number: ");
                ans = scanner.next().charAt(0);
            }
        }
    }

    private void loop(){
        for (int i = 0; i < levels.size() && winner; i++){
            System.out.println("Level" + (level + 1));
            gameOver = false;
            while(!gameOver){
                printBoard();
                System.out.println(player);
                char c = playerTurn();
                int x = player.getPosition().getX();
                int y = player.getPosition().getY();
                int x2 = x;
                int y2 = y;
                Tile tile;
                switch(c){
                    case 'd':
                        y2 = y + 1;
                        break;
                    case 'a':
                        y2 = y - 1;
                        break;
                    case 'w':
                        x2 = x - 1;
                        break;
                    case 's':
                        x2 = x + 1;
                        break;
                    case 'e':
                        castSpecialAbility();
                        break;
                    case 'q':
                        break;
                }
                tile = gameBoard.getTile(x2, y2);
                if (tile != null) {
                    if(player.visit(tile)){
                        gameBoard.setTile(x2, y2, player);
                        gameBoard.setTile(x, y, new Empty(new Position(x, y)));
                        if (monsters.contains(tile))
                            monsters.remove(tile);
                        else if(traps.contains(tile))
                            traps.remove(tile);
                    }
                }
                gameTick();

            }
        }
    }

    private void gameTick(){
        player.gameTick();
        for(Monster monster: monsters){
            Position position = monster.gameTick(player);
            int x = position.getX();
            int y = position.getY();
            int x2 = monster.getPosition().getX();
            int y2 = monster.getPosition().getY();
            if (monster.visit(gameBoard.getTile(x, y))) {
                if (gameBoard.getTile(x, y) == player)
                {
                    playerDead();
                    break;
                }
                else{
                    gameBoard.setTile(x2, y2, new Empty(new Position(x2, y2)));
                    gameBoard.setTile(x, y, monster);
                }
            }
        }
        if (!gameOver){
            for (Trap trap: traps){
                trap.gameTick(player);
                int x = trap.getPosition().getX();
                int y = trap.getPosition().getY();
                gameBoard.setChar(x, y, trap.getTile());
                if (player.getHealthAmount() <= 0){
                    playerDead();
                    break;
                }
            }
        }
        if (monsters.isEmpty() && traps.isEmpty()) {
            gameOver = true;
            level++;
            if (level < levels.size()){
                System.out.println("You won this level!");
                readFile(levels.get(level));
            }
            else {
                System.out.println("You won the game!");
            }
        }

    }

    private void printBoard(){
        gameBoard.printBoard();
    }

    private char playerTurn(){
        char c;
        while (true) {
            c = scanner.next().charAt(0);
            if (c == 'w' || c == 's' || c =='a' || c == 'd' || c == 'e' || c == 'q') break;
            else System.out.println("Please enter a validate char.");
        }
        return c;
    }

    private void castSpecialAbility(){
        int range = player.getRange();
        List<Enemy> enemies = getEnemies(range);
        if (!enemies.isEmpty()){
            player.abilityCast(enemies);
            checkEnemies();
        }
    }

    private List<Enemy> getEnemies(int range){
        List<Enemy> output = new ArrayList<>();
        for (Monster monster: monsters) {
            double distanse = Position.distanse(player.getPosition(), monster.getPosition());
            if (distanse < range)
                output.add(monster);
        }
        for (Trap trap: traps) {
            double distanse = Position.distanse(player.getPosition(), trap.getPosition());
            if (distanse < range)
                output.add(trap);
        }
        return output;
    }

    private void checkEnemies(){
        Iterator<Monster> monsterIterator = monsters.iterator();
        while (monsterIterator.hasNext()) {
            Monster monster = monsterIterator.next();
            if (monster.getHealthAmount() <= 0) {
                monsterIterator.remove();
                Position position = monster.getPosition();
                gameBoard.setTile(position.getX(), position.getY(), new Empty(position));
            }
        }
        Iterator<Trap> trapIterator = traps.iterator();
        while (trapIterator.hasNext()) {
            Trap trap = trapIterator.next();
            if (trap.getHealthAmount() <= 0) {
                trapIterator.remove();
                Position position = trap.getPosition();
                gameBoard.setTile(position.getX(), position.getY(), new Empty(position));
            }
        }
    }

    private void playerDead(){
        gameBoard.setChar(position.getX(), position.getY(), 'X');
        printBoard();
        System.out.println("You lost!");
        gameOver = true;
        winner = false;
    }
}
