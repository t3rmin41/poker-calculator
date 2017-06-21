package com.simple.poker.calculator.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand implements Serializable {

  private HandStrength strength = HandStrength.HIGH_CARD;
  private boolean isRepeatable = false;
  
  private List<Card> cards = new ArrayList<Card>();

  public HandStrength getStrength() {
    return strength;
  }

  public Hand setStrength(HandStrength strength) {
    this.strength = strength;
    return this;
  }

  public void arrangeHandByCardRank() {
    // arrange hand by card's formatted rank
    Collections.sort(cards);
  }

  public List<Card> getCards() {
    return cards;
  }

  public boolean isRepeatable() {
    return isRepeatable;
  }

  public Hand setRepeatable(boolean isRepeatable) {
    this.isRepeatable = isRepeatable;
    return this;
  }

  public void shiftRightByOnePosition() {
      Collections.rotate(cards, 1);
  }
}
