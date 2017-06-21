package com.simple.poker.calculator.entity;

import java.io.Serializable;

import com.simple.poker.calculator.api.PokerCalculatorEngine;
import com.simple.poker.calculator.impl.PokerCalculatorEngineImpl;

public class HandContainer implements Serializable {

  private static final int FIRST_PLAYER_ID = 1;
  private static final int SECOND_PLAYER_ID = 2;
  
  private int id;
  private Hand firstPlayerHand;
  private Hand secondPlayerHand;
  private int winner = 0;
  private boolean finished;
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public Hand getFirstPlayerHand() {
    return firstPlayerHand;
  }
  public void setFirstPlayerHand(Hand firstPlayerHand) {
    this.firstPlayerHand = firstPlayerHand;
  }
  public Hand getSecondPlayerHand() {
    return secondPlayerHand;
  }
  public void setSecondPlayerHand(Hand secondPlayerHand) {
    this.secondPlayerHand = secondPlayerHand;
  }
  
  public void defineWinner() {
      winner = returnWinner();
  }
  
  public int getWinner() {
      return winner;
  }

  @Override
  public String toString() {
    String stringified = "[";
    for (Card card : firstPlayerHand.getCards()) {
      stringified += card.getRank()+card.getColor()+";";
    }
    stringified += "(repeatable = "+firstPlayerHand.isRepeatable()+";strength="+firstPlayerHand.getStrength()+")";

    stringified += "] [";
    for (Card card : secondPlayerHand.getCards()) {
      stringified += card.getRank()+card.getColor()+";";
    }
    stringified += "(repeatable = "+secondPlayerHand.isRepeatable()+";strength="+secondPlayerHand.getStrength()+")";
    stringified += "|winner = "+winner+"]";
    return stringified;
  }
  
  private int returnWinner() {
      if (firstPlayerHand.getStrength().getRating() != secondPlayerHand.getStrength().getRating()) {
          return firstPlayerHand.getStrength().getRating() > secondPlayerHand.getStrength().getRating() ? FIRST_PLAYER_ID : SECOND_PLAYER_ID;
      }
      PokerCalculatorEngine engine = new PokerCalculatorEngineImpl();
      switch (firstPlayerHand.getStrength()) {
        case STRAIGHT_FLUSH :    return firstPlayerHand.getCards().get(4).getRankFormatted() > secondPlayerHand.getCards().get(4).getRankFormatted() ? FIRST_PLAYER_ID : SECOND_PLAYER_ID;
        case QUADS          : if (engine.getQuadsRank(firstPlayerHand) > engine.getQuadsRank(secondPlayerHand)) {
                                 return FIRST_PLAYER_ID;
                              } else if (engine.getQuadsRank(firstPlayerHand) < engine.getQuadsRank(secondPlayerHand)) {
                                return SECOND_PLAYER_ID;
                              } else if (engine.getQuadsKickerRank(firstPlayerHand) > engine.getQuadsKickerRank(secondPlayerHand)) {
                                return FIRST_PLAYER_ID;
                              }
                              return SECOND_PLAYER_ID;
        case FULL_HOUSE     : if (engine.getTripsRank(firstPlayerHand) > engine.getTripsRank(secondPlayerHand)) {
                                return FIRST_PLAYER_ID;
                              } else if (engine.getTripsRank(firstPlayerHand) < engine.getTripsRank(secondPlayerHand)) {
                                return SECOND_PLAYER_ID;
                              } else if (engine.getFullHousePairRank(firstPlayerHand) > engine.getFullHousePairRank(secondPlayerHand)) {
                                return FIRST_PLAYER_ID;
                              }
                              return SECOND_PLAYER_ID;
        case FLUSH          : 
        case STRAIGHT       : return firstPlayerHand.getCards().get(4).getRankFormatted() > secondPlayerHand.getCards().get(4).getRankFormatted() ? FIRST_PLAYER_ID : SECOND_PLAYER_ID;
        case TRIPS          : if (engine.getTripsRank(firstPlayerHand) > engine.getTripsRank(secondPlayerHand)) {
                                return FIRST_PLAYER_ID;
                              } else if (engine.getTripsRank(firstPlayerHand) < engine.getTripsRank(secondPlayerHand)) {
                                return SECOND_PLAYER_ID;
                              } else if (engine.getTripsHigherKickerRank(firstPlayerHand) > engine.getTripsHigherKickerRank(secondPlayerHand)) {
                                return FIRST_PLAYER_ID;
                              } else if (engine.getTripsHigherKickerRank(firstPlayerHand) < engine.getTripsHigherKickerRank(secondPlayerHand)) {
                                return SECOND_PLAYER_ID;
                              } 
        case TWO_PAIR       : if (engine.getTwoPairHigherPairRank(firstPlayerHand) > engine.getTwoPairHigherPairRank(secondPlayerHand)) {
                                return FIRST_PLAYER_ID;
                              } else if (engine.getTwoPairHigherPairRank(firstPlayerHand) < engine.getTwoPairHigherPairRank(secondPlayerHand)) {
                                return SECOND_PLAYER_ID;
                              } else if (engine.getTwoPairLowerPairRank(firstPlayerHand) > engine.getTwoPairLowerPairRank(secondPlayerHand)) {
                                return FIRST_PLAYER_ID;
                              } else if (engine.getTwoPairLowerPairRank(firstPlayerHand) < engine.getTwoPairLowerPairRank(secondPlayerHand)) {
                                return SECOND_PLAYER_ID;
                              }
                              return engine.compareTwoPairKickerRank(firstPlayerHand, secondPlayerHand);
        case ONE_PAIR       : 
        case HIGH_CARD      : 
      }
      return 0;
  }

  public boolean isFinished() {
    return finished;
  }
  public void setFinished(boolean finished) {
    this.finished = finished;
  }

}
