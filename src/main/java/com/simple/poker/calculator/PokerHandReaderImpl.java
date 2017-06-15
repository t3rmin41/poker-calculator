package com.simple.poker.calculator;

import java.net.URI;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PokerHandReaderImpl implements PokerHandReader {

  private static final Logger log = LoggerFactory.getLogger(PokerHandReaderImpl.class);

  public static final String URL = "vm://localhost?create=false";
  //default broker URL is : tcp://localhost:61616"

  public static final String QUEUE = "POKERHANDQUEUE"; //Queue Name
  
  public static void main(String[] args) throws JMSException, Exception {
    
    BrokerService broker = new BrokerService();
    // configure the broker
    broker.setBrokerName("localhost");
    broker.setUseJmx(false);
    broker.start();
    
    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
    Connection connection = connectionFactory.createConnection();
    connection.start();
    // JMS messages are sent and received using a Session. We will
    // create here a non-transactional session object. If you want
    // to use transactions you should set the first parameter to 'true'
    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    // Destination represents here our queue 'VALLYSOFTQ' on the
    // JMS server. You don't have to do anything special on the
    // server to create it, it will be created automatically.
    Destination destination = session.createQueue(QUEUE);
    // MessageProducer is used for sending messages (as opposed
    // to MessageConsumer which is used for receiving them)
    MessageProducer producer = session.createProducer(destination);
    // We will send a small text message saying 'Hello' in Japanese
    while(true) {
      TextMessage message = session.createTextMessage("Send message to ActiveMQ!");
      // Here we are sending the message!
      producer.send(message);
      log.info("Sentage '" + message.getText() + "'");
    }
  }
  
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
    // TODO Auto-generated method stub

  }

}
