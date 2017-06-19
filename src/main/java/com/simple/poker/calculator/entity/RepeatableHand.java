package com.simple.poker.calculator.entity;

public enum RepeatableHand {

  QUADS(7),
  FULL_HOUSE(6),
  TRIPS(3),
  TWO_PAIR(2),
  ONE_PAIR(1);
  
  private int strength;
  
  private RepeatableHand(int strength) {
    this.strength = strength;
  }
  
  public int getStrength() {
    return this.strength;
  }
}
