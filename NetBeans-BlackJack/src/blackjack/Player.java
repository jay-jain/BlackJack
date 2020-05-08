// Jay Jain -- 05/07/2020
// Player.java
package blackjack;

import java.util.Scanner;

public class Player {
    protected double balance, bet;
    protected Hand hand;    
    
    private Scanner scan = new Scanner(System.in);
    
    // Creates Player --> Used to simulate dealer
    public Player(){}
    
    // Creates Player with new hand and specified balance
    public Player(double balance ){        
        this.balance = balance;
    }    
    
    public void increaseBet(double amount){
        if(balance - (bet + amount) >= 0){
            bet += amount;
        }else{
            System.out.println("You do not have enough funds to increase your bet.");
        }
    }
    
    public void placeBet(){
        boolean validBet = true;
        do{
            System.out.println("How much would you like to bet: ");        
            while(! scan.hasNextDouble()){
                System.out.println("Not a number!");
                scan.nextLine();
                System.out.println("How much would you like to bet: ");        
                continue;
            }
            this.bet = scan.nextDouble();
            if (balance - bet >= 0){
                balance = balance - bet;
                validBet = true;
            }else{
                System.out.println("You do not have enough funds to make this bet.");
                System.out.println("Try another bet....");
                validBet = false;
            }
        }while(bet <= 0 || !validBet);
        System.out.println("Your bet is: $" + bet);
        System.out.println("Your current balance is: $" + balance);
    }
    
    public Hand newHand(){
        this.hand = new Hand();
        return hand;
    }
      
    public boolean hasBlackJack(){
        if (hand.sumOfHand() == 21){
            return true;       
        }else{
            return false;
        }
    }
    
    public boolean hasBust(){
        if(hand.sumOfHand() > 21){
            return true;
        }else{
            return false;
        }
    }
    
    public void hit(){
        Card c = Blackjack.deck.draw();
        hand.cards.add(c);
    }
    
    public void doubleDown(){
        increaseBet(bet);
        balance = balance - (bet / 2);
        hit();
    }
}
