// Ivan Labovich
// 2/28/22
// Blackjack Part 2
// Period 1

/*
  This program plays the casino game of blackjack using arrays
  to simulate a deck of cards. The game consists of the player 
  competing against an "AI" opponent, seeing who can get a higher 
  value on their cards without going over 21. 
*/


import java.util.*;

class BlackJack {
  
  static final String[] SUITES = { "♠", "♥", "♦", "♣" };
  static final String[] SPECIALS = { "A", "J", "Q", "K" };
  static final int BASE = 9;
  
  static final int DECK_SIZE = (SPECIALS.length + BASE) * SUITES.length;
  static final int HAND_SIZE = 5;

  static final Random SHUFFLER = new Random();
  static final Scanner scn = new Scanner(System.in);
  
  public static String[] DECK = /*shuffle(DECK_SIZE)*/ new String[DECK_SIZE];
  public static String[] playerHand = new String[HAND_SIZE];
  public static String[] dealerHand = new String[HAND_SIZE];

  public static void main(String[] args) {
    
    boolean play = true;

    while (play) {

      int bet = setup();
      
      boolean winner = playRound();
      
      displayEnding(winner, bet);

      // some code that allows for more games if the user wants
      
   /* System.out.print("\n  Want to (p)lay again or (s)top? ");
      String playAgain = scn.next();
      playAgain.toLowerCase();
      if (playAgain.charAt(0) == 'p') {
        play = true;
        System.out.print("\n");
      } else {
        play = false;
        System.out.println("Okay, game ended");
      } */
      
      play = false;

    }

  }

  // runs things that are the same no matter what at the start
  // also deals the first cards and gets the bet
  public static int setup() {

    DECK = shuffle(DECK_SIZE);
    
    useTest(scn);

    System.out.print("\n");
    lineThing();
    System.out.println("  Let's play Blackjack!");
    lineThing();

    System.out.print("\n  How much will you bet? ");
    int bet = scn.nextInt();

    playerHand = new String[HAND_SIZE];
    dealerHand = new String[HAND_SIZE];

    // the order of dealing cards took me an embarassing amount of 
    // time to figure out
    
    for (int i = 0; i < 2; i++) {
      addCard(false);
      addCard(true);
    }
      
    System.out.printf("  Dealer shows: [%s] [  ]\n\n", dealerHand[0]);

    return bet;

  }

  // plays a round, and returns the winner using checkWin()
  public static boolean playRound() {

    // false is player and true is dealer (i was lazy)
    boolean dealerBust = false;
    boolean playerBust = false;

    // player's turn

    printTurnLabel(false);
    System.out.print("\n");

    printHand(playerHand);

    String choice = "h";
    while (choice.equals("h")) {
      
      if (playerHand[HAND_SIZE - 1] == null && 
        getTotal(playerHand, false) < 21) {
        
        System.out.println("  Take a (h)it or (s)tand?");
        choice = scn.next();
        
      } else {
        choice = "s";
      }

      if (choice.equals("s") || getTotal(playerHand, false) >= 21) {
        break;
      }

      if (choice.equals("h")) {
        addCard(false);

        if (getTotal(playerHand, false) > 21) {
          playerBust = true;
        }
        printHand(playerHand);
        
      }
      
    }

    // dealer's turn

    System.out.print("\n");
    printTurnLabel(true);
    System.out.print("\n");
    
    printHand(dealerHand);

    // not sure about the exact rules for when the dealer hits but whatever,
    // it's just one integer to change it
    while (getTotal(dealerHand, true) < 17 ) {
      addCard(true);
      printHand(dealerHand);
      
      if (getTotal(dealerHand, false) > 21) {
        dealerBust = true;
        System.out.print("\n");
        return checkWin(playerBust, dealerBust);
      }
    }
    
    System.out.print("\n");
    return checkWin(playerBust, dealerBust);
  }

  // this method deals with all possible endings and aces
  // determines the winner using some parameters and the ending hands
  public static boolean checkWin(boolean playerBust, boolean dealerBust) {

    int dealer = getTotal(dealerHand, false);
    int player = getTotal(playerHand, false);

    // optimizes player's hand using aces
    // basically if the hand has an ace, and having the ace be 
    // worth 11 wont take the hand total over 21, it counts the ace as 11
    if (getTotal(playerHand, true) <= 21) {
      player = getTotal(playerHand, true);
    }
    if (getTotal(dealerHand, true) <= 21) {
      dealer = getTotal(dealerHand, true);
    }

    // theres a lot of possible outcomes
    // i tried to optimize this
      
    if (playerBust) {
      return true;
    } else if (dealerBust && !playerBust) {
      return false;
    }
    if (dealer > player) {
      return true;
    }
    if (player > dealer) {
      return false;
    }
    if (player == dealer) {
      return true; 
      // apparently rules vary for ties, so idk what is correct
      // seems like the casino would want more of a house edge, 
      // so i left it like this
    }

    return false;
    
  }

