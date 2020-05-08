// Jay Jain -- 05/07/2020
// Card.java
package blackjack;

enum Suit{
    Diamonds, Spades, Clubs, Hearts
}

enum FaceValue{
    Two(2),Three(3),Four(4),Five(5), Six(6), Seven(7), Eight(8),
    Nine(9), Ten(10), Jack(10), Queen(10), King(10), Ace(11);
            
    protected final int value;

     FaceValue(int value){
        this.value = value;
    }
}

public class Card {
    protected FaceValue faceVal;    
    protected Suit suit;   
    
    public Card(Suit suit, FaceValue faceVal){
        this.suit = suit;
        this.faceVal = faceVal;        
    }
    
    public String toString(){
        return "The " + faceVal.toString() + " of " + suit.toString();
    }
}
