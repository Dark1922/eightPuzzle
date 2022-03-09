package eightpuzzle;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import eightpuzzle.player.AStarSearchPlayer;
import eightpuzzle.player.EightPuzzlePlayer;
import eightpuzzle.player.IterativeDeepeningSearchPlayer;
import gridgames.data.action.Action;
import gridgames.data.action.MoveAction;
import gridgames.display.ConsoleDisplay;
import gridgames.display.Display;
import gridgames.grid.Board;
import gridgames.player.HumanPlayer;
import gridgames.player.Player;

public class Main {
	
    public static void main(String[] args) {
    	List<Action> allActions = Arrays.asList(MoveAction.MOVE_ACTIONS);
    	runOnConsole(allActions);
    }
    
    public static void runOnConsole(List<Action> allActions) {
    	Scanner scanner = new Scanner(System.in);
    	Display display = new ConsoleDisplay();
        String choice;
        Player player = null;
        EightPuzzle game = null;
        
        do {
        	do {
        		System.out.print("Want to play [yes, no]: ");
        		choice = scanner.next().toLowerCase();
        	} while(!choice.equals("yes") && !choice.equals("no"));
        	if("yes".equals(choice)) {
        		game = new EightPuzzle(display, 3);
        	}else if("no".equals(choice)) {
        		System.out.println("Restart to play again");
        		break;
        	}
        	player = getPlayer(scanner, game);
            do {
                game.play(player);
				System.out.print("congratulations , Play again? [yes, no]: ");
                choice = scanner.next().toLowerCase();
            } while(!choice.equals("yes") && !choice.equals("no"));
        } while(choice.equals("yes"));
        scanner.close();
    }
    
    private static Player getPlayer(Scanner scanner, EightPuzzle game) {
    	List<Action> actions = MoveAction.getAllActions();
    	Display display = game.getDisplay();
    	Board board = game.getBoard();
    	Player player = null;
    	String choice;
    	 do {
             System.out.print("Human play or computer play? [HUMAN, COMPUTER]: ");
             choice = scanner.next().toLowerCase();
         } while(!choice.equals("human") && !choice.equals("computer"));
    	 
    	 if(choice.equals("human")) { 
    		 EightPuzzlePlayer eightPuzzlePlayer = new EightPuzzlePlayer(actions, display, board);
    		 player = new HumanPlayer(eightPuzzlePlayer, scanner);
         } else {
        	 do {
                 System.out.print("Unformed or informed agent? [UNINFORMED, INFORMED]: ");
                 choice = scanner.next().toLowerCase();
             } while(!choice.equals("uninformed") && !choice.equals("informed"));
             if(choice.equals("uninformed")) {
                 player = new IterativeDeepeningSearchPlayer(actions, display, board);
             } else {
                player = new AStarSearchPlayer(actions, display, board);
             }
         }
    	return player;
    }
}