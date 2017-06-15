package com.simple.poker.calculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PokerHandReaderImpl implements PokerHandReader, Runnable {

  private static final Logger log = LoggerFactory.getLogger(PokerHandReaderImpl.class);

  private static final String URL = CalculatorMain.URL;

  private static final String QUEUE = CalculatorMain.QUEUE;
  
  private String datasourceFilePath = "";

  private Session session;
  
  private Connection connection;
  
  @Override
  public void setDatasourceFile(String path) {
    this.datasourceFilePath = path;
  }

  @Override
  public void readCards() {
    BufferedReader br = null;
    initiateQueueSession();
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
          getQueueConnection().close();
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
  public void putToQueue(HandContainer handContainer) {
    try {
      Destination destination = getQueueSession().createQueue(QUEUE);
      MessageProducer producer = getQueueSession().createProducer(destination);
      //TextMessage message = session.createTextMessage("Send message to ActiveMQ!");
      ObjectMessage objectMessage = session.createObjectMessage(handContainer);
      //producer.send(message);
      producer.send(objectMessage);
      log.info("Sent message '" + handContainer + "'");
      //log.info("Sent message '" + message.getText() + "'");
    } catch (JMSException e) {
      log.error(e.getMessage());
    } catch (NullPointerException e) {
      log.error(e.getMessage());
    }
  }

  @Override
  public void run() {
    readCards();
  }

  private void initiateQueueSession() {
      try {
          ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
          connection = connectionFactory.createConnection();
          connection.start();
          session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
          //Destination destination = session.createQueue(QUEUE);
          //MessageProducer producer = session.createProducer(destination);
        } catch (JMSException e) {
          log.error(e.getMessage());
        } catch (NullPointerException e) {
          log.error(e.getMessage());
        }
  }
  
  public Session getQueueSession() {
    return session;
  }
  
  public Connection getQueueConnection() {
    return connection;
  }
}
