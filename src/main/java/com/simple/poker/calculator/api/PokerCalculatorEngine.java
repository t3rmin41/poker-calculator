package com.simple.poker.calculator.api;

import com.simple.poker.calculator.entity.Hand;

public interface PokerCalculatorEngine {

  public final int POKER_CARD_COUNT = 5;

  static final int FIRST_PLAYER_ID = 1;
  static final int SECOND_PLAYER_ID = 2;
  static final int DRAW_ID = 0;
    
  Hand calculateHand(Hand hand);
  
  boolean isRepeatable(Hand hand);
 
  void calculateRepeatable(Hand hand);
  
  void calculateNonRepeatable(Hand hand);
  
  boolean isStraight(Hand hand);
  
  boolean isFlush(Hand hand);
  
  boolean isFullHouse(Hand hand);
  
  int getQuadsRank(Hand hand);

  int getTripsRank(Hand hand);
  
  int getPairCount(Hand hand);
  
  int getFullHousePairRank(Hand hand);
  
  int getTripsHigherKickerRank(Hand hand);
  
  int getTripsLowerKickerRank(Hand hand);
  
  int getTwoPairHigherPairRank(Hand hand);
  
  int getTwoPairLowerPairRank(Hand hand);
  
  int getOnePairRank(Hand hand);
  
  int getQuadsKickerRank(Hand hand);
  
  int getTwoPairKickerRank(Hand hand);

  int compareKickerRank(Hand firstHand, Hand secondHand);
  
  int compareOnePairKickerRank(Hand firstHand, Hand secondHand);
  
  int returnWinner(Hand firstHand, Hand secondHand);

}
