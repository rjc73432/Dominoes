/**
 * A class to represent a single domino.
 * @author carroll
 *
 */
public class Domino {
	
	//For non-pair dominos, side 1 will always be the left or bottom side, while side2 is the right or top side.
	private int side1, side2;
	
	/**
	 * Empty constructor for Domino objects. Makes a double blank domino.
	 */
	public Domino() {
		this.side1 = 0;
		this.side2 = 0;
	}//empty constructor
	
	/**
	 * Valued constructor for Domino objects with integer inputs.
	 * @param side1 The value for the first side of the domino.
	 * @param side2 The value for the second side of the domino.
	 */
	public Domino(int side1, int side2) {
		this.side1 = side1;
		this.side2 = side2;
	}//main constructor
	
	/**
	 * Gets the value of side 1 of the domino, the left or bottom side.
	 * @return Side 1 integer value.
	 */
	public int getSide1() {
		return side1;
	}//getSide1
	
	/**
	 * Gets the value of side 2 of the domino, the right or top side.
	 * @return Side 2 integer value.
	 */
	public int getSide2() {
		return side2;
	}//getSide2
	
	/**
	 * Gets the combined value of both sides of the domino.
	 * @return Combined integer value of side 1 and side 2.
	 */
	public int getValue() {
		return side1 + side2;
	}//getValue
	

	/**
	 * Determines if a domino is a double, having two equal numbered sides.
	 * @return True if domino is a double.
	 */
	public boolean isDouble() {
		return (side1 == side2) ;
	}//isDouble
	
	/**
	 * Prints the domino value to standard output.
	 */
	public void print() {
		System.out.print(side1 + "-" + side2 + " ");
	}//print
	
	/**
	 * Exchanges the values of side1 and side2.
	 */
	public boolean flip() {
		int temp = side1;
		side1 = side2;
		side2 = temp;
		return true;
	}//flip
	
	/**
	 * Determines if two dominoes are equal in type.
	 */
	public boolean isEqual(Domino other) {
		if(this.getSide1() == other.getSide1() && this.getSide2() == other.getSide2()) {
			return true;
		} else if(this.getSide1() == other.getSide2() && this.getSide2() == other.getSide1()) {
			return true;
		} else {
			return false;
		}
	}//isEqual
	
	/**
	 * Determines if a domino is equal to a string of format "0-4".
	 * @param domString The string representation of a domino.
 	 * @return True if equal, false otherwise.
	 */
	public boolean isEqual(String domString) {
		int first = Character.getNumericValue(domString.charAt(0));
		int last = Character.getNumericValue(domString.charAt(2));
		
		Domino comparison = new Domino(first, last);
		if(isEqual(comparison)) {
			return true;
		} else {
			return false;
		}
	}//isEqual (string)
	
}//Domino class
