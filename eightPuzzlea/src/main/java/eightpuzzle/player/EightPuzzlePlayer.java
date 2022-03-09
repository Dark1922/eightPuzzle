package eightpuzzle.player;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eightpuzzle.data.EightPuzzleItem;
import gridgames.data.action.Action;
import gridgames.data.action.MoveAction;
import gridgames.display.Display;
import gridgames.grid.Board;
import gridgames.grid.Cell;
import gridgames.player.Player;

public class EightPuzzlePlayer extends Player {
	
	private Board board;
	private Board initialStateBoard;
	private Cell emptyCell;
	private List<Action> plannedActions;
	private int currentActionIndex;

	public EightPuzzlePlayer(List<Action> actions, Display display, Board board) {
		super(actions, display);
		this.board = (Board)board.clone();
		this.initialStateBoard = (Board)board.clone();
		this.plannedActions = new ArrayList<Action>();
		display.setBoard(this.board);
		setEmptyCell();
	}
	
	private void setEmptyCell() {
		Cell cell;
		for(int row=0; row<board.getNumRows(); row++) {
			for(int col=0; col<board.getNumCols(); col++) {
				cell = board.getCell(row, col);
				if(cell.contains(EightPuzzleItem.EMPTY)) {
					emptyCell = cell;
					return;
				}
			}
		}
	}
	
	public Cell getEmptyCell() {
		return this.emptyCell;
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public void resetToInitialState() {
		this.board = (Board)this.initialStateBoard.clone();
		setEmptyCell();
		getDisplay().setBoard(board);
		this.plannedActions.clear();
	}
	
	public List<Action> getPlannedActions() {
		return this.plannedActions;
	}
	
	public Action getNextPlannedAction() {
		if(currentActionIndex < plannedActions.size()) {
			return plannedActions.get(currentActionIndex++);
		} else {
			return null;
		}
	}
	
	public void addPlannedAction(Action plannedAction) {
		plannedActions.add(plannedAction);
	}
	
	public boolean isGoalReached() {
		ArrayList<EightPuzzleItem> items = new ArrayList<EightPuzzleItem>(Arrays.asList(EightPuzzleItem.EIGHT_PUZZLE_ITEMS));
		int sideLength = board.getNumRows();
		Cell cell;
		int i=0;
		
		for(int row=0; row<sideLength; row++) {
			for(int col=0; col<sideLength; col++) {
				cell = board.getCell(row, col);
				if(!cell.contains(items.get(i))) {
					if((row == sideLength-1 && col == sideLength-1)) {
						return cell.contains(EightPuzzleItem.EMPTY); 
					}
					return false;
				}
				i++;
			}
		}
		return true;
	}
	
	public void moveEmptyCell(Action move) {
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
			swapCellWithEmptyCell(swappedCell);
		}
	}
	
	// este método é usado por moveEmptyCell (movimento de ação)
	private void swapCellWithEmptyCell(Cell otherCell) {
		int emptyCellRow = emptyCell.getRow();
		int emptyCellCol = emptyCell.getCol();
		int otherCellRow = otherCell.getRow();
		int otherCellCol = otherCell.getCol();
		board.setCell(emptyCellRow, emptyCellCol, otherCell);
		board.setCell(otherCellRow, otherCellCol, emptyCell);
	}

	//esta classe será estendida ou encapsulada por uma instância HumanPlayer
	@Override
	public Action getAction() {
		return null;
	}
}

