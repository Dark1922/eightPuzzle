package eightpuzzle.data;

import gridgames.data.item.Item;

public enum EightPuzzleItem implements Item {
	ONE,
	TWO,
	THREE,
	FOUR,
	FIVE,
	SIX,
	SEVEN,
	EIGHT, 
	EMPTY;
	
	public static EightPuzzleItem[] EIGHT_PUZZLE_ITEMS = {ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, EMPTY};
	
	@Override
	public boolean isHiddenItem() {
		return false;
	}
	
	@Override
    public String toString() {
        if(this.equals(ONE)) {
            return "1";
        } else if(this.equals(TWO)) {
        	return "2";
        } else if(this.equals(THREE)) {
            return "3";
        } else if(this.equals(FOUR)) {
        	return "4";
        } else if(this.equals(FIVE)) {
            return "5";
        } else if(this.equals(SIX)) {
        	return "6";
        } else if(this.equals(SEVEN)) {
            return "7";
        } else if(this.equals(EIGHT)) {
        	return "8";
        } else {
            return "";
        }
    }
}
