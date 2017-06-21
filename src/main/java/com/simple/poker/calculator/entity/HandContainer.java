package com.simple.poker.calculator.entity;

import java.io.Serializable;

public class HandContainer implements Serializable {

  private int id;
  private Hand firstPlayerHand;
  private Hand secondPlayerHand;
  private int winner = 0;
  
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
      if (HandStrength.STRAIGHT_FLUSH.equals(firstPlayerHand.getStrength())) {
          return firstPlayerHand.getCards().get(4).getRankFormatted() > secondPlayerHand.getCards().get(4).getRankFormatted() ? 1 : 2;
      }
      if (HandStrength.QUADS.equals(firstPlayerHand.getStrength())) {
          return 0;
      }
      if (HandStrength.FULL_HOUSE.equals(firstPlayerHand.getStrength())) {
          return 0;
      }
      if (HandStrength.FLUSH.equals(firstPlayerHand.getStrength())) {
          return 0;
      }
      if (HandStrength.STRAIGHT.equals(firstPlayerHand.getStrength())) {
          return firstPlayerHand.getCards().get(4).getRankFormatted() > secondPlayerHand.getCards().get(4).getRankFormatted() ? 1 : 2;
      }
      if (HandStrength.TRIPS.equals(firstPlayerHand.getStrength())) {
          return 0;
      }
      if (HandStrength.TWO_PAIR.equals(firstPlayerHand.getStrength())) {
          return 0;
      }
      if (HandStrength.ONE_PAIR.equals(firstPlayerHand.getStrength())) {
          return 0;
      }
      if (HandStrength.HIGH_CARD.equals(firstPlayerHand.getStrength())) {
          return 0;
      }
      return 0;
  }
}
