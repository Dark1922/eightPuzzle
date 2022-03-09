package eightpuzzle.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import eightpuzzle.data.EightPuzzleItem;
import gridgames.data.action.Action;
import gridgames.data.action.MoveAction;
import gridgames.display.ConsoleDisplay;
import gridgames.grid.Board;

public class AStarSearchPlayerTest {

	private AStarSearchPlayer player;
	private Board board;
	private ConsoleDisplay display;

	@Before
	public void setUp() throws Exception {
		display = new ConsoleDisplay();
	}


	
	@Test
	public void testSolvedEightPuzzle() {
		try {
			createSolvedEightPuzzle();
			Action action = player.getAction();
			assertTrue("player should be reporting reached goal but it's not", player.isGoalReached());
			assertNull("returned action should be null but it's not", action);
			assertEquals("player's planned actions should be empty but it's not", 0, player.getPlannedActions().size());
			assertEquals("player's number of executed actions should be 0 but it's not", 0, player.getNumActionsExecuted());
		} catch (Exception e) {
			e.printStackTrace();
			fail("check the console for the exception stack trace");
		}
	}
	
	@Test
	public void testEightPuzzleOne() {
		try {
			createEightPuzzleOne();
			Action action = player.getAction();
			assertTrue("player should be reporting reached goal but it's not", player.isGoalReached());
			assertEquals("returned action should be DOWN but it's not", MoveAction.DOWN, action);
			assertEquals("player's planned actions size should be 1 but it's not", 1, player.getPlannedActions().size());
			assertEquals("player's number of executed actions should be 1 but it's not", 1, player.getNumActionsExecuted());
		} catch (Exception e) {
			e.printStackTrace();
			fail("check the console for the exception stack trace");
		}
	}
	
	@Test
	public void testEightPuzzleTwo() {
		try {
			createEightPuzzleTwo();
			Action action = player.getAction();
			assertTrue("player should be reporting reached goal but it's not", player.isGoalReached());
			assertEquals("returned action should be RIGHT but it's not", MoveAction.RIGHT, action);
			assertEquals("player's planned actions size should be 2 but it's not", 2, player.getPlannedActions().size());
			assertEquals("player's number of executed actions should be 6 but it's not", 6, player.getNumActionsExecuted());
			
			action = player.getAction();
			assertEquals("returned action should be DOWN but it's not", MoveAction.DOWN, action);
		} catch (Exception e) {
			e.printStackTrace();
			fail("check the console for the exception stack trace");
		}
	}
	
	@Test
	public void testEightPuzzleThree() {
		try {
			createEightPuzzleThree();
			Action action = player.getAction();
			assertTrue("player should be reporting reached goal but it's not", player.isGoalReached());
			assertEquals("returned action should be UP but it's not", MoveAction.UP, action);
			assertEquals("player's planned actions size should be 3 but it's not", 3, player.getPlannedActions().size());
			assertEquals("player's number of executed actions should be 17 but it's not", 17, player.getNumActionsExecuted());
			
			action = player.getAction();
			assertEquals("returned action should be RIGHT but it's not", MoveAction.RIGHT, action);
			
			action = player.getAction();
			assertEquals("returned action should be DOWN but it's not", MoveAction.DOWN, action);
		} catch (Exception e) {
			e.printStackTrace();
			fail("check the console for the exception stack trace");
		}
	}
	
	
	/*
	 *  1  2  3
	 *  4  5  6
	 *  7  8  _
	 */
	private void createSolvedEightPuzzle() {
		board = new Board(3,3);
		board.getCell(0,0).add(EightPuzzleItem.ONE);
		board.getCell(0,1).add(EightPuzzleItem.TWO);
		board.getCell(0,2).add(EightPuzzleItem.THREE);
		board.getCell(1,0).add(EightPuzzleItem.FOUR);
		board.getCell(1,1).add(EightPuzzleItem.FIVE);
		board.getCell(1,2).add(EightPuzzleItem.SIX);
		board.getCell(2,0).add(EightPuzzleItem.SEVEN);
		board.getCell(2,1).add(EightPuzzleItem.EIGHT);
		board.getCell(2,2).add(EightPuzzleItem.EMPTY);
		player = new AStarSearchPlayer(new ArrayList<Action>(Arrays.asList(MoveAction.MOVE_ACTIONS)), display, board);
	}
	
	/*
	 *  1  2  3
	 *  4  5  _
	 *  7  8  6
	 */
	private void createEightPuzzleOne() {
		board = new Board(3,3);
		board.getCell(0,0).add(EightPuzzleItem.ONE);
		board.getCell(0,1).add(EightPuzzleItem.TWO);
		board.getCell(0,2).add(EightPuzzleItem.THREE);
		board.getCell(1,0).add(EightPuzzleItem.FOUR);
		board.getCell(1,1).add(EightPuzzleItem.FIVE);
		board.getCell(1,2).add(EightPuzzleItem.EMPTY);
		board.getCell(2,0).add(EightPuzzleItem.SEVEN);
		board.getCell(2,1).add(EightPuzzleItem.EIGHT);
		board.getCell(2,2).add(EightPuzzleItem.SIX);
		player = new AStarSearchPlayer(new ArrayList<Action>(Arrays.asList(MoveAction.MOVE_ACTIONS)), display, board);
	}
	
	/*
	 *  1  2  3
	 *  4  _  5
	 *  7  8  6
	 */
	private void createEightPuzzleTwo() {
		board = new Board(3,3);
		board.getCell(0,0).add(EightPuzzleItem.ONE);
		board.getCell(0,1).add(EightPuzzleItem.TWO);
		board.getCell(0,2).add(EightPuzzleItem.THREE);
		board.getCell(1,0).add(EightPuzzleItem.FOUR);
		board.getCell(1,1).add(EightPuzzleItem.EMPTY);
		board.getCell(1,2).add(EightPuzzleItem.FIVE);
		board.getCell(2,0).add(EightPuzzleItem.SEVEN);
		board.getCell(2,1).add(EightPuzzleItem.EIGHT);
		board.getCell(2,2).add(EightPuzzleItem.SIX);
		player = new AStarSearchPlayer(new ArrayList<Action>(Arrays.asList(MoveAction.MOVE_ACTIONS)), display, board);
	}
	
	/*
	 *  1  2  3
	 *  4  8  5
	 *  7  _  6
	 */
	private void createEightPuzzleThree() {
		board = new Board(3,3);
		board.getCell(0,0).add(EightPuzzleItem.ONE);
		board.getCell(0,1).add(EightPuzzleItem.TWO);
		board.getCell(0,2).add(EightPuzzleItem.THREE);
		board.getCell(1,0).add(EightPuzzleItem.FOUR);
		board.getCell(1,1).add(EightPuzzleItem.EIGHT);
		board.getCell(1,2).add(EightPuzzleItem.FIVE);
		board.getCell(2,0).add(EightPuzzleItem.SEVEN);
		board.getCell(2,1).add(EightPuzzleItem.EMPTY);
		board.getCell(2,2).add(EightPuzzleItem.SIX);
		player = new AStarSearchPlayer(new ArrayList<Action>(Arrays.asList(MoveAction.MOVE_ACTIONS)), display, board);
	}
}