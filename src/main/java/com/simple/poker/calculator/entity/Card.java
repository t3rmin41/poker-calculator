package com.simple.poker.calculator.entity;

import java.io.Serializable;

public class Card implements Comparable<Card>, Serializable {
  
  public static final int A = 14;
  public static final int K = 13;
  public static final int Q = 12;
  public static final int J = 11;
  public static final int T = 10;
    
  private String rank;
  
  private String color;

  private int rankFormatted;
  
  public Card(String rankAndColor) {
    setRank(rankAndColor.substring(0, 1));
    setColor(rankAndColor.substring(1, 2));
  }

  public String getRank() {
    return rank;
  }

  public Card setRank(String rank) {
    this.rank = rank;
    setRankFormatted(rank);
    return this;
  }

  public String getColor() {
    return color;
  }

  public Card setColor(String color) {
    this.color = color;
    return this;
  }
  
  public int getRankFormatted() {
    return rankFormatted;
  }
  
  @Override
  public int compareTo(Card o) {
    return Integer.compare(this.rankFormatted, o.rankFormatted);
  }
  
  private void setRankFormatted(String rank) {
    switch (rank) {
      case "2" : this.rankFormatted = 2;
                 break;
      case "3" : this.rankFormatted = 3;
                 break;
      case "4" : this.rankFormatted = 4;
                 break;
      case "5" : this.rankFormatted = 5;
                 break;
      case "6" : this.rankFormatted = 6;
                 break;
      case "7" : this.rankFormatted = 7;
                 break;
      case "8" : this.rankFormatted = 8;
                 break;
      case "9" : this.rankFormatted = 9;
                 break;
      case "T" : this.rankFormatted = T;
                 break;
      case "J" : this.rankFormatted = J;
                 break;
      case "Q" : this.rankFormatted = Q;
                 break;
      case "K" : this.rankFormatted = K;
                 break;
      case "A" : this.rankFormatted = A;
                 break;
      default  : this.rankFormatted = 0;
                 break;
    }
  }
}
