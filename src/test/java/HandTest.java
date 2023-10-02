package main.java;

import org.junit.Test;
import static org.junit.Assert.*;

public class HandTest {

    @Test
    public void testEvaluateHighCard() {
        Hand hand = new Hand();
        hand.addCard(new Card("S", "2"));
        hand.addCard(new Card("H", "4"));
        hand.addCard(new Card("D", "7"));
        hand.addCard(new Card("C", "10"));
        hand.addCard(new Card("S", "K"));

        Rank expectedRank = Rank.HIGH_CARD;
        Rank actualRank = hand.evaluateHand();
        assertEquals(expectedRank, actualRank);
    }

    @Test
    public void testEvaluateOnePair() {
        Hand hand = new Hand();
        hand.addCard(new Card("S", "2"));
        hand.addCard(new Card("H", "4"));
        hand.addCard(new Card("D", "4"));
        hand.addCard(new Card("C", "10"));
        hand.addCard(new Card("S", "K"));

        Rank expectedRank = Rank.ONE_PAIR;
        Rank actualRank = hand.evaluateHand();
        assertEquals(expectedRank, actualRank);
    }


}
