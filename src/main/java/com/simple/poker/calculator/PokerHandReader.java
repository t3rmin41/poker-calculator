package com.simple.poker.calculator;

public interface PokerHandReader {

  void setDatasourceFile();
  
  void readCards();
  
  void putToQueue(HandContainer handContainer);
}
