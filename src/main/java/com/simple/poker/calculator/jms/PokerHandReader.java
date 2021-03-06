package com.simple.poker.calculator.jms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simple.poker.calculator.entity.Card;
import com.simple.poker.calculator.entity.Hand;
import com.simple.poker.calculator.entity.HandContainer;
import com.simple.poker.calculator.main.CalculatorMain;

public class PokerHandReader implements Runnable {

  private static final Logger log = LoggerFactory.getLogger(PokerHandReader.class);

  private static final String URL = CalculatorMain.URL;

  private static final String QUEUE = CalculatorMain.QUEUE;
  
  private String datasourceFilePath = "";

  private MessageProducer producer;
  
  private Session session;

  public void setDatasourceFile(String path) {
    this.datasourceFilePath = path;
  }

  @Override
  public void run() {
    initiateQueueSession();
    readCards();
  }

  private void initiateQueueSession() {
      try {
          //ActiveMQConnectionFactory connectionFactory = new PooledConnectionFactory(URL);
          ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
          PooledConnectionFactory pooledFactory = new PooledConnectionFactory(connectionFactory);
          Connection connection = pooledFactory.createConnection();
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
  
  private void readCards() {
      BufferedReader br = null;
      try {
        br = new BufferedReader(new FileReader(datasourceFilePath));
        String line;
        int i = 1;
        log.info("Start reading the datasource");
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
           container.setFinished(false);
           putToQueue(container);
           i++;
        }
        HandContainer container = new HandContainer();
        container.setFinished(true);
        putToQueue(container);
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
  
  private void putToQueue(HandContainer handContainer) {
      try {
        ObjectMessage objectMessage = session.createObjectMessage(handContainer);
        producer.send(objectMessage);
        //log.info("Sent message #"+handContainer.getId()+" '" + handContainer + "'");
      } catch (JMSException e) {
        log.error(e.getMessage());
      }
    }

}
