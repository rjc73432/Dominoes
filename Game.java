import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class used to play a standard dominoes game.
 * @author carroll
 *
 */
public class Game {

	private List<Player> players;
	private Scanner input;
	private Set set;
	private Board board;
	
	/**
	 * Default constructor for Game object.
	 */
	public Game() {
		this.input = new Scanner(System.in);
		this.set = new Set();
		this.board = new Board();
		this.players = new ArrayList<>();
	}//constructor
	
	/**
	 * The main method that runs the game.
	 */
	public void play() {
		createPlayers();
		dealHands();
		
		//main loop to cycle through players in turn order	
		int i = 0;
		while(minHand() > 0) {
			takeTurn(players.get(i));	
			
			if(i == 3) {
				i = 0;
			} else {
				i++;
			}
		}//while
		
		//start endGame operations with prev denoting the player who went out first
		int prev = i - 1;
		if(prev == -1) {
			prev = 3;
		}
		endGame(players.get(prev));		
	}//play
	
	/**
	 * Prompts the user for Player names and creates two teams of two players each.
	 */
	public void createPlayers() {
		//get player names
		String p1_name = getPlayerName(1);
		String p2_name = getPlayerName(2);		
		String p3_name = getPlayerName(3);		
		String p4_name = getPlayerName(4);
	
		//create player objects
		players = new ArrayList<Player>();
		Player p1 = new Player(1, p1_name);
		Player p2 = new Player(2, p2_name);
		Player p3 = new Player(3, p3_name);
		Player p4 = new Player(4, p4_name);
		
		//add to players list and create teams
		this.players.add(p1);
		this.players.add(p2);
		this.players.add(p3);
		this.players.add(p4);
		
		System.out.println();
		System.out.println("The teams are:");
		System.out.println("Team 1: " + p1.getName() + ", " + p3.getName());
		System.out.println("Team 2: " + p2.getName() + ", " + p4.getName());
	}//createPlayers
	
	/**
	 * Deals each player their starting hand of seven dominoes.
	 */
	private void dealHands() {
		set.shuffle();
		for(Player p : players) {
			set.dealToPlayer(p,7);
		}
	}//dealHands
	
	/**
	 * Allows a player to execute prompts in order to play their turn.
	 * @param p The active player for the turn.
	 */
	private void takeTurn(Player p) {
		//Print the active player, the board, and the active player's current hand
		System.out.println();
		System.out.println(p.getName() + "'s Turn:");
		board.print();
		
		System.out.println();
		System.out.println("Your Hand: ");
		for (Domino d : p.getHand()) {
			d.print();
		}
		
		boolean done = false;
		while(!done) {
			System.out.println();
			System.out.println("Please enter a command (play, pass, hands, score)");
			String command = input.next();
			switch (command) {
			case "play":
				playDomino(p);
				done = true;
				break;
			case "pass":
				done = true;
				break;
			case "hands":
				seeHands();
				break;
			case "score":
				showScore();
				break;
			default:
				System.out.println("Sorry, unrecognized command. Please try again");
				break;
			}
		}
	}//takeTurn
	
