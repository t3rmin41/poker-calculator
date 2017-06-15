package com.simple.poker.calculator;

public interface PokerCardReader {

  void setDatasourceFile();
  
  void readCards();
  
  void putToQueue(HandContainer handContainer);
}
