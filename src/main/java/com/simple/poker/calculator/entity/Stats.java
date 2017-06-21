package com.simple.poker.calculator.entity;

public class Stats {

  private static int firstPlayerWins = 0;
  private static int secondPlayerWins = 0;
  private static int draws = 0;

  public static void setOutcome(int outcome) {
    switch (outcome) {
      case 0 : draws++;
               break;
      case 1 : firstPlayerWins++;
               break;
      case 2 : secondPlayerWins++;
               break;
      default: break;
    }
  }
  
  public static int getFirstPlayerWins() {
    return firstPlayerWins;
  }

  public static int getSecondPlayerWins() {
    return secondPlayerWins;
  }

  public static int getDraws() {
    return draws;
  }

}
