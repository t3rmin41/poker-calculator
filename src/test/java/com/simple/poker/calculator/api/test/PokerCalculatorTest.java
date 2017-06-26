package com.simple.poker.calculator.api.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.simple.poker.calculator.api.PokerCalculator;
import com.simple.poker.calculator.entity.Card;
import com.simple.poker.calculator.entity.Hand;
import com.simple.poker.calculator.entity.HandStrength;
import com.simple.poker.calculator.impl.PokerCalculatorImpl;

public class PokerCalculatorTest {

    private PokerCalculator calc = new PokerCalculatorImpl();
    
    @Test
    public void handStraightFlushShouldEqualToStraightFlush() {
        Hand straighflush = new Hand();
        Card[] cards = {new Card("2S"), new Card("3S"), new Card("4S"), new Card("6S"), new Card("5S")};
        straighflush.getCards().addAll(Arrays.asList(cards));
        calc.calculateHand(straighflush);
        assertEquals(HandStrength.STRAIGHT_FLUSH, straighflush.getStrength());
    }
    
    @Test
    public void handSteelWheelShouldEqualToStraighFlush() { // 'steel wheel' is straight flush from ace to 5
        Hand steelWheel = new Hand();
        Card[] cards = {new Card("2S"), new Card("3S"), new Card("AS"), new Card("4S"), new Card("5S")};
        steelWheel.getCards().addAll(Arrays.asList(cards));
        calc.calculateHand(steelWheel);
        assertEquals(HandStrength.STRAIGHT_FLUSH, steelWheel.getStrength());
    }
    
    @Test
    public void handFlushShouldEqualToFlush() {
        Hand flush = new Hand();
        Card[] cards = {new Card("2S"), new Card("3S"), new Card("7S"), new Card("JS"), new Card("KS")};
        flush.getCards().addAll(Arrays.asList(cards));
        calc.calculateHand(flush);
        assertEquals(HandStrength.FLUSH, flush.getStrength());
    }
    
    @Test
    public void handFullHouseShouldEqualToFullHouse() {
        Hand fullHouse = new Hand();
        Card[] cards = {new Card("2S"), new Card("2D"), new Card("7S"), new Card("2H"), new Card("7H")};
        fullHouse.getCards().addAll(Arrays.asList(cards));
        calc.calculateHand(fullHouse);
        assertEquals(HandStrength.FULL_HOUSE, fullHouse.getStrength());
    }
    
    @Test
    public void givenRepeatableShouldReturnRepeatable() {
      Hand repeatable = new Hand();
      Card[] cards = {new Card("AS"), new Card("KD"), new Card("7S"), new Card("2H"), new Card("7H")};
      repeatable.getCards().addAll(Arrays.asList(cards));
      calc.calculateHand(repeatable);
      assertTrue(repeatable.isRepeatable());
    }
    
}
