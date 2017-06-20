package com.simple.poker.calculator.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Hand implements Serializable {

  private HandStrength strength;
  
  private List<Card> cards = new ArrayList<Card>();

  public void arrangeHandByCardRank() {
    // arrange hand by card's formatted rank
    Collections.sort(cards);
  }

  public List<Card> getCards() {
    return cards;
  }

}
