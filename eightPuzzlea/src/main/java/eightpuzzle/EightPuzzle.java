package eightpuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import eightpuzzle.data.EightPuzzleItem;
import eightpuzzle.player.EightPuzzlePlayer;
import gridgames.data.action.Action;
import gridgames.data.action.MoveAction;
import gridgames.display.Display;
import gridgames.game.Game;
import gridgames.grid.Board;
import gridgames.grid.Cell;
import gridgames.player.HumanPlayer;
import gridgames.player.Player;

public class EightPuzzle extends Game {
	
	public EightPuzzle(Display display, int sideLength) {
		this.board = new Board(sideLength, sideLength);
		initializeBoard();
        this.display = display;
        this.display.setBoard(board);
	}

	@Override
	public void play(Player player) {
		EightPuzzlePlayer eightPuzzlePlayer = (EightPuzzlePlayer) player.getGamePlayer(); 
		Action move;

		if(player instanceof HumanPlayer) {
			do {
				display.printState(true);
				move = player.getAction();
				eightPuzzlePlayer.moveEmptyCell(move);
			} while(!eightPuzzlePlayer.isGoalReached());
		} else {
			player.getAction();
			display.addMessage("The computer solved the puzzle in " + eightPuzzlePlayer.getPlannedActions().size() + " moves with " + eightPuzzlePlayer.getNumActionsExecuted() + " actions.");
			display.printState(true);
		}
	}

	@Override
	public void initializeBoard() {
		ArrayList<EightPuzzleItem> eightPuzzleItems = new ArrayList<EightPuzzleItem>(Arrays.asList(EightPuzzleItem.EIGHT_PUZZLE_ITEMS));
		ArrayList<Action> moveActions = new ArrayList<Action>(Arrays.asList(MoveAction.MOVE_ACTIONS));
		int sideLength = board.getNumRows();
		int i=0;
		Random r = new Random();
		
		for(int row=0; row<sideLength; row++) {
			for(int col=0; col<sideLength; col++) {
				board.getCell(row, col).add(eightPuzzleItems.get(i));
				i++;
			}
		}
		board.getCell(sideLength-1, sideLength-1).removeAll();
		board.getCell(sideLength-1, sideLength-1).add(EightPuzzleItem.EMPTY);
		Cell emptyCell = board.getCell(sideLength-1, sideLength-1);
		//shuffle cells by repeatedly moving empty cell randomly
		for(i=0; i<25; i++) {
			moveEmptyCell(moveActions.get(r.nextInt(moveActions.size())), emptyCell);
		}
	}
	
	private void moveEmptyCell(Action move, Cell emptyCell) {
		int emptyCellRow = emptyCell.getRow();
		int emptyCellCol = emptyCell.getCol();
		Cell swappedCell = null;
		
		if(MoveAction.UP.equals(move) && emptyCellRow > 0) {
			swappedCell = board.getCell(emptyCellRow-1, emptyCellCol);
		} else if(MoveAction.RIGHT.equals(move) && emptyCellCol < board.getNumCols()-1) {
			swappedCell = board.getCell(emptyCellRow, emptyCellCol+1);
		} else if(MoveAction.DOWN.equals(move) && emptyCellRow < board.getNumRows()-1) {
			swappedCell = board.getCell(emptyCellRow+1, emptyCellCol);
		} else if(MoveAction.LEFT.equals(move) && emptyCellCol > 0) {
			swappedCell = board.getCell(emptyCellRow, emptyCellCol-1);
		}
		if(swappedCell != null) {
			swapCellWithEmptyCell(swappedCell, emptyCell);
		}
	}
	
	private void swapCellWithEmptyCell(Cell otherCell, Cell emptyCell) {
		int emptyCellRow = emptyCell.getRow();
		int emptyCellCol = emptyCell.getCol();
		int otherCellRow = otherCell.getRow();
		int otherCellCol = otherCell.getCol();
		board.setCell(emptyCellRow, emptyCellCol, otherCell);
		board.setCell(otherCellRow, otherCellCol, emptyCell);
	}
}