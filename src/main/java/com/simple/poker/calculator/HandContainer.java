package com.simple.poker.calculator;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class HandContainer implements Serializable {

  private Set<Hand> hands = new HashSet<Hand>();
  
  public Set<Hand> getHands() {
    return this.hands;
  }

}
