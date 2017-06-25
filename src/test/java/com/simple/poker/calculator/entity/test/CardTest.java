package com.simple.poker.calculator.entity.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.simple.poker.calculator.entity.Card;

public class CardTest {

    @Test
    public void givenCardWhenCreateThenGetRank() {
        Card card = new Card("KD");
        assertEquals(13, card.getRankFormatted());
    }
    
    @Test
    public void givenLowerAndHigherCardsWhenCompareThenDefineCorrectly() {
        Card lowerCard = new Card("TS");
        Card higherCard = new Card("JC");
        assertTrue(higherCard.getRankFormatted() > lowerCard.getRankFormatted());
    }
}
