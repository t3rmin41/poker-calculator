package com.simple.poker.calculator;

import java.io.Serializable;

public class Card implements Serializable {
  
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
      case "T" : this.rankFormatted = 10;
                 break;
      case "J" : this.rankFormatted = 11;
                 break;
      case "Q" : this.rankFormatted = 12;
                 break;
      case "K" : this.rankFormatted = 13;
                 break;
      case "A" : this.rankFormatted = 14;
                 break;
      default  : this.rankFormatted = 0;
                 break;
    }
  }

}
