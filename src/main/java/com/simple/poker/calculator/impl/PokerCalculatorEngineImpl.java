package com.simple.poker.calculator.impl;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simple.poker.calculator.api.PokerCalculatorEngine;
import com.simple.poker.calculator.api.PokerHandCalculator;
import com.simple.poker.calculator.entity.Card;
import com.simple.poker.calculator.entity.Hand;
import com.simple.poker.calculator.entity.HandStrength;

public class PokerCalculatorEngineImpl implements PokerCalculatorEngine {

  @Override
  public boolean isRepeatable(Hand hand) {
    hand.arrangeHandByCardRank();
    for (int i = 0; i < PokerHandCalculator.POKER_CARD_COUNT; i++) {
      for (int j = i+1; j < PokerHandCalculator.POKER_CARD_COUNT; j++) {
        if (hand.getCards().get(i).getRankFormatted() == hand.getCards().get(j).getRankFormatted()) {
          return hand.setRepeatable(true).isRepeatable();
        }
      }
    }
    return hand.setRepeatable(false).isRepeatable();
  }

  @Override
  public void calculateRepeatable(Hand hand) {
    if (0 != getQuadsRank(hand)) {
      hand.setStrength(HandStrength.QUADS);
    } else if (isFullHouse(hand)) {
      hand.setStrength(HandStrength.FULL_HOUSE);
    } else if (0 != getTripsRank(hand)) {
      hand.setStrength(HandStrength.TRIPS);
    } else if (2 == getPairCount(hand)) {
      hand.setStrength(HandStrength.TWO_PAIR);
    } else {
      hand.setStrength(HandStrength.ONE_PAIR);
    }
  }

  @Override
  public void calculateNonRepeatable(Hand hand) {
    boolean straight = isStraight(hand);
    boolean flush = isFlush(hand);
    if (straight && flush) {
        hand.setStrength(HandStrength.STRAIGHT_FLUSH);
    } else if (straight) {
        hand.setStrength(HandStrength.STRAIGHT);
    } else if (flush) {
        hand.setStrength(HandStrength.FLUSH);
    } else {
        hand.setStrength(HandStrength.HIGH_CARD);
    }
  }

  @Override
  public boolean isStraight(Hand hand) {
    if ((hand.getCards().get(0).getRankFormatted() == 2) // special case of "A->5" straight
        && (hand.getCards().get(1).getRankFormatted() == 3)
        && (hand.getCards().get(2).getRankFormatted() == 4)
        && (hand.getCards().get(3).getRankFormatted() == 5)
        && (hand.getCards().get(4).getRankFormatted() == 14)) {
        hand.shiftRightByOnePosition();
        return true;
     }
     for (int i = 0; i < PokerHandCalculator.POKER_CARD_COUNT-1; i++) {
         if (((hand.getCards().get(i).getRankFormatted()+1) != hand.getCards().get(i+1).getRankFormatted())) {
             return false;
         }
     }
     return true;
  }

  @Override
  public boolean isFlush(Hand hand) {
    for (int i = 1; i < PokerHandCalculator.POKER_CARD_COUNT; i++) {
      if (!hand.getCards().get(0).getColor().equals(hand.getCards().get(i).getColor())) {
          return false;
      }
    }
    return true;
  }

  @Override
  public int getQuadsRank(Hand hand) {
    int quadsRank = 0;
    if ((hand.getCards().get(1).getRankFormatted() == hand.getCards().get(2).getRankFormatted())
        && (hand.getCards().get(1).getRankFormatted() == hand.getCards().get(3).getRankFormatted())
        ) {
          if ((hand.getCards().get(1).getRankFormatted() == hand.getCards().get(0).getRankFormatted())
              || (hand.getCards().get(1).getRankFormatted() == hand.getCards().get(4).getRankFormatted())) {
              quadsRank = hand.getCards().get(1).getRankFormatted();
          }
    }
    return quadsRank;
  }

