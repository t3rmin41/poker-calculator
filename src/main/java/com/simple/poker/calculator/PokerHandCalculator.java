package com.simple.poker.calculator;

public interface PokerHandCalculator {

  public final String[] cards = {"2","3","4","5","6","7","8","9","T","J","Q","K","A"};
  
  public final String[] colors = {"S", "H", "C", "D"};
  
  void readFromQueue();
  
  void calculateHand();
  
  void getStats();
  
}
