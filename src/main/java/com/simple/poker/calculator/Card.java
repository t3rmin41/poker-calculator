package com.simple.poker.calculator;

public class Card {
  
  private String rank;
  
  private String color;

  private int rankFormatted;
  
  public Card(String rank, String color) {
    setRank(rank);
    setColor(color);
  }

  public String getRank() {
    return rank;
  }

  public void setRank(String rank) {
    this.rank = rank;
    setRankFormatted(rank);
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
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
