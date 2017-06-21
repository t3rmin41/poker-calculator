package com.simple.poker.calculator.entity;

import java.io.Serializable;

import com.simple.poker.calculator.api.PokerCalculatorEngine;
import com.simple.poker.calculator.impl.PokerCalculatorEngineImpl;

public class HandContainer implements Serializable {

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
          return firstPlayerHand.getStrength().getRating() > secondPlayerHand.getStrength().getRating() ? 1 : 2;
      }
      PokerCalculatorEngine engine = new PokerCalculatorEngineImpl();
      switch (firstPlayerHand.getStrength()) {
        case STRAIGHT_FLUSH :    return firstPlayerHand.getCards().get(4).getRankFormatted() > secondPlayerHand.getCards().get(4).getRankFormatted() ? 1 : 2;
        case QUADS          : if (engine.getQuadsRank(firstPlayerHand) > engine.getQuadsRank(secondPlayerHand)) {
                                 return 1;
                              } else if (engine.getQuadsRank(firstPlayerHand) < engine.getQuadsRank(secondPlayerHand)) {
                                return 2;
                              } else if (engine.getQuadsKickerRank(firstPlayerHand) > engine.getQuadsKickerRank(secondPlayerHand)) {
                                return 1;
                              }
                              return 2;
        case FULL_HOUSE     : 
        case FLUSH          : 
        case STRAIGHT       : return firstPlayerHand.getCards().get(4).getRankFormatted() > secondPlayerHand.getCards().get(4).getRankFormatted() ? 1 : 2;
        case TRIPS          : 
        case TWO_PAIR       : if (engine.getTwoPairHigherPairRank(firstPlayerHand) > engine.getTwoPairHigherPairRank(secondPlayerHand)) {
                                return 1;
                              } else if (engine.getTwoPairHigherPairRank(firstPlayerHand) < engine.getTwoPairHigherPairRank(secondPlayerHand)) {
                                return 2;
                              } else if (engine.getTwoPairLowerPairRank(firstPlayerHand) > engine.getTwoPairLowerPairRank(secondPlayerHand)) {
                                return 1;
                              } else if (engine.getTwoPairLowerPairRank(firstPlayerHand) < engine.getTwoPairLowerPairRank(secondPlayerHand)) {
                                return 2;
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
