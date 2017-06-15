package com.simple.poker.calculator;

import java.net.URI;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
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
    try {
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
      Connection connection = connectionFactory.createConnection();
      connection.start();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Destination destination = session.createQueue(QUEUE);
      MessageProducer producer = session.createProducer(destination);
      TextMessage message = session.createTextMessage("Send message to ActiveMQ!");
      producer.send(message);
      log.info("Sent message '" + message.getText() + "'");
    } catch (JMSException e) {
      log.error(e.getMessage());
    } catch (NullPointerException e) {
      log.error(e.getMessage());
    }
  }

  @Override
  public void run() {
    while (true) {
      putToQueue(new HandContainer());
    }
  }

}
