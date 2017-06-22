package com.simple.poker.calculator.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simple.poker.calculator.api.PokerCalculator;
import com.simple.poker.calculator.entity.HandContainer;
import com.simple.poker.calculator.entity.Stats;
import com.simple.poker.calculator.impl.PokerCalculatorImpl;
import com.simple.poker.calculator.main.CalculatorMain;

public class PokerHandContainerReciever implements Runnable {

  private static final Logger log = LoggerFactory.getLogger(PokerHandContainerReciever.class);
  
  private static String URL = CalculatorMain.URL;

  private static String QUEUE = CalculatorMain.QUEUE;
  
  private PokerCalculator calc = new PokerCalculatorImpl();

  private Session session;
  
  private MessageConsumer consumer;

  @Override
  public void run() {
      initiateQueueSession();
      readFromQueue();
  }

  private void readFromQueue() {
      try {
        while (true) {
          Message message = consumer.receive();
          if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            HandContainer handContainer = (HandContainer) objectMessage.getObject();
            if (handContainer.isFinished()) {
              break;
            }
            handContainer.setWinner(calc.returnWinner(handContainer.getFirstPlayerHand(), handContainer.getSecondPlayerHand()));
            Stats.setOutcome(handContainer.getWinner());
            //log.info("Sorted "+handContainer.getId()+" hand container : " + handContainer);
          }
        }
        log.info("player #1 wins = "+Stats.getFirstPlayerWins()+"; player #2 wins = "+Stats.getSecondPlayerWins()+"; draws = "+Stats.getDraws());
      } catch (JMSException e) {
        log.error(e.getMessage());
      } finally {
        try {
            session.close();
        } catch (JMSException e) {
            log.error(e.getLocalizedMessage());
        }
      }
  }
  
  private void initiateQueueSession() {
      try {
          ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
          Connection connection = connectionFactory.createConnection();
          connection.start();
          session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
          Destination destination = session.createQueue(QUEUE);
          consumer = session.createConsumer(destination);
      } catch (JMSException e) {
          log.error(e.getMessage());
      } catch (NullPointerException e) {
          log.error(e.getMessage());
      }
  }
  
}
