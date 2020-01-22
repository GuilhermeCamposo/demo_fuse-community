package fuse.community.message;

import org.apache.camel.component.amqp.AMQPComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;

@Configuration
public class Config {

    @Value( "${broker.user}" )
    private String brokerUser;
    @Value( "${broker.password}" )
    private String brokerPassword;
    @Value( "${broker.url}" )
    private String brokerUrl;


    @Bean(value = "jmsConnectionFactory")
    public JmsConnectionFactory jmsConnectionFactory() {

        final JmsConnectionFactory jmsConnectionFactory = new JmsConnectionFactory();
        jmsConnectionFactory.setRemoteURI(brokerUrl);
        jmsConnectionFactory.setUsername(brokerUser);
        jmsConnectionFactory.setPassword(brokerPassword);

        return jmsConnectionFactory;
    }

    @Bean(value="jmsTransactionManager")
    public JmsTransactionManager jmsTransactionManager(JmsConnectionFactory jmsConnectionFactory){
        final JmsTransactionManager txManager = new JmsTransactionManager();
        txManager.setConnectionFactory(jmsConnectionFactory);
        return txManager;
    }

    @Bean(value = "jmsConfig")
    public JmsConfiguration jmsConfig(JmsConnectionFactory factory) {
        JmsConfiguration jmsConfig = new JmsConfiguration(factory);
        jmsConfig.setConcurrentConsumers(1);
        return jmsConfig;
    }

    @Bean(value = "amq")
    public AMQPComponent component(JmsConfiguration jmsConfig){
        AMQPComponent amqpComponent = new AMQPComponent();
        amqpComponent.setConfiguration(jmsConfig);
        amqpComponent.setTransacted(true);
        return amqpComponent;
    }
}


