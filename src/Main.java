import Game.GameRunner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        if(args.length < 1){
            System.out.println("Error: this program needs a path to the levels directory as an argument");
            System.exit(-1);
        }
        GameRunner gameRunner = new GameRunner();
        gameRunner.loadLevel(args[0]);
    }
}