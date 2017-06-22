package com.simple.poker.calculator.entity;

import com.simple.poker.calculator.api.PokerCalculator;

public class Stats {

  private static int firstPlayerWins = 0;
  private static int secondPlayerWins = 0;
  private static int draws = 0;

  public static void setOutcome(int outcome) {
    switch (outcome) {
      case PokerCalculator.DRAW_ID          : draws++;
                                              break;
      case PokerCalculator.FIRST_PLAYER_ID  : firstPlayerWins++;
                                              break;
      case PokerCalculator.SECOND_PLAYER_ID : secondPlayerWins++;
                                              break;
      default:                                break;
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