  // displays the end screen depending on the winner
  public static void displayEnding(boolean winner, int bet) {

    lineThing();
    if (winner) {
      System.out.printf("  You LOOSE!!! $%d\n", bet);
    } else {
      System.out.printf("  You win!!! $%d\n", bet * 2);
    }
    lineThing();
    
  }

  public static void lineThing() {
    System.out.println("--------------------------------------");
  }

  public static void printTurnLabel(boolean dealer) {
    lineThing();
    System.out.println(((dealer) ? "  Dealer" : "  Player") + " plays...");
    lineThing();
  }

  public static void printHand(String[] hand) {

    System.out.print("  ");

    for (int i = 0; i < hand.length; i++) {
      if (hand[i] != null) {
        System.out.printf("[%s] ", hand[i]);
      } else {
        System.out.print("     ");
      }
    }
    System.out.println(" total: " + getTotal(hand, true));

  }

  public static void addCard(boolean dealer) {

    for (int i = 0; i < HAND_SIZE; i++) {
      if (dealerHand[i] == null && dealer) {
        dealerHand[i] = dealCard(DECK);
        break;
      } else if (playerHand[i] == null && !dealer) {
        playerHand[i] = dealCard(DECK);
        break;
      }
    }

  }

  // returns the total value of a hand
  // can also be set count aces as 1 or 11
  public static int getTotal(String[] hand, boolean withAce){
    
    int total = 0;
    
    for (int i = 0; i < hand.length; i++) {
      if (hand[i] != null) {
        String value = hand[i].substring(1);
        
        try {
          total += Integer.parseInt(value);
        } catch (Exception e) {
          total += (value.equals("A")) ? (withAce) ? 11 : 1 : 10;
        }
        
      }
    }
    return total;
  }

  public static String dealCard(String[] cards) {
    String card = "";
    for (int i = 0; i < cards.length; i++) {
      card = cards[i];
      if (card.length() > 0) {
        cards[i] = "";
        return card;
      }
    }
    return card;
  }

  public static String[] shuffle(int size) {
    String[] deck = new String[size];
    for (int i = 0; i < size; i++) {
      int r = SHUFFLER.nextInt(size);
      String card = makeCard(i);
      deck[i] = makeCard(r);
      deck[r] = card;
    }

    System.out.println();
    System.out.println("THE CARDS ARE SHUFFLED!");

    return deck;
  }

  public static String makeCard(int seed) {
    int n = seed % (BASE + SPECIALS.length);
    int s = seed / (BASE + SUITES.length);

    String val = (n == 0) ? SPECIALS[n] : "" + (n + 1);
    if (n > BASE)
      val = SPECIALS[n - BASE];

    return SUITES[s] + val;
  }

  // This makes the tests work.
  public static void useTest(Scanner scn) {
    System.out.println("Want to use a test deck?");
    System.out.print("(0:looser 1:winner or skip): ");
    String t = scn.next();
    int i = 3;
    try {
      i = Integer.parseInt(t);
    } catch (NumberFormatException e) {
    }
    if (i == 0 || i == 1)
      DECK = DECK_TESTS[i];
  }

  // These are the test decks.
  static final String[][] DECK_TESTS = {
      { "♥3", "♥A", "♥9", "♠4", "♣4", "♥4", "♥5", "♦4", "♠2", "♥Q", "♣8", "♠K", "♦2", "♥2", "♣J", "♦5", "♣2", "♥K",
          "♠J", "♥10", "♠3", "♣3", "♣K", "♦3", "♦A", "♦K", "♥7", "♥8", "♦7", "♦J", "♦8", "♦Q", "♠6", "♣5", "♠10", "♠8",
          "♥6", "♣10", "♥J", "♠9", "♣9", "♦9", "♦6", "♠A", "♠Q", "♣A", "♣Q", "♦10", "♣7", "♠7", "♣6", "♠5" },
      { "♣J", "♥2", "♣2", "♥A", "♣9", "♠6", "♥4", "♥3", "♦K", "♠5", "♥K", "♦2", "♠7", "♥10", "♣8", "♥6", "♠A", "♦3",
          "♠3", "♦4", "♣3", "♣A", "♥8", "♦6", "♦A", "♠J", "♦8", "♠8", "♥Q", "♦5", "♠2", "♠4", "♠10", "♣7", "♦7", "♣Q",
          "♠K", "♠Q", "♦10", "♣5", "♦Q", "♣4", "♣10", "♥J", "♥5", "♦J", "♦9", "♥9", "♥7", "♣K", "♠9", "♣6" } };

}