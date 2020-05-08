// Jay Jain -- 05/07/2020
// Blackjack.java

package blackjack;

import java.util.Scanner;

enum EndResult{
    DealerBlackJack, PlayerBlackJack, PlayerBust, DealerBust, Tie, PlayerWin, DealerWin
}
public class Blackjack {
    private static Player player,dealer;    
    protected static Deck deck;    
    private static boolean outOfMoney = false;  
    private static Scanner scan = new Scanner(System.in);
            
    public static void main(String [] args){        
        dealer = new Player();        
        
        double amt;
        System.out.println("Welcome to Blackjack");
        System.out.println("How much money would you like to start off with: ");
        // Erorr handling for non-numerical input
        while(! scan.hasNextDouble()){            
            System.out.println("Not a number! ");            
            scan.nextLine();
            System.out.println("How much money would you like to start off with: ");
            continue;
        }
        amt = scan.nextDouble();
        player = new Player(amt);
        deal();
        optionsManager();
        while(!outOfMoney){            
            deal();
            optionsManager();                           
        }                
    }
        
    /* Takes player bet and deals a new game*/
    private static void deal(){
        System.out.println("========================================");
        System.out.println("\nNew round");
        player.placeBet();
        if(player.bet == 0 && player.balance > 0){
            System.out.println("You must place a bet before the dealer deals.");
        }else{
            dealNewGame();            
            if(player.hasBlackJack()){
                System.out.println("You win. You have blackjack.");
                endGame(EndResult.PlayerBlackJack);
                dealNewGame();
            }
        }        
    }
    
    /* Deals the initial first two cards for player and dealer*/
    private static void dealNewGame(){
        System.out.println("\nLet's start the game");
        // Create new deck and shuffle
        deck = new Deck();
        deck.shuffle();
        // Create a hand for dealer and player
        player.newHand();
        dealer.newHand();
        
        // Deal 2 cards to both player and the dealer
        for(int i = 0; i < 2; i++){
            Card c = deck.draw();
            player.hand.cards.add(c);
            
            Card d = deck.draw();
            dealer.hand.cards.add(d);
            
        }
        // Display Player cards
        displayHand(player,"Player");
        System.out.println("The hand total is: " + player.hand.sumOfHand());
        
        // Display ONLY the first Dealer card
        System.out.println("============= DEALER HAND ===============");
        System.out.println(dealer.hand.cards.get(0)); // Only show first card of dealer hand
        System.out.println("The hand total: " + dealer.hand.cards.get(0).faceVal.value);
        System.out.println("=========================================");        
    }
    
    private static EndResult getGameResults(){
        EndResult endState;
//        Dealer Blackjack
        if (dealer.hand.cards.size() == 2 && dealer.hasBlackJack()){
            endState = EndResult.DealerBlackJack;
//        Dealer bust    
        }else if (dealer.hasBust()){
            endState = EndResult.DealerBust;
//        Dealer wins    
        }else if (dealer.hand.sumOfHand() > player.hand.sumOfHand()){
            endState = EndResult.DealerWin;
//        Tie    
        }else if(dealer.hand.sumOfHand() == player.hand.sumOfHand()){
            endState = EndResult.Tie;
//        Player Win
        }else{
            endState = EndResult.PlayerWin;
        }
        return endState;
    }

    // Determines what to do after outcome of a play
    private static void endGame(EndResult endState){
        switch(endState){
            case DealerBust :
                System.out.println("Dealer bust");
                player.balance += player.bet * 2;
                System.out.println("Balance: $" + player.balance);
                break;

            case DealerBlackJack :
                System.out.println("Dealer BlackJack");                
                System.out.println("Balance: $" + player.balance);
                break;

            case DealerWin :
                System.out.println("Dealer Won");                
                System.out.println("Balance: $" + player.balance);
                break;

            case PlayerBlackJack :
                System.out.println("Blackjack!");
                player.balance += player.bet * 2.5;
                player.balance += player.bet * 2;
                System.out.println("Balance: $" + player.balance);
                break;

            case PlayerBust :
                System.out.println("You bust");
                System.out.println("Balance: $" + player.balance);
                break;

            case PlayerWin : 
                System.out.println("You won!");
                player.balance += player.bet * 2;
                System.out.println("Balance: $" + player.balance);
                break;
            
            case Tie :
                System.out.println("Tie!");                
                player.balance += player.bet;
                System.out.println("Balance: $" + player.balance);
        }
        if (player.balance <= 0){
            System.out.println("Game over. You're out of money.");
            outOfMoney = true;
        }
    } 
   
// =============================================================================
// Player action methods for stand, hit, and double down
// =============================================================================    
    
    private static void stand(){
        dealer.hit();
        displayHand(dealer,"Dealer");
        System.out.println("Dealer Hand Total: " + dealer.hand.sumOfHand());
        endGame(getGameResults());
    }
    
    private static void hit(){
//        firstTurn = false;
        player.hit();
        displayHand(player,"Player");
        if (player.hasBust()){
            System.out.println("Player Hand Total: " + player.hand.sumOfHand());
            endGame(EndResult.PlayerBust);
        }else if (player.hand.sumOfHand() < 21){
            System.out.println("Player Hand Total: " + player.hand.sumOfHand());
            optionsManager();
        }else{
            endGame(EndResult.PlayerWin);
        }
    }
    
    private static void doubleDown(){
        player.doubleDown();
        if(player.hasBust()){
            displayHand(player,"Player");
            System.out.println("Player Hand Total: " + player.hand.sumOfHand());
            displayHand(dealer,"Dealer");
            System.out.println("Dealer Hand Total: " + dealer.hand.sumOfHand());
            endGame(getGameResults());
        }else{            
            displayHand(player,"Player");
            System.out.println("Player Hand Total: " + player.hand.sumOfHand());
            displayHand(dealer,"Dealer");
            System.out.println("Dealer Hand Total: " + dealer.hand.sumOfHand());
            endGame(getGameResults());
        }
    }

// =============================================================================
// Helper methods to facilitate user interface
// =============================================================================        
    private static void optionsManager(){
        boolean invalidInput = false;
        do{
            System.out.println("What would you like to do?");
            System.out.println("[H]it, [S]tand, or [D]ouble down.");
            String input = scan.next();
            if (input.equals("H")){
                hit();
            }else if (input.equals("S")){
                stand();
            }else if(input.equals("D")){
                doubleDown();
            }else{
                System.out.println("That's invalid input. Try again.");
                invalidInput = true;
            }
        }while(invalidInput);        
    }
    
    /* Displays hand of player or dealer based on String s*/
    private static void displayHand(Player p, String s){
        if (s.equals("Player")){
            System.out.println("============= PLAYER HAND ===============");
        }else{
            System.out.println("============= DEALER HAND ===============");
        }
        for (Card c : p.hand.cards){
            System.out.println(c);
        }
    }
    
}
