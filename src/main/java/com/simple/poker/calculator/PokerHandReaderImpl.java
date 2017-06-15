package com.simple.poker.calculator;

import java.net.URI;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PokerHandReaderImpl implements PokerHandReader {

  private static final Logger log = LoggerFactory.getLogger(PokerHandReaderImpl.class);

  public static final String URL = "tcp://localhost:61616";
  //default broker URL is : "tcp://localhost:61616"

  public static final String QUEUE = "POKERHANDQUEUE"; //Queue Name
  
  public static void main(String[] args) throws JMSException, Exception {
    
    BrokerService broker = new BrokerService();
    
    TransportConnector connector = new TransportConnector();
    connector.setUri(new URI(URL));
    broker.addConnector(connector);
    broker.start();
    
    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
    Connection connection = connectionFactory.createConnection();
    connection.start();

    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    Destination destination = session.createQueue(QUEUE);
    MessageProducer producer = session.createProducer(destination);


    PokerHandCalculatorImpl pokerCalc = new PokerHandCalculatorImpl();
    
    Thread calcThread = new Thread(pokerCalc);
    
    calcThread.start();
    
    while(true) {
      TextMessage message = session.createTextMessage("Send message to ActiveMQ!");
      // Here we are sending the message!
      producer.send(message);
      log.info("Sent message '" + message.getText() + "'");
    }

  }
  
  @Override
  public void setDatasourceFile() {
    // TODO Auto-generated method stub

  }

  @Override
  public void readCards() {
    // TODO Auto-generated method stub

  }

  @Override
  public void putToQueue(HandContainer handContainer) {
    // TODO Auto-generated method stub

  }

}
