package com.simple.poker.calculator.entity;

public enum HandStrength {

  STRAIGHT_FLUSH(8),
  QUADS(7),
  FULL_HOUSE(6),
  FLUSH(5),
  STRAIGHT(4),
  TRIPS(3),
  TWO_PAIR(2),
  ONE_PAIR(1),
  HIGH_CARD(0);
  
  private int strength;
  
  private HandStrength(int strength) {
    this.strength = strength;
  }
  
  public int getStrength() {
    return this.strength;
  }
  
}
