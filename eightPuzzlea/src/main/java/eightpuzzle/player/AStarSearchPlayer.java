package eightpuzzle.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eightpuzzle.data.EightPuzzleItem;
import gridgames.data.action.Action;
import gridgames.data.action.MoveAction;
import gridgames.display.Display;
import gridgames.grid.Board;

@SuppressWarnings("unused")
public class AStarSearchPlayer extends EightPuzzlePlayer {

	private boolean wasSolved;

	public AStarSearchPlayer(List<Action> actions, Display display, Board board) {
		super(actions, display, board);
	}

	@Override
	public Action getAction() {
		int currentDepth = 0;
		int depthLimit = 1;

		// continue incrementando depth e depthLimit até encontrarmos uma solução
		while (wasSolved == false) // uma vez verdade, nunca mais rodaremos isso
		{
			wasSolved = solvePuzzle(currentDepth, depthLimit);// nós só voltamos aqui para incrementar depthLimit

			if (!wasSolved) {
				resetToInitialState();
			}
			depthLimit++;
		}

		// retornamos a próxima ação planejada
		return getNextPlannedAction();
	}

	// MÉTODO RECURSIVO

	private boolean solvePuzzle(int currentDepth, int depthLimit) {

		// -----------------------------------------------------------------------
		// isso verifica se o quebra-cabeça raiz já está resolvido
		wasSolved = isGoalReached();

		if (wasSolved) {
			return wasSolved;
		}
		// -----------------------------------------------------------------------

		// obtem células vizinhas

		List<Action> possibleMoves = getEmptyCellNeighbors();

		// faça a mudança e adicione aos nossos planos de ação e incremente a
		// profundidade atual e verifique a solução

		for (int i = 0; i < possibleMoves.size(); i++) {
			if (wasSolved) {
				break;
			}
			// movimentos possíveis = getEmptyCellNeighbors();
			moveEmptyCell(possibleMoves.get(i));
			addPlannedAction(possibleMoves.get(i));
			incrementNumActionsExecuted();
			currentDepth++;
			wasSolved = isGoalReached();

			if (wasSolved == true) {
				// incrementNumActionsExecuted();
				return wasSolved;
			}
			if (currentDepth == depthLimit) {
				undoMoveAndRemovePlannedAction();
				currentDepth--;
			} else if (currentDepth != depthLimit) {
				solvePuzzle(currentDepth, depthLimit);
				// se encontramos solução, pare de avaliar
				if (!wasSolved) {
					currentDepth--;
					undoMoveAndRemovePlannedAction();
				}
			}
		}
		return this.wasSolved;
	}

	// retorna uma lista de vizinhos vazios da célula para determinar possíveis
	// movimentos para fazer

	private List<Action> getEmptyCellNeighbors() {
		List<Action> actions = new ArrayList<Action>();
		int currentRow = getEmptyCell().getRow();
		int currentCol = getEmptyCell().getCol();

		// HashMap<Integer, Action> myList = new HashMap<>();
		// HashMap<Action, Integer> myList2 = new HashMap<>(); // podemos ter valores
		// duplicados

		Integer heuristicUP = 0;
		Integer heuristicRIGHT = 0;
		Integer heuristicDOWN = 0;
		Integer heuristicLEFT = 0;

		ArrayList<Integer> list = new ArrayList<>();
		List<Action> actionList = new ArrayList<>();

		int maxRows = getBoard().getNumRows(); // deseja encontrar o máximo de linhas/colunas para não verificarmos
												// vizinhos inexistentes

		int maxCols = getBoard().getNumCols();

		if (currentRow - 1 >= 0) // verifique se UP é um vizinho
		{
			moveEmptyCell(MoveAction.UP);
			addPlannedAction(MoveAction.UP);
			heuristicUP = getHeuristicValue();
			undoMoveAndRemovePlannedAction();
			list.add(heuristicUP);
			actionList.add(MoveAction.UP);
		}
		if (currentCol + 1 < maxCols) // verifique se RIGHT é um vizinho
		{
			moveEmptyCell(MoveAction.RIGHT);
			addPlannedAction(MoveAction.RIGHT);
			heuristicRIGHT = getHeuristicValue();
			undoMoveAndRemovePlannedAction();
			list.add(heuristicRIGHT);
			actionList.add(MoveAction.RIGHT);
		}
		if (currentRow + 1 < maxRows) // verifique se DOWN é um vizinho
		{
			moveEmptyCell(MoveAction.DOWN);
			addPlannedAction(MoveAction.DOWN);
			heuristicDOWN = getHeuristicValue();
			undoMoveAndRemovePlannedAction();
			list.add(heuristicDOWN);
			actionList.add(MoveAction.DOWN);
		}
		if (currentCol - 1 >= 0) // verifique se LEFT é um vizinho
		{
			moveEmptyCell(MoveAction.LEFT);
			addPlannedAction(MoveAction.LEFT);
			heuristicLEFT = getHeuristicValue();
			undoMoveAndRemovePlannedAction();
			list.add(heuristicLEFT);
			actionList.add(MoveAction.LEFT);
		}

		int min = list.get(0); // suponha que o primeiro elemento é min
		int i = 0;
		while (!list.isEmpty()) {
			if (list.get(i) < min) {
				min = list.get(i);
			}

			if (i == list.size() - 1) {
				for (int j = 0; j < list.size(); j++) {
					if (list.get(j) == min) {
						actions.add(actionList.get(j));
						actionList.remove(j);
						list.remove(j);
					}
				}
				i = 0;
				if (!list.isEmpty()) {
					min = list.get(i);
				}
			} else {
				i++;
			}
		}
		return actions;
	}

