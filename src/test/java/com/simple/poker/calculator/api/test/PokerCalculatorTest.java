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
    public void givenRepeatableShouldReturnRepeatable() {
      Hand repeatable = new Hand();
      Card[] cards = {new Card("AS"), new Card("KD"), new Card("7S"), new Card("2H"), new Card("7H")};
      repeatable.getCards().addAll(Arrays.asList(cards));
      calc.calculateHand(repeatable);
      assertTrue(repeatable.isRepeatable());
    }
    
    @Test
    public void handStraighShouldEqualToStraight() {
        Hand straight = new Hand();
        Card[] cards = {new Card("8S"), new Card("QD"), new Card("JS"), new Card("9H"), new Card("TH")};
        straight.getCards().addAll(Arrays.asList(cards));
        calc.calculateHand(straight);
        assertEquals(HandStrength.STRAIGHT, straight.getStrength());
    }
    
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
    public void handQuadsShouldReturnQuadsRank() {
        Hand quads = new Hand();
        Card[] cards = {new Card("4S"), new Card("4D"), new Card("KS"), new Card("4H"), new Card("4H")};
        quads.getCards().addAll(Arrays.asList(cards));
        calc.calculateHand(quads);
        assertEquals(HandStrength.QUADS, quads.getStrength());
        assertEquals(4, calc.getQuadsRank(quads));
    }
    

    @Test
    public void handQuadsShouldReturnQuadsKicker() {
        Hand quads = new Hand();
        Card[] cards = {new Card("4S"), new Card("4D"), new Card("QS"), new Card("4H"), new Card("4H")};
        quads.getCards().addAll(Arrays.asList(cards));
        calc.calculateHand(quads);
        assertEquals(HandStrength.QUADS, quads.getStrength());
        assertEquals(Card.Q, calc.getQuadsKickerRank(quads));
    }
    
    @Test
    public void handTripsShouldReturnTripsRank() {
        Hand trips = new Hand();
        Card[] cards = {new Card("4S"), new Card("JD"), new Card("KS"), new Card("4H"), new Card("4H")};
        trips.getCards().addAll(Arrays.asList(cards));
        calc.calculateHand(trips);
        assertEquals(HandStrength.TRIPS, trips.getStrength());
        assertEquals(4, calc.getTripsRank(trips));
    }
    
    @Test
    public void handTripsShouldReturnHigherKickerRank() {
        Hand trips = new Hand();
        Card[] cards = {new Card("4S"), new Card("JD"), new Card("KS"), new Card("4H"), new Card("4H")};
        trips.getCards().addAll(Arrays.asList(cards));
        calc.calculateHand(trips);
        assertEquals(HandStrength.TRIPS, trips.getStrength());
        assertEquals(Card.K, calc.getTripsHigherKickerRank(trips));
    }
    
    @Test
    public void handTripsShouldReturnLowerKickerRank() {
        Hand trips = new Hand();
        Card[] cards = {new Card("4S"), new Card("JD"), new Card("KS"), new Card("4H"), new Card("4H")};
        trips.getCards().addAll(Arrays.asList(cards));
        calc.calculateHand(trips);
        assertEquals(HandStrength.TRIPS, trips.getStrength());
        assertEquals(Card.J, calc.getTripsLowerKickerRank(trips));
    }
    
    @Test
    public void handTwoPairShouldReturnHigherPairRank() {
        Hand hand = new Hand();
        Card[] cards = {new Card("4S"), new Card("JD"), new Card("KS"), new Card("4H"), new Card("JH")};
        hand.getCards().addAll(Arrays.asList(cards));
        calc.calculateHand(hand);
        assertEquals(HandStrength.TWO_PAIR, hand.getStrength());
        assertEquals(Card.J, calc.getTwoPairHigherPairRank(hand));
    }
    
    @Test
    public void handTwoPairShouldReturnLowerPairRank() {
        Hand hand = new Hand();
        Card[] cards = {new Card("4S"), new Card("JD"), new Card("KS"), new Card("4H"), new Card("JH")};
        hand.getCards().addAll(Arrays.asList(cards));
        calc.calculateHand(hand);
        assertEquals(HandStrength.TWO_PAIR, hand.getStrength());
        assertEquals(4, calc.getTwoPairLowerPairRank(hand));
    }
    
    @Test
    public void handTwoPairShouldReturnKickerRank() {
        Hand hand = new Hand();
        Card[] cards = {new Card("4S"), new Card("JD"), new Card("KS"), new Card("4H"), new Card("JH")};
        hand.getCards().addAll(Arrays.asList(cards));
        calc.calculateHand(hand);
        assertEquals(HandStrength.TWO_PAIR, hand.getStrength());
        assertEquals(Card.K, calc.getTwoPairKickerRank(hand));
    }
    
    @Test
    public void handTwoPairCompareShouldReturnHigherKickerRank() {
        Hand hand1 = new Hand();
        Card[] cards1 = {new Card("4S"), new Card("JD"), new Card("7S"), new Card("4H"), new Card("JH")};
        hand1.getCards().addAll(Arrays.asList(cards1));
        calc.calculateHand(hand1);
        
        Hand hand2 = new Hand();
        Card[] cards2 = {new Card("4S"), new Card("JD"), new Card("KS"), new Card("4H"), new Card("JH")};
        hand2.getCards().addAll(Arrays.asList(cards2));
        calc.calculateHand(hand2);
        
        assertEquals(HandStrength.TWO_PAIR, hand1.getStrength());
        assertEquals(HandStrength.TWO_PAIR, hand2.getStrength());
        assertEquals(PokerCalculator.SECOND_PLAYER_ID, calc.compareKickerRank(hand1, hand2));
    }
    
    @Test
    public void handOnePairCompareShouldReturnHigherKickerRank() {
        Hand hand1 = new Hand();
        Card[] cards1 = {new Card("4S"), new Card("JD"), new Card("7S"), new Card("3H"), new Card("JH")};
        hand1.getCards().addAll(Arrays.asList(cards1));
        calc.calculateHand(hand1);
        
        Hand hand2 = new Hand();
        Card[] cards2 = {new Card("4S"), new Card("JD"), new Card("KS"), new Card("5H"), new Card("JH")};
        hand2.getCards().addAll(Arrays.asList(cards2));
        calc.calculateHand(hand2);
        
        assertEquals(HandStrength.ONE_PAIR, hand1.getStrength());
        assertEquals(HandStrength.ONE_PAIR, hand2.getStrength());
        assertEquals(PokerCalculator.SECOND_PLAYER_ID, calc.compareOnePairKickerRank(hand1, hand2));
    }
    
    @Test
    public void handsFullHouseAndHigherFullHouseCompareShouldReturnCorrectWinner() {
        Hand hand1 = new Hand();
        Card[] cards1 = {new Card("4S"), new Card("JD"), new Card("4D"), new Card("4H"), new Card("JH")};
        hand1.getCards().addAll(Arrays.asList(cards1));
        calc.calculateHand(hand1);
        
        Hand hand2 = new Hand();
        Card[] cards2 = {new Card("4S"), new Card("TD"), new Card("4C"), new Card("4H"), new Card("TS")};
        hand2.getCards().addAll(Arrays.asList(cards2));
        calc.calculateHand(hand2);
        
        assertEquals(HandStrength.FULL_HOUSE, hand1.getStrength());
        assertEquals(HandStrength.FULL_HOUSE, hand2.getStrength());
        assertEquals(PokerCalculator.FIRST_PLAYER_ID, calc.returnWinner(hand1, hand2));
    }
}