  @Override
  public boolean isFullHouse(Hand hand) {
    if (0 != getTripsRank(hand)) {
      if ((hand.getCards().get(0).getRankFormatted() == hand.getCards().get(1).getRankFormatted() && (hand.getCards().get(0).getRankFormatted() != hand.getCards().get(2).getRankFormatted()))
          || (hand.getCards().get(4).getRankFormatted() == hand.getCards().get(3).getRankFormatted()) && (hand.getCards().get(4).getRankFormatted() != hand.getCards().get(2).getRankFormatted()) ) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int getTripsRank(Hand hand) {
    int tripsRank = 0;
    if ((hand.getCards().get(2).getRankFormatted() == hand.getCards().get(3).getRankFormatted())
        && (hand.getCards().get(2).getRankFormatted() == hand.getCards().get(4).getRankFormatted())) {
      tripsRank = hand.getCards().get(2).getRankFormatted();
    }
    if ((hand.getCards().get(2).getRankFormatted() == hand.getCards().get(0).getRankFormatted())
        && (hand.getCards().get(2).getRankFormatted() == hand.getCards().get(1).getRankFormatted())) {
      tripsRank = hand.getCards().get(2).getRankFormatted();
    }
    return tripsRank;
  }

  @Override
  public int getPairCount(Hand hand) {
    int count = 0;
    if (0 == getTripsRank(hand)) {
      for (int i = 0; i < PokerHandCalculator.POKER_CARD_COUNT; i++) {
        for (int j = i+1; j < PokerHandCalculator.POKER_CARD_COUNT; j++) {
          if (hand.getCards().get(i).getRankFormatted() == hand.getCards().get(j).getRankFormatted()) {
            count++;
          }
        }
      }
    }
    return count;
  }

  @Override
  public int getFullHousePairRank(Hand hand) {
    int pairRank = 0;
    if (hand.getCards().get(0).getRankFormatted() == hand.getCards().get(1).getRankFormatted()) {
      pairRank = hand.getCards().get(0).getRankFormatted();
    } else if (hand.getCards().get(4).getRankFormatted() == hand.getCards().get(3).getRankFormatted()) {
      pairRank = hand.getCards().get(4).getRankFormatted();
    }
    return pairRank;
  }

  @Override
  public int getTwoPairHigherPairRank(Hand hand) {
    return hand.getCards().get(3).getRankFormatted();
  }

  @Override
  public int getTwoPairLowerPairRank(Hand hand) {
    return hand.getCards().get(1).getRankFormatted();
  }

  @Override
  public int getOnePairRank(Hand hand) {
    int rank = 0;
    if (0 == getTripsRank(hand)) {
      for (int i = 0; i < PokerHandCalculator.POKER_CARD_COUNT; i++) {
        for (int j = i+1; j < PokerHandCalculator.POKER_CARD_COUNT; j++) {
          if (hand.getCards().get(i).getRankFormatted() == hand.getCards().get(j).getRankFormatted()) {
            rank = hand.getCards().get(i).getRankFormatted();
          }
        }
      }
    }
    return rank;
  }

  @Override
  public int getTripsHigherKickerRank(Hand hand) {
    int rank = 0;
    if (hand.getCards().get(2).getRankFormatted() != hand.getCards().get(PokerHandCalculator.POKER_CARD_COUNT-1).getRankFormatted()) {
      rank = hand.getCards().get(PokerHandCalculator.POKER_CARD_COUNT-1).getRankFormatted();
    } else {
      rank = hand.getCards().get(1).getRankFormatted();
    }
    return rank;
  }

  @Override
  public int getTripsLowerKickerRank(Hand hand) {
    int rank = 0;
    if (hand.getCards().get(2).getRankFormatted() != hand.getCards().get(PokerHandCalculator.POKER_CARD_COUNT-1).getRankFormatted()) {
      if (hand.getCards().get(2).getRankFormatted() != hand.getCards().get(0).getRankFormatted()) {
        rank = hand.getCards().get(0).getRankFormatted();
      } else {
        rank = hand.getCards().get(3).getRankFormatted();
      }
    } else {
      rank = hand.getCards().get(0).getRankFormatted();
    }
    return rank;
  }
  
  @Override
  public int getQuadsKickerRank(Hand hand) {
    if (hand.getCards().get(2).getRankFormatted() == hand.getCards().get(0).getRankFormatted()) {
      return hand.getCards().get(4).getRankFormatted();
    } else {
      return hand.getCards().get(0).getRankFormatted();
    }
  }

  @Override
  public int compareKickerRank(Hand firstHand, Hand secondHand) {
    for (int i = 0; i < PokerHandCalculator.POKER_CARD_COUNT; i++) {
        if (firstHand.getCards().get(i).getRankFormatted() != secondHand.getCards().get(i).getRankFormatted()) {
            return firstHand.getCards().get(i).getRankFormatted() > secondHand.getCards().get(i).getRankFormatted() ? 1 : 2;
        }
    }
    return 0;
  }

  @Override
  public int getTwoPairKickerRank(Hand hand) {
      int higherPairRank = getTwoPairHigherPairRank(hand);
      int lowerPairRank = getTwoPairLowerPairRank(hand);
      for (Iterator<Card> iterator = hand.getCards().iterator(); iterator.hasNext();) {
          if (iterator.next().getRankFormatted() == lowerPairRank) {
              iterator.remove();
          }
      }
      for (Iterator<Card> iterator = hand.getCards().iterator(); iterator.hasNext();) {
          if (iterator.next().getRankFormatted() == higherPairRank) {
              iterator.remove();
          }
      }
      return hand.getCards().get(0).getRankFormatted();
  }

  @Override
  public int compareOnePairKickerRank(Hand firstHand, Hand secondHand) {
    int firstHandPairRank = getOnePairRank(firstHand);
    for (Iterator<Card> iterator = firstHand.getCards().iterator(); iterator.hasNext();) {
        if (iterator.next().getRankFormatted() == firstHandPairRank) {
            iterator.remove();
        }
    }
    int secondHandPairRank = getOnePairRank(secondHand);
    for (Iterator<Card> iterator = secondHand.getCards().iterator(); iterator.hasNext();) {
        if (iterator.next().getRankFormatted() == secondHandPairRank) {
            iterator.remove();
        }
    }
    for (int i = 0; i < 3; i++) {
        if (firstHand.getCards().get(i).getRankFormatted() != secondHand.getCards().get(i).getRankFormatted()) {
            return firstHand.getCards().get(i).getRankFormatted() > secondHand.getCards().get(i).getRankFormatted() ? 1 : 2;
        }
    }
    return 0;
  }

}
