package com.simple.poker.calculator.main;

import java.net.URI;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simple.poker.calculator.jms.PokerHandContainerReceiver;
import com.simple.poker.calculator.jms.PokerHandReader;

public class CalculatorMain {

  private static final Logger log = LoggerFactory.getLogger(CalculatorMain.class);

  public static final String URL = "tcp://localhost:61616";

  public static final String QUEUE = "POKERHANDQUEUE";
  
  private static BrokerService broker = new BrokerService();

  public static void main(String[] args) throws Exception {
    
    log.info("Start poker calculator");

    TransportConnector connector = new TransportConnector();
    connector.setUri(new URI(URL));
    broker.addConnector(connector);
    broker.setUseJmx(false);
    broker.setUseShutdownHook(true);
    broker.setPersistent(false);
    broker.start();

    PokerHandContainerReceiver receiver = new PokerHandContainerReceiver();
    PokerHandReader handReader = new PokerHandReader();

    if (args.length > 0) {
        handReader.setDatasourceFile(args[0]);
    } else {
        //pokerReader.setDatasourceFile("./src/main/data/poker_test.txt");
        handReader.setDatasourceFile("./src/main/data/poker.txt");
    }
    
    Thread readerThread = new Thread(handReader);
    readerThread.setName("PokerHandReader");
    Thread receiverThread = new Thread(receiver);
    receiverThread.setName("PokerHandReciever");

    readerThread.start();
    receiverThread.start();
  }

  public static BrokerService getBroker() {
    return broker;
  }
}
