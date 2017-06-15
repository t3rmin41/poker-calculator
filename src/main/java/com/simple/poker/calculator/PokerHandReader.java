package com.simple.poker.calculator;

public interface PokerHandReader {

  void setDatasourceFile(String path);
  
  void readCards();
  
  void putToQueue(HandContainer handContainer);
}
