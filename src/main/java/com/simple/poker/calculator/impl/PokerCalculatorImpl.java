package com.simple.poker.calculator.impl;

import java.util.Arrays;
import java.util.Iterator;

import com.simple.poker.calculator.api.PokerCalculator;
import com.simple.poker.calculator.entity.Card;
import com.simple.poker.calculator.entity.Hand;
import com.simple.poker.calculator.entity.HandStrength;

public class PokerCalculatorImpl implements PokerCalculator {

  @Override
  public Hand calculateHand(Hand hand) {
    hand.arrangeHandByCardRank();
    if (isRepeatable(hand)) {
      calculateRepeatable(hand);
    } else {
      calculateNonRepeatable(hand);
    }
    return hand;
  }
    
  @Override
  public boolean isRepeatable(Hand hand) {
    for (int i = 0; i < POKER_CARD_COUNT; i++) {
      for (int j = i+1; j < POKER_CARD_COUNT; j++) {
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
     for (int i = 0; i < POKER_CARD_COUNT-1; i++) {
         if (((hand.getCards().get(i).getRankFormatted()+1) != hand.getCards().get(i+1).getRankFormatted())) {
             return false;
         }
     }
     return true;
  }

  @Override
  public boolean isFlush(Hand hand) {
    for (int i = 1; i < POKER_CARD_COUNT; i++) {
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
    if ((hand.getCards().get(2).getRankFormatted() == hand.getCards().get(0).getRankFormatted())
            && (hand.getCards().get(2).getRankFormatted() == hand.getCards().get(1).getRankFormatted())) {
          tripsRank = hand.getCards().get(2).getRankFormatted();
    }
    if ((hand.getCards().get(2).getRankFormatted() == hand.getCards().get(3).getRankFormatted())
        && (hand.getCards().get(2).getRankFormatted() == hand.getCards().get(1).getRankFormatted())) {
      tripsRank = hand.getCards().get(2).getRankFormatted();
    }
    if ((hand.getCards().get(2).getRankFormatted() == hand.getCards().get(3).getRankFormatted())
            && (hand.getCards().get(2).getRankFormatted() == hand.getCards().get(4).getRankFormatted())) {
          tripsRank = hand.getCards().get(2).getRankFormatted();
    }
    return tripsRank;
  }

  @Override
  public int getPairCount(Hand hand) {
    int count = 0;
    if (0 == getTripsRank(hand)) {
      for (int i = 0; i < POKER_CARD_COUNT; i++) {
        for (int j = i+1; j < POKER_CARD_COUNT; j++) {
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
    if (hand.getCards().get(0).getRankFormatted() == hand.getCards().get(1).getRankFormatted()
        && hand.getCards().get(0).getRankFormatted() != hand.getCards().get(2).getRankFormatted()) {
      pairRank = hand.getCards().get(0).getRankFormatted();
    } else if (hand.getCards().get(4).getRankFormatted() == hand.getCards().get(3).getRankFormatted()
               && hand.getCards().get(4).getRankFormatted() != hand.getCards().get(2).getRankFormatted()) {
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
      for (int i = 0; i < POKER_CARD_COUNT; i++) {
        for (int j = i+1; j < POKER_CARD_COUNT; j++) {
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
    if (hand.getCards().get(2).getRankFormatted() != hand.getCards().get(POKER_CARD_COUNT-1).getRankFormatted()) {
      rank = hand.getCards().get(POKER_CARD_COUNT-1).getRankFormatted();
    } else {
      rank = hand.getCards().get(1).getRankFormatted();
    }
    return rank;
  }

  @Override
  public int getTripsLowerKickerRank(Hand hand) {
    int rank = 0;
    if (hand.getCards().get(2).getRankFormatted() != hand.getCards().get(POKER_CARD_COUNT-1).getRankFormatted()) {
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
    for (int i = 0; i < POKER_CARD_COUNT; i++) {
        if (firstHand.getCards().get(i).getRankFormatted() != secondHand.getCards().get(i).getRankFormatted()) {
            return firstHand.getCards().get(i).getRankFormatted() > secondHand.getCards().get(i).getRankFormatted() ? 1 : 2;
        }
    }
    return 0;
  }

  @Override
  public int getTwoPairKickerRank(Hand hand) {
      int kickerRank = 0;
      Card[] cards = new Card[5];
      cards = hand.getCards().toArray(cards);
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
      kickerRank = hand.getCards().get(0).getRankFormatted();
      hand.getCards().clear();
      hand.getCards().addAll(Arrays.asList(cards));
      return kickerRank;
  }

  @Override
  public int compareOnePairKickerRank(Hand firstHand, Hand secondHand) {
    int playerId = 0;
    Card[] cards1 = new Card[5];
    cards1 = firstHand.getCards().toArray(cards1);
    int firstHandPairRank = getOnePairRank(firstHand);
    for (Iterator<Card> iterator = firstHand.getCards().iterator(); iterator.hasNext();) {
        if (iterator.next().getRankFormatted() == firstHandPairRank) {
            iterator.remove();
        }
    }
    Card[] cards2 = new Card[5];
    cards2 = secondHand.getCards().toArray(cards2);
    int secondHandPairRank = getOnePairRank(secondHand);
    for (Iterator<Card> iterator = secondHand.getCards().iterator(); iterator.hasNext();) {
        if (iterator.next().getRankFormatted() == secondHandPairRank) {
            iterator.remove();
        }
    }
    for (int i = 2; i > -1; i--) {
        if (firstHand.getCards().get(i).getRankFormatted() != secondHand.getCards().get(i).getRankFormatted()) {
            playerId = firstHand.getCards().get(i).getRankFormatted() > secondHand.getCards().get(i).getRankFormatted() ? PokerCalculator.FIRST_PLAYER_ID : PokerCalculator.SECOND_PLAYER_ID;
            break;
        }
    }
    firstHand.getCards().clear();
    firstHand.getCards().addAll(Arrays.asList(cards1));
    secondHand.getCards().clear();
    secondHand.getCards().addAll(Arrays.asList(cards2));
    return playerId;
  }

  @Override
  public int returnWinner(Hand firstHand, Hand secondHand) {
      firstHand = calculateHand(firstHand);
      secondHand = calculateHand(secondHand);
      if (firstHand.getStrength().getRating() != secondHand.getStrength().getRating()) {
          return firstHand.getStrength().getRating() > secondHand.getStrength().getRating() ? FIRST_PLAYER_ID : SECOND_PLAYER_ID;
      }
      switch (firstHand.getStrength()) {
        case STRAIGHT_FLUSH : return firstHand.getCards().get(4).getRankFormatted() > secondHand.getCards().get(4).getRankFormatted() ? FIRST_PLAYER_ID : SECOND_PLAYER_ID;
        case QUADS          : if (getQuadsRank(firstHand) > getQuadsRank(secondHand)) {
                                 return FIRST_PLAYER_ID;
                              } else if (getQuadsRank(firstHand) < getQuadsRank(secondHand)) {
                                return SECOND_PLAYER_ID;
                              } else if (getQuadsKickerRank(firstHand) > getQuadsKickerRank(secondHand)) {
                                return FIRST_PLAYER_ID;
                              }
                              return SECOND_PLAYER_ID;
        case FULL_HOUSE     : if (getTripsRank(firstHand) > getTripsRank(secondHand)) {
                                return FIRST_PLAYER_ID;
                              } else if (getTripsRank(firstHand) < getTripsRank(secondHand)) {
                                return SECOND_PLAYER_ID;
                              } else if (getFullHousePairRank(firstHand) > getFullHousePairRank(secondHand)) {
                                return FIRST_PLAYER_ID;
                              }
                              return SECOND_PLAYER_ID;
        case FLUSH          : return compareKickerRank(firstHand, secondHand);
        case STRAIGHT       : return firstHand.getCards().get(4).getRankFormatted() > secondHand.getCards().get(4).getRankFormatted() ? FIRST_PLAYER_ID : SECOND_PLAYER_ID;
        case TRIPS          : if (getTripsRank(firstHand) > getTripsRank(secondHand)) {
                                return FIRST_PLAYER_ID;
                              } else if (getTripsRank(firstHand) < getTripsRank(secondHand)) {
                                return SECOND_PLAYER_ID;
                              } else if (getTripsHigherKickerRank(firstHand) > getTripsHigherKickerRank(secondHand)) {
                                return FIRST_PLAYER_ID;
                              } else if (getTripsHigherKickerRank(firstHand) < getTripsHigherKickerRank(secondHand)) {
                                return SECOND_PLAYER_ID;
                              } 
        case TWO_PAIR       : if (getTwoPairHigherPairRank(firstHand) > getTwoPairHigherPairRank(secondHand)) {
                                return FIRST_PLAYER_ID;
                              } else if (getTwoPairHigherPairRank(firstHand) < getTwoPairHigherPairRank(secondHand)) {
                                return SECOND_PLAYER_ID;
                              } else if (getTwoPairLowerPairRank(firstHand) > getTwoPairLowerPairRank(secondHand)) {
                                return FIRST_PLAYER_ID;
                              } else if (getTwoPairLowerPairRank(firstHand) < getTwoPairLowerPairRank(secondHand)) {
                                return SECOND_PLAYER_ID;
                              } 
                              int firstPlayerKickerRank = getTwoPairKickerRank(firstHand);
                              int secondPlayerKickerRank = getTwoPairKickerRank(secondHand);
                              if (firstPlayerKickerRank > secondPlayerKickerRank) {
                                return FIRST_PLAYER_ID;
                              } else if (firstPlayerKickerRank < secondPlayerKickerRank) {
                                return SECOND_PLAYER_ID;
                              } else {
                                return DRAW_ID;
                              }
        case ONE_PAIR       : return compareOnePairKickerRank(firstHand, secondHand);
        case HIGH_CARD      : return compareKickerRank(firstHand, secondHand);
      }
                              return DRAW_ID;
  }

}