	private int getHeuristicValue() {

		ArrayList<EightPuzzleItem> eightPuzzleItems = new ArrayList<EightPuzzleItem>(
				Arrays.asList(EightPuzzleItem.EIGHT_PUZZLE_ITEMS));

		// sabemos que sempre teremos valores para 1-3

		int movesForOne = 0;
		int movesForTwo = 0;
		int movesForThree = 0;
		int movesForFour = 0;
		int movesForFive = 0;
		int movesForSix = 0;
		int movesForSeven = 0;
		int movesForEight = 0;

		// ONDE OS VALORES DEVEM SER PARA 3x3

		int oneRow8 = (int) ((1 - 1) / 3);
		int oneCol8 = (1 - 1) % 3;
		int twoRow8 = (int) ((2 - 1) / 3);
		int twoCol8 = (2 - 1) % 3;
		int threeRow8 = (int) ((3 - 1) / 3);
		int threeCol8 = (3 - 1) % 3;

		int fourRow8 = (int) ((4 - 1) / 3);
		int fourCol8 = (4 - 1) % 3;
		int fiveRow8 = (int) ((5 - 1) / 3);
		int fiveCol8 = (5 - 1) % 3;
		int sixRow8 = (int) ((6 - 1) / 3);
		int sixCol8 = (6 - 1) % 3;
		int sevenRow8 = (int) ((7 - 1) / 3);
		int sevenCol8 = (7 - 1) % 3;
		int eightRow8 = (int) ((8 - 1) / 3);
		int eightCol8 = (8 - 1) % 3;

		int whereOneIsRow = 0;
		int whereOneIsCol = 0;
		int whereTwoIsRow = 0;
		int whereTwoIsCol = 0;
		int whereThreeIsRow = 0;
		int whereThreeIsCol = 0;
		int whereFourIsRow = 0;
		int whereFourIsCol = 0;
		int whereFiveIsRow = 0;
		int whereFiveIsCol = 0;
		int whereSixIsRow = 0;
		int whereSixIsCol = 0;
		int whereSevenIsRow = 0;
		int whereSevenIsCol = 0;
		int whereEightIsRow = 0;
		int whereEightIsCol = 0;

		// descobrir onde eles estão
		for (int row = 0; row < getBoard().getNumCols(); row++) {
			for (int col = 0; col < getBoard().getNumCols(); col++) {
				if (getBoard().getCell(row, col).contains(EightPuzzleItem.ONE)) {
					whereOneIsRow = row;
					whereOneIsCol = col;
				} else if (getBoard().getCell(row, col).contains(EightPuzzleItem.TWO)) {
					whereTwoIsRow = row;
					whereTwoIsCol = col;
				} else if (getBoard().getCell(row, col).contains(EightPuzzleItem.THREE)) {
					whereThreeIsRow = row;
					whereThreeIsCol = col;
				} else if (getBoard().getCell(row, col).contains(EightPuzzleItem.FOUR)) {
					whereFourIsRow = row;
					whereFourIsCol = col;
				} else if (getBoard().getCell(row, col).contains(EightPuzzleItem.FIVE)) {
					whereFiveIsRow = row;
					whereFiveIsCol = col;
				} else if (getBoard().getCell(row, col).contains(EightPuzzleItem.SIX)) {
					whereSixIsRow = row;
					whereSixIsCol = col;
				} else if (getBoard().getCell(row, col).contains(EightPuzzleItem.SEVEN)) {
					whereSevenIsRow = row;
					whereSevenIsCol = col;
				} else if (getBoard().getCell(row, col).contains(EightPuzzleItem.EIGHT)) {
					whereEightIsRow = row;
					whereEightIsCol = col;
				}

				// adicione mais se para os próximos 4-8 números

			}
		}

		// verifique o tamanho da placa, se col (ou linha) for maior que 2, sabemos
		// verificar 4 - 8

		int total = movesForOne + movesForTwo + movesForThree + movesForFour + movesForFive + movesForSix
				+ movesForSeven + movesForEight;
		return total;
	}

	private void undoMoveAndRemovePlannedAction() {
		List<Action> actionToRemove = getPlannedActions();
		Action actionToUndo;

		// Action undoAction = null;
		actionToUndo = actionToRemove.remove(actionToRemove.size() - 1); // espera que remova a ação anterior
																			// última ação adicionada
		// agora encontre o oposto desta ação para voltar
		
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
