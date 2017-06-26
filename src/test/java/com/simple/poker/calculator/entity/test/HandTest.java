package com.simple.poker.calculator.entity.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.simple.poker.calculator.entity.Card;
import com.simple.poker.calculator.entity.Hand;

public class HandTest {

    @Test
    public void givenHandArrangeByRank() {
        Hand hand = new Hand();
        Card[] cards = {new Card("KS"), new Card("8D"), new Card("9S"), new Card("TC"), new Card("3D")};
        hand.getCards().addAll(Arrays.asList(cards));
        hand.arrangeHandByCardRank();
        assertEquals(Card.K, hand.getCards().get(4).getRankFormatted());
        assertTrue("K".equals(hand.getCards().get(4).getRank()));
    }
}
