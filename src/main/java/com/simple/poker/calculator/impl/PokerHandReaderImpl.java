package com.simple.poker.calculator.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simple.poker.calculator.api.PokerHandReader;
import com.simple.poker.calculator.entity.Card;
import com.simple.poker.calculator.entity.Hand;
import com.simple.poker.calculator.entity.HandContainer;
import com.simple.poker.calculator.main.CalculatorMain;

public class PokerHandReaderImpl implements PokerHandReader, Runnable {

  private static final Logger log = LoggerFactory.getLogger(PokerHandReaderImpl.class);

  private static final String URL = CalculatorMain.URL;

  private static final String QUEUE = CalculatorMain.QUEUE;
  
  private String datasourceFilePath = "";

  private MessageProducer producer;
  
  private Session session;
  
  @Override
  public void setDatasourceFile(String path) {
    this.datasourceFilePath = path;
  }

  @Override
  public void readCards() {
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(datasourceFilePath));
      String line;
      while ((line = br.readLine()) != null) {
         String[] cardsStrings = line.split(" ");
         Hand firstPlayerHand = new Hand();
         Hand secondPlayerHand = new Hand();
         
         for (int i = 0; i < 5; i++) {
           firstPlayerHand.getCards().add(new Card(cardsStrings[i]));
         }
         for (int j = 5; j < 10; j++) {
           secondPlayerHand.getCards().add(new Card(cardsStrings[j]));
         }

         HandContainer container = new HandContainer();
         container.setFirstPlayerHand(firstPlayerHand);
         container.setSecondPlayerHand(secondPlayerHand);
         
         putToQueue(container);
      }
      try {
          session.close();
      } catch (JMSException e) {
          log.error(e.getLocalizedMessage());
      }
    } catch (IOException e) {
      log.error(e.getLocalizedMessage());
    } finally {
      try {
        br.close();
      } catch (IOException e) {
        log.error(e.getLocalizedMessage());
      }
    }
  }

  @Override
  public void run() {
    initiateQueueSession();
    readCards();
  }

  private void initiateQueueSession() {
      try {
          ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
          Connection connection = connectionFactory.createConnection();
          connection.start();
          session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
          Destination destination = session.createQueue(QUEUE);
          producer = session.createProducer(destination);
        } catch (JMSException e) {
          log.error(e.getMessage());
        } catch (NullPointerException e) {
          log.error(e.getMessage());
        }
  }
  
  private void putToQueue(HandContainer handContainer) {
      try {
        //TextMessage message = session.createTextMessage("Send message to ActiveMQ!");
        //producer.send(message);
        //log.info("Sent message '" + message.getText() + "'");
        ObjectMessage objectMessage = session.createObjectMessage(handContainer);
        producer.send(objectMessage);
        log.info("Sent message '" + handContainer + "'");
      } catch (JMSException e) {
        log.error(e.getMessage());
      } catch (NullPointerException e) {
        log.error(e.getMessage());
      }
    }

}
