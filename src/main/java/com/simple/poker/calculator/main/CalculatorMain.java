package com.simple.poker.calculator.main;

import java.net.URI;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simple.poker.calculator.jms.PokerHandContainerReciever;
import com.simple.poker.calculator.jms.PokerHandReader;

public class CalculatorMain {

  private static final Logger log = LoggerFactory.getLogger(CalculatorMain.class);

  public static final String URL = "tcp://localhost:61616";

  public static final String QUEUE = "POKERHANDQUEUE";

  public static void main(String[] args) throws Exception {
    
    log.info("Start poker calculator");
    
    BrokerService broker = new BrokerService();
    
    TransportConnector connector = new TransportConnector();
    connector.setUri(new URI(URL));
    broker.addConnector(connector);
    broker.setUseJmx(false);
    broker.start();

    PokerHandContainerReciever reciever = new PokerHandContainerReciever();
    PokerHandReader handReader = new PokerHandReader();

    if (args.length > 0) {
        handReader.setDatasourceFile(args[0]);
    } else {
        //pokerReader.setDatasourceFile("./src/main/data/poker_test.txt");
        handReader.setDatasourceFile("./src/main/data/poker.txt");
    }
    
    Thread readerThread = new Thread(handReader);
    readerThread.setName("PokerHandReader");
    Thread recieverThread = new Thread(reciever);
    recieverThread.setName("PokerHandCalculator");

    readerThread.start();
    recieverThread.start();
  }

}
