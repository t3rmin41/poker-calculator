package com.simple.poker.calculator;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Hand implements Serializable {

  private int strength;
  
  private Set<Card> cards = new HashSet<Card>();

  public void calculateHandStrength() {
    
  }
  
  public void arrangeHandByCardRank() {
    // arrange hand by card's formatted rank
  }

  public Set<Card> getCards() {
    return cards;
  }
}
