package com.simple.poker.calculator.api;

public interface PokerHandReader {

  void setDatasourceFile(String path);
  
  void readCards();

}
