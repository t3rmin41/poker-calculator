package com.simple.poker.calculator;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class HandContainer implements Serializable {

  private Hand firstPlayerHand;
  private Hand secondPlayerHand;
  
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

  @Override
  public String toString() {
    String stringified = "[";
    for (Card card : firstPlayerHand.getCards()) {
      stringified += card.getColor()+card.getRank()+";";
    }
    stringified += "] [";
    for (Card card : secondPlayerHand.getCards()) {
      stringified += card.getColor()+card.getRank()+";";
    }
    stringified += "]";
    return stringified;
  }
}
