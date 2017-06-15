package com.simple.poker.calculator;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PokerHandCalculatorImpl implements PokerHandCalculator, Runnable {

  private static final Logger log = LoggerFactory.getLogger(PokerHandCalculatorImpl.class);
  
  private static String url = PokerHandReaderImpl.URL;
  //default broker URL is : tcp://localhost:61616"

  //Name of the queue we will receive messages from
  private static String queue = PokerHandReaderImpl.QUEUE;

  
  @Override
  public void readFromQueue() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void calculateHand() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void getStats() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void run() {
      try {
          ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
          Connection connection = connectionFactory.createConnection(); // exception happens here...
          connection.start();
          Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
          Destination destination = session.createQueue(queue);
          MessageConsumer consumer = session.createConsumer(destination);
          while (true) {
            try {
              Message message = consumer.receive();
              if (message instanceof ObjectMessage) {
                ObjectMessage objectMessage = (ObjectMessage) message;
                TextMessage requestMessage = (TextMessage) objectMessage.getObject();
                String text = requestMessage.getText();
                log.info("Received message with text : " + text);
              }
            } catch (JMSException e) {
              log.error(e.getMessage());
            } catch (NullPointerException e) {
              log.error(e.getMessage());
            }
          }
       } catch (Exception e) {
         log.error(e.getMessage());
       }
    }
}
