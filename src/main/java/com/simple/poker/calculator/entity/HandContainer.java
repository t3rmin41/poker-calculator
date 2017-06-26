package com.simple.poker.calculator.entity;

import java.io.Serializable;

import com.simple.poker.calculator.api.PokerCalculator;

@SuppressWarnings("serial")
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

}
