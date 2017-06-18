package com.simple.poker.calculator.api;

import com.simple.poker.calculator.entity.HandContainer;

public interface PokerHandReader {

  void setDatasourceFile(String path);
  
  void readCards();
  
  void putToQueue(HandContainer handContainer);
}
