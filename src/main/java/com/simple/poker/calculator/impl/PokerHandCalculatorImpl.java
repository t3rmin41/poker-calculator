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
import com.simple.poker.calculator.entity.Card;
import com.simple.poker.calculator.entity.Hand;
import com.simple.poker.calculator.entity.HandContainer;
import com.simple.poker.calculator.entity.HandStrength;
import com.simple.poker.calculator.main.CalculatorMain;

public class PokerHandCalculatorImpl implements PokerHandCalculator, Runnable {

  private static final Logger log = LoggerFactory.getLogger(PokerHandCalculatorImpl.class);
  
  private static String url = CalculatorMain.URL;

  private static String queue = CalculatorMain.QUEUE;
  
  private Session session;
  
  private MessageConsumer consumer;

  @Override
  public Hand calculateHand(Hand hand) {
    if (isRepeatable(hand)) {
        calculateRepeatable(hand);
    } else {
        calculateNonRepeatable(hand);
    }
    return hand;
  }

  @Override
  public void run() {
      initiateQueueSession();
      readFromQueue();
  }

  private boolean isRepeatable(Hand hand) {
      hand.arrangeHandByCardRank();
      for (int i = 1; i < hand.getCards().size(); i++) {
        if (hand.getCards().get(i).getRankFormatted() == hand.getCards().get(0).getRankFormatted()) {
          return hand.setRepeatable(true).isRepeatable();
        }
      }
      return hand.setRepeatable(false).isRepeatable();
  }
  
  private void calculateRepeatable(Hand hand) {
      
  }
  
  private void calculateNonRepeatable(Hand hand) {
      boolean straight = isStraight(hand);
      boolean flush = isFlush(hand);
      if (straight && flush) {
          hand.setStrength(HandStrength.STRAIGHT_FLUSH);
      } else if (straight) {
          hand.setStrength(HandStrength.STRAIGHT);
      } else if (flush) {
          hand.setStrength(HandStrength.FLUSH);
      } else {
          hand.setStrength(HandStrength.HIGH_CARD);
      }
  }
  
  private boolean isStraight(Hand hand) {
      for (int i = 1; i < hand.getCards().size(); i++) {
          if (((hand.getCards().get(i-1).getRankFormatted()+1) != hand.getCards().get(i).getRankFormatted())
               && !"A".equals(hand.getCards().get(4).getRank())) { // special case for "A-5" straight
              return false;
          }
      }
      hand.shiftRightByOnePosition();
      return true;
  }
  
  private boolean isFlush(Hand hand) {
      for (int i = 1; i < hand.getCards().size(); i++) {
          if (!hand.getCards().get(0).getColor().equals(hand.getCards().get(i).getColor())) {
              return false;
          }
      }
      return true;
  }
  
  private boolean isQuads(Hand hand) {
      return false;
  }
  
  private boolean isFullHouse(Hand hand) {
      return false;
  }
  
  private boolean isTrips(Hand hand) {
      return false;
  }
  
  private boolean isTwoPair(Hand hand) {
      return false;
  }

  private void readFromQueue() {
      try {
        while (true) {
          //ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
          //String text = textMessage.getText();
          //log.info("Received message with text : " + text);
          Message message = consumer.receive();
          if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            HandContainer handContainer = (HandContainer) objectMessage.getObject();
            //log.info("Received "+handContainer.getId()+" message container : " + handContainer);
            //handContainer.getFirstPlayerHand().arrangeHandByCardRank();
            //handContainer.getSecondPlayerHand().arrangeHandByCardRank();
            calculateHand(handContainer.getFirstPlayerHand());
            calculateHand(handContainer.getSecondPlayerHand());
            //handContainer.setFirstPlayerHand(firstHand);
            //handContainer.setSecondPlayerHand(secondHand);
            //log.info("Sorted "+handContainer.getId()+" hand container : " + handContainer);
            handContainer.defineWinner();
            log.info("Sorted "+handContainer.getId()+" hand container : " + handContainer);
            //log.info("Hand #"+handContainer.getId()+" wins player #"+handContainer.getWinner());
          }
        }
      } catch (JMSException e) {
        log.error(e.getMessage());
      } catch (NullPointerException e) {
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
