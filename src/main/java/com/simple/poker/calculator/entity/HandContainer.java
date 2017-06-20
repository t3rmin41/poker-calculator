package com.simple.poker.calculator.entity;

import java.io.Serializable;

public class HandContainer implements Serializable {

  private int id;
  
  private Hand firstPlayerHand;
  private Hand secondPlayerHand;
  private int winner;
  
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
      
  }
  public int getWinner() {
      return winner;
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
