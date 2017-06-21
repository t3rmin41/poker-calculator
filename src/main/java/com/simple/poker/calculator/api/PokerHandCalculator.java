package com.simple.poker.calculator.api;

import com.simple.poker.calculator.entity.Hand;

public interface PokerHandCalculator {

  public final String[] cards = {"2","3","4","5","6","7","8","9","T","J","Q","K","A"};
  
  public final String[] colors = {"S", "H", "C", "D"};
  
  public final int POKER_CARD_COUNT = 5;

  Hand calculateHand(Hand hand);

}
