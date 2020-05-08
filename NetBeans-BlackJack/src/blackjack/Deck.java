// Jay Jain -- 05/07/2020
// Deck.java
package blackjack;

import java.util.*;

public class Deck {
    protected List<Card> cards = new ArrayList<Card>();
    
    public Deck(){
        for (Suit s : Suit.values()){
            for(FaceValue f : FaceValue.values()){
                cards.add(new Card(s,f));
            }
        }
    }
    
    public Card draw(){
        Card card = cards.get(0);
        cards.remove(0);
        return card;
    }
    
    public void shuffle(){
        Random rand = new Random();
        for(int i = 0; i < cards.size();i++){
            int x = i;
            int y = rand.nextInt(cards.size());
            swapCard(x,y);
        }
    }
    
    private void swapCard(int x, int y){
        Card temp = cards.get(x);
        cards.set(x, cards.get(y));
        cards.set(y,temp);
    }

}
