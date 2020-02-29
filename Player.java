import java.util.List;
import java.util.ArrayList;

/**
 * Class to represent a player and their hand in the dominoes game.
 * @author carroll
 *
 */
public class Player {

	private String name;
	private List<Domino> hand;
	private int id;
	private int score;
	
	/**
	 * Default constructor for player objects.
	 * @param id The player id, a number from 1 to 4.
	 * @param name The player's name.
	 */
	public Player(int id, String name) {
		this.id = id;
		this.name = name;
		hand = new ArrayList<Domino>();
	}//constructor
	
	/**
	 * Gets the player's name.
	 * @return The player's name.
	 */
	public String getName() {
		return name;
	}//getName
	
	/**
	 * Get's the player's id.
	 * @return The player's id.
	 */
	public int getId() {
		return id;
	}//getId
	
	/**
	 * Gets the player's hand as a list of domino objects.
	 * @return The player's hand.
	 */
	public List<Domino> getHand(){
		return hand;
	}//getHand
	
	/**
	 * Gets the size of the player's hand as an integer.
	 * @return The hand size.
	 */
	public int getHandSize() {
		return hand.size();
	}//getHandSize
	
	/**
	 * Adds a domino to the player's hand.
	 * @param dom The domino to add.
	 */
	public void addToHand(Domino dom) {
		hand.add(dom);
	}//addToHand
	
	/**
	 * Removes a domino from the player's hand. 
	 * @param dom The domino to remove.
	 * @return True if a domino was removed from the hand.
	 */
	public boolean removeFromHand(Domino dom) {
		boolean removed = false;
		for(int i = 0; i < hand.size(); i++) {
			if(dom.isEqual(hand.get(i))) {
				hand.remove(i);
				removed = true;
			}
		}
		return removed;
	}//removeFromHand
	
	/**
	 * Gets the combined value of all dominoes in a player's hand.
	 * @return The player's hand value.
	 */
	public int valueOfHand() {
		int value = 0;
		
		for(Domino dom : hand) {
			value += dom.getValue();
		}
		
		return value;
	}//valueOfHand
	
	/**
	 * Prints the player object.
	 */
	public void print() {
		System.out.println("Id: " + id + ", Name: " + name);
	}//print
	
	/**
	 * Adds an amount to the player's individual score total.
	 */
	public void addScore(int added){
		score += added;
	}//addScore
	
	/**
	 * Gets the player's individual score total.
	 * @return The individual player's score.
	 */
	public int getScore() {
		return score;
	}///getScore
	
}//Player class
