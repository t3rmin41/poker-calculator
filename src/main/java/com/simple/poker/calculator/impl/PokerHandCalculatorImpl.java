package com.simple.poker.calculator.impl;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simple.poker.calculator.api.PokerHandCalculator;
import com.simple.poker.calculator.entity.HandContainer;
import com.simple.poker.calculator.main.CalculatorMain;

public class PokerHandCalculatorImpl implements PokerHandCalculator, Runnable {

  private static final Logger log = LoggerFactory.getLogger(PokerHandCalculatorImpl.class);
  
  private static String url = CalculatorMain.URL;

  private static String queue = CalculatorMain.QUEUE;
  
  private Connection connection;

  @Override
  public void readFromQueue() {
    try {
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
      connection = connectionFactory.createConnection(); // exception happens here...
      connection.start();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Destination destination = session.createQueue(queue);
      MessageConsumer consumer = session.createConsumer(destination);
      while (true) {
        Message message = consumer.receive();
        if (message instanceof ObjectMessage) {
          ObjectMessage objectMessage = (ObjectMessage) message;
          HandContainer handContainer = (HandContainer) objectMessage.getObject();
          log.info("Received message container : " + handContainer);
        }
      }
      //ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
      //String text = textMessage.getText();
      //log.info("Received message with text : " + text);
    } catch (JMSException e) {
      log.error(e.getMessage());
    } catch (NullPointerException e) {
      log.error(e.getMessage());
    } finally {
      try {
        connection.close();
      } catch (JMSException e) {
          log.error(e.getLocalizedMessage());
      }
    }
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
      readFromQueue();
  }

}