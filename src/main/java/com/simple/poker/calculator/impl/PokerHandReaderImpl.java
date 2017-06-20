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
      int i = 1;
      while ((line = br.readLine()) != null) {
         String first = line.substring(0, 14);
         String second = line.substring(15, 29);
         String[] firstArr = first.split(" ");
         String[] secondArr = second.split(" ");
         Hand firstPlayerHand = new Hand();
         Hand secondPlayerHand = new Hand();
         
         for (int j = 0; j < 5; j++) {
           firstPlayerHand.getCards().add(j, new Card(firstArr[j]));
           secondPlayerHand.getCards().add(j, new Card(secondArr[j]));
         }
         HandContainer container = new HandContainer();
         container.setFirstPlayerHand(firstPlayerHand);
         container.setSecondPlayerHand(secondPlayerHand);
         container.setId(i);
         putToQueue(container);
         i++;
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
    //close queue session when done
    try {
        session.close();
    } catch (JMSException e) {
        log.error(e.getLocalizedMessage());
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
