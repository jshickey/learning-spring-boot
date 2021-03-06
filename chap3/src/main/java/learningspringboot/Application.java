package learningspringboot;

import javax.jms.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan
@EnableScheduling
@EnableAutoConfiguration
public class Application {
	private static final String MAILBOX = "events";

	// Wraps the POJO so it can consume events
	@Bean
	MessageListenerAdapter adapter(NetworkEventConsumer consumer) {
		MessageListenerAdapter adapter = new MessageListenerAdapter(consumer);
		adapter.setDefaultListenerMethod("process");
		return adapter;
	}

	// JMS Consumer
	@Bean
	SimpleMessageListenerContainer container(MessageListenerAdapter adapter, ConnectionFactory factory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setMessageListener(adapter);
		container.setConnectionFactory(factory);
		container.setDestinationName(MAILBOX);
		return container;
	}

	// JMS Message producer
	@Bean
	NetworkEventSimulator simulator(JmsTemplate jmsTemplate) {
		return new NetworkEventSimulator(jmsTemplate, MAILBOX);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}