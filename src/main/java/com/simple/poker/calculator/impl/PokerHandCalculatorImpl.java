package com.simple.poker.calculator.impl;

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

import com.simple.poker.calculator.api.PokerCalculatorEngine;
import com.simple.poker.calculator.api.PokerHandCalculator;
import com.simple.poker.calculator.entity.Hand;
import com.simple.poker.calculator.entity.HandContainer;
import com.simple.poker.calculator.entity.Stats;
import com.simple.poker.calculator.main.CalculatorMain;

public class PokerHandCalculatorImpl implements PokerHandCalculator, Runnable {

  private static final Logger log = LoggerFactory.getLogger(PokerHandCalculatorImpl.class);
  
  private static String url = CalculatorMain.URL;

  private static String queue = CalculatorMain.QUEUE;
  
  private PokerCalculatorEngine engine = new PokerCalculatorEngineImpl();

  private Session session;
  
  private MessageConsumer consumer;

  @Override
  public Hand calculateHand(Hand hand) {
    if (engine.isRepeatable(hand)) {
      engine.calculateRepeatable(hand);
    } else {
      engine.calculateNonRepeatable(hand);
    }
    return hand;
  }

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
            calculateHand(handContainer.getFirstPlayerHand());
            calculateHand(handContainer.getSecondPlayerHand());
            handContainer.defineWinner();
            Stats.setOutcome(handContainer.getWinner());
            //log.info("Sorted "+handContainer.getId()+" hand container : " + handContainer);
            log.info("Hand #"+handContainer.getId()+" wins player #"+handContainer.getWinner());
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
          ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
          Connection connection = connectionFactory.createConnection();
          connection.start();
          session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
          Destination destination = session.createQueue(queue);
          consumer = session.createConsumer(destination);
      } catch (JMSException e) {
          log.error(e.getMessage());
      } catch (NullPointerException e) {
          log.error(e.getMessage());
      }
  }
  
}
