package com.simple.poker.calculator.entity;

import java.io.Serializable;

public class Hand implements Serializable {

  private HandStrength strength;
  
  private Card[] cards = new Card[5];

  public void arrangeHandByCardRank() {
    // arrange hand by card's formatted rank
  }

  public Card[] getCards() {
    return cards;
  }
}
