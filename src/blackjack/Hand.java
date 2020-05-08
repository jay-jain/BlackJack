// Jay Jain -- 05/07/2020
// Hand.java
package blackjack;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    protected List<Card> cards = new ArrayList<Card>();
    
    public int sumOfHand(){
        int val = 0;
        int numAces = 0;
        // Add value of card to sum
        for (Card c : cards){
            if (c.faceVal == FaceValue.Ace){
                numAces++;
                val += 11;
            }else if(c.faceVal == FaceValue.Jack || c.faceVal == FaceValue.Queen || c.faceVal == FaceValue.King){
                val += 10;
            }else{
                val += c.faceVal.value;                
            }
        }
        // Determines if ace is 1 or 11
        while (val > 21 && numAces > 0){
            val -=10;
            numAces --;
        }        
        return val;
    }
}
