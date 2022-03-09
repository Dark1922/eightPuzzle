package eightpuzzle.player;

import java.util.ArrayList;
import java.util.List;

import gridgames.data.action.Action;
import gridgames.data.action.MoveAction;
import gridgames.display.Display;
import gridgames.grid.Board;

public class IterativeDeepeningSearchPlayer extends EightPuzzlePlayer {

	private boolean wasSolved;

	public IterativeDeepeningSearchPlayer(List<Action> actions, Display display, Board board) {
		super(actions, display, board);
	}

	@Override
	public Action getAction() {

		int currentDepth = 0;
		int depthLimit = 1;

		// List<Action> possibleMoves = getEmptyCellNeighbors();

		// continue incrementando depth e depthLimit até encontrarmos uma solução

		while (wasSolved == false) // se for vdd n sera mais executado
		{
			wasSolved = solvePuzzle(currentDepth, depthLimit);// incrementar depthLimit
			if (!wasSolved) {
				resetToInitialState();
			}
			depthLimit++;
		}

		return getNextPlannedAction();
	}

	private boolean solvePuzzle(int currentDepth, int depthLimit) {

		wasSolved = isGoalReached();

		if (wasSolved) {
			return wasSolved;
		}

		List<Action> possibleMoves = getEmptyCellNeighbors();

		for (int i = 0; i < possibleMoves.size(); i++) {
			if (wasSolved) {
				break;
			}

			moveEmptyCell(possibleMoves.get(i));
			addPlannedAction(possibleMoves.get(i));
			incrementNumActionsExecuted();
			currentDepth++;
			wasSolved = isGoalReached();

			if (wasSolved == true) {
				return wasSolved;
			}
			if (currentDepth == depthLimit) {
				undoMoveAndRemovePlannedAction();
				currentDepth--;
			} else if (currentDepth != depthLimit) {
				solvePuzzle(currentDepth, depthLimit);
				if (!wasSolved) {
					currentDepth--;
					undoMoveAndRemovePlannedAction();
				}
			}
		}
		return this.wasSolved;
	}

	private List<Action> getEmptyCellNeighbors() {
		List<Action> actions = new ArrayList<Action>();

		int currentRow = getEmptyCell().getRow();
		int currentCol = getEmptyCell().getCol();

		int maxRows = getBoard().getNumRows();
		int maxCols = getBoard().getNumCols();

		if (currentRow - 1 >= 0) {
			actions.add(MoveAction.UP);
		}
		if (currentCol + 1 < maxCols) {
			actions.add(MoveAction.RIGHT);
		}
		if (currentRow + 1 < maxRows) {
			actions.add(MoveAction.DOWN);
		}
		if (currentCol - 1 >= 0) {
			actions.add(MoveAction.LEFT);
		}

		return actions;
	}

	private void undoMoveAndRemovePlannedAction() {
		List<Action> actionToRemove = getPlannedActions();
		Action actionToUndo;

		actionToUndo = actionToRemove.remove(actionToRemove.size() - 1);

		if (actionToUndo.getDescription().toUpperCase().equals("UP")) {
			moveEmptyCell(MoveAction.DOWN);
		} else if (actionToUndo.getDescription().toUpperCase().equals("DOWN")) {
			moveEmptyCell(MoveAction.UP);
		} else if (actionToUndo.getDescription().toUpperCase().equals("LEFT")) {
			moveEmptyCell(MoveAction.RIGHT);
		} else {
			moveEmptyCell(MoveAction.LEFT);
		}
	}
}