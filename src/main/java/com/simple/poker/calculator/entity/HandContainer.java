package com.simple.poker.calculator.entity;

import java.io.Serializable;

import com.simple.poker.calculator.api.PokerCalculator;

public class HandContainer implements Serializable {

  private int id;
  private Hand firstPlayerHand = new Hand();
  private Hand secondPlayerHand = new Hand();
  private int winner = PokerCalculator.DRAW_ID;
  private boolean finished;
  
  public int getId() {
    return this.id;
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

  public int getWinner() {
      return this.winner;
  }
  
  public void setWinner(int winner) {
      this.winner = winner;
  }
  
  public boolean isFinished() {
      return finished;
  }
  public void setFinished(boolean finished) {
      this.finished = finished;
  }

  @Override
  public String toString() {
    return firstPlayerHand+" "+secondPlayerHand+"|winner = "+winner;
  }

  /*
  public int getWinner() {
      engine.calculateHand(firstPlayerHand);
      engine.calculateHand(secondPlayerHand);
      return engine.returnWinner(firstPlayerHand, secondPlayerHand);
  }
  public void defineWinner() {
      winner = returnWinner();
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
        case FLUSH          : return engine.compareKickerRank(firstPlayerHand, secondPlayerHand);
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
                              int firstPlayerKickerRank = engine.getTwoPairKickerRank(firstPlayerHand);
                              int secondPlayerKickerRank = engine.getTwoPairKickerRank(secondPlayerHand);
                              if (firstPlayerKickerRank > secondPlayerKickerRank) {
                                return FIRST_PLAYER_ID;
                              } else if (firstPlayerKickerRank < secondPlayerKickerRank) {
                                return SECOND_PLAYER_ID;
                              } else {
                                return 0;
                              }
        case ONE_PAIR       : return engine.compareOnePairKickerRank(firstPlayerHand, secondPlayerHand);
        case HIGH_CARD      : return engine.compareKickerRank(firstPlayerHand, secondPlayerHand);
      }
      return 0;
  }
  /**/
  


}
