package com.simple.poker.calculator.main;

import java.net.URI;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simple.poker.calculator.impl.PokerHandCalculatorImpl;
import com.simple.poker.calculator.impl.PokerHandReaderImpl;

public class CalculatorMain {

  private static final Logger log = LoggerFactory.getLogger(CalculatorMain.class);

  public static final String URL = "tcp://localhost:61616";

  public static final String QUEUE = "POKERHANDQUEUE";

  public static void main(String[] args) throws JMSException, Exception {
    BrokerService broker = new BrokerService();
    
    TransportConnector connector = new TransportConnector();
    connector.setUri(new URI(URL));
    broker.addConnector(connector);
    broker.setUseJmx(false);
    broker.start();
    
    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
    Connection connection = connectionFactory.createConnection();
    connection.start();

    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    Destination destination = session.createQueue(QUEUE);

    PokerHandCalculatorImpl pokerCalc = new PokerHandCalculatorImpl();
    PokerHandReaderImpl pokerReader = new PokerHandReaderImpl();
    pokerReader.setDatasourceFile("src/main/resources/poker_test.txt");
    
    Thread readerThread = new Thread(pokerReader);
    Thread calcThread = new Thread(pokerCalc);

    readerThread.start();
    calcThread.start();

  }

}