	/**
	 * Prompts the user to determine a domino to play, and carries out its placement on the board.
	 * @param p The active player.
	 */
	private void playDomino(Player p) {
		//if first domino played
		if(board.getPlayed().size() == 0) {
			System.out.println();
			System.out.println("Please enter a domino to play (Ex. \"0-4\")");
			
			//take input and check valid
			boolean validDomino = false;
			while(!validDomino) {
			String domInput = input.next();
				List<Domino> hand = p.getHand();
				for(Domino d : hand) {
					if(d.isEqual(domInput)) {
						validDomino = true;
						board.addInitial(d);
						p.removeFromHand(d);
						p.addScore(score());
						break;
					}
				}
				if(!validDomino) {
					System.out.println("Invalid input. Please enter in the form \"1-4\" or \"3-3\", without quotes.");
				}
			}//while valid
		}
		else { //if not the first domino played
			System.out.println();
			System.out.println("Please enter a domino to play (Ex. \"0-4\")");
			
			//take input and check valid
			Domino domToPlay = new Domino();
			boolean validDomino = false;
			while(!validDomino) {
			String domInput = input.next();
				List<Domino> hand = p.getHand();
				for(Domino d : hand) {
					if(d.isEqual(domInput)) {
						validDomino = true;
						domToPlay = d;
						break;
					}
				}
				if(!validDomino) {
					System.out.println("Invalid input. Please enter in the form \"1-4\" or \"3-3\", without quotes.");
					continue;
				}
				
				//at this point we have selected a domino to play but not necessarily where to play it
				System.out.println("Select an arm to play off of (right, left, top, bot)");
				int rightArmSize = board.getRightArm().size();
				int leftArmSize = board.getLeftArm().size();
				int topArmSize = board.getTopArm().size();
				int botArmSize = board.getBotArm().size();
				
				
				
				boolean validArm = false;
				while(!validArm) {
					String armInput = input.next();
					int numToMatch;
					
					switch(armInput) {
					case "right":
						numToMatch = board.getRightArm().get(rightArmSize - 1).getSide2();
						
						//orient domino correctly if flipped
						if(domToPlay.getSide2() == numToMatch) {
							domToPlay.flip();
						}
						
						//play the domino if it fits on that arm, otherwise error message
						if(domToPlay.getSide1() == numToMatch) {
							board.addToRight(domToPlay);
							p.removeFromHand(domToPlay);
							p.addScore(score());
						} else { //if arm does not match domino
							System.out.println("Sorry, that domino doesn't go there. Please choose a domino in your hand to play.");
							validDomino = false;
							validArm = true;
							break;
						}
						validArm = true;
						break;
					case "left":
						numToMatch = board.getLeftArm().get(leftArmSize - 1).getSide1();
						
						//orient domino correctly if flipped
						if(domToPlay.getSide1() == numToMatch) {
							domToPlay.flip();
						}
						
						//play the domino if it fits on that arm, otherwise error message
						if(domToPlay.getSide2() == numToMatch) {
							board.addToLeft(domToPlay);
							p.removeFromHand(domToPlay);
							p.addScore(score());
						} else { //if arm does not match domino
							System.out.println("Sorry, that domino doesn't go there. Please choose a domino in your hand to play.");
							validDomino = false;
							validArm = true;
							break;
						}
						validArm = true;
						break;
					case "top":					
						if(board.getTopArm().size() > 0) {
							numToMatch = board.getTopArm().get(topArmSize - 1).getSide2();
						} else {
							numToMatch = -1;
						}
						
						//orient domino correctly if flipped
						if(domToPlay.getSide2() == numToMatch) {
							domToPlay.flip();
						}
						
						//check that spinner exists and is is sandwiched, meaning top plays are valid
						if(board.getSpinner() == null || rightArmSize < 2 || leftArmSize < 2) {
							System.out.println("You may not play on the top arm until both the left and right of the spinner have been played on. Please choose a domino in your hand to play.");
							validDomino = false;
							validArm = true;
							break;
						} else if(domToPlay.getSide1() == numToMatch){ //if top is a valid play							
							board.addToTop(domToPlay);
							p.removeFromHand(domToPlay);
							p.addScore(score());
						} else {
							System.out.println("Sorry, that domino doesn't go there. Please choose a domino in your hand to play.");
							validDomino = false;
							validArm = true;
							break;
						}
						validArm = true;
						break;
					case "bot":
					case "bottom":
						if(board.getBotArm().size() > 0) {
							numToMatch = board.getBotArm().get(botArmSize - 1).getSide1();
						} else {
							numToMatch = -1;
						}
						
						//orient domino correctly if flipped
						if(domToPlay.getSide1() == numToMatch) {
							domToPlay.flip();
						}
						
						//check that spinner exists and is is sandwiched, meaning bot plays are valid
						if(board.getSpinner() == null || rightArmSize < 2 || leftArmSize < 2) {
							System.out.println("You may not play on the bot arm until both the left and right of the spinner have been played on. Please choose a domino in your hand to play.");
							validDomino = false;
							validArm = true;
							break;
						} else if(domToPlay.getSide2() == numToMatch){ //if bot is a valid play							
							board.addToBot(domToPlay);
							p.removeFromHand(domToPlay);
							p.addScore(score());
						} else {
							System.out.println("Sorry, that domino doesn't go there. Please choose a domino in your hand to play.");
							validDomino = false;
							validArm = true;
							break;
						}
						validArm = true;
						break;	
					}//switch
				}//while valid arm
			}//while valid
							
		}//if else first domino or multiple
		
	}//playDomino
	
