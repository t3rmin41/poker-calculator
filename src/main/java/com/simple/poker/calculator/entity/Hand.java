package com.simple.poker.calculator.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hand implements Serializable {

  private HandStrength strength;
  
  private List<Card> cards = new ArrayList<Card>();

  public void calculateHandStrength() {
    
  }
  
  public void arrangeHandByCardRank() {
    // arrange hand by card's formatted rank
  }

  public List<Card> getCards() {
    return cards;
  }
}