	/**
	 * Allows the player to see the number of dominoes in each opponent's hand.
	 */
	private void seeHands() {
		System.out.println();
		for(Player current : players) {
			System.out.println(current.getName() + ": " + current.getHandSize() + " dominoes in hand.");
		}
	}//seeHands
	
	/**
	 * Allows the player to see the score of both teams.
	 */
	private void showScore() {
		System.out.println();
		System.out.println("Scoreboard:");
		
		int team1Score = players.get(0).getScore() + players.get(2).getScore();
		System.out.println("Team 1 (" + players.get(0).getName() + " , " + players.get(2).getName() + "): " + team1Score);

		int team2Score = players.get(1).getScore() + players.get(3).getScore();
		System.out.println("Team 2 (" + players.get(1).getName() + " , " + players.get(3).getName() + "): " + team2Score);

	}//showScore
	
	/**
	 * Prompts the user for a player name and returns that name.
	 * @param num The numerical id (1 through 4) associated with that player.
	 * @return The player's name as input to the application.
	 */
	private String getPlayerName(int num) {
		System.out.println("Please enter the name of Player " + num + ".");
		String name = input.next();
		return name;
	}//getPlayerName
	
	/**
	 * Returns the score of the current board if the current count is a multiple of 5.
	 * @return The value of their current score, if a multiple of five. If not, returns zero.
	 */
	private int score() {
		int currentScore = board.getScore();
		
		if((currentScore % 5) == 0) {
			return currentScore;
		} else {
			return 0;
		}
	}//addScore
	
	/**
	 * Returns the minimum number of dominoes in any player's hand.
	 * @return The minumum number of dominoes a player has in their hand.
	 */
	private int minHand() {
		int min = 7;
		for(Player p : players) {
			if(p.getHandSize() < min)
				min = p.getHandSize();
		}
		return min;
	}//minHand	
	
	/**
	 * Called when a player has run out of tiles and the game has ended.
	 * @param p The player who placed the final piece.
	 */
	private void endGame(Player p) {
		System.out.println();
		System.out.println("--- Game Over. ---");
		System.out.println(p.getName() + " went out first, so they will have their opponent's hand values added to their team's score.");
		int pMod = p.getId() % 2;
		
		for(Player current : players) {
			if(p.getId() == current.getId()) {
				continue;
			} else {
				System.out.println();
				System.out.print(current.getName() + "'s hand was ");
				int handValue = 0;
				for(Domino d : current.getHand()) {
					d.print();
					handValue += d.getValue();
				}
				System.out.print(" for a total value of: " + handValue);
				System.out.print(", rounded to " + roundByFives(handValue));
					
				//if not opposite the player who went out, add current player's score to that player
				if(current.getId() % 2 != pMod) {
					p.addScore(roundByFives(handValue));
				}
			}
		}
		System.out.println();
		
		
		showScore();
	}//endGame
	
	/**
	 * Takes an integer and rounds it to the nearest multiple of five.
	 * @param num The integer to round.
	 * @return The rounded integer.
	 */
	private int roundByFives(int num) {
		int modFive = num % 5;
		
		if(modFive < 2.5) {
			return num - modFive;
		} else {
			return num + 5 - modFive;
		}
	}//roundByFives
	
}//Game class
