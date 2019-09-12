package project.delivery.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@EnableRabbit
@Configuration
public class RabbitConfiguration {

	@Value("${spring.rabbitmq.host}")
	private String host;

	@Value("${spring.rabbitmq.exchange}")
	private String exchange;

	@Value("${spring.rabbitmq.queue}")
	private String queue;

	@Bean
	public Queue myQueue() {
		return new Queue(queue);
	}

	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(exchange);
	}

	@Bean
	public CachingConnectionFactory connectionFactory() {
		return new CachingConnectionFactory(host);
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
		rabbitTemplate.setExchange(exchange);
		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(mapper()));
		rabbitTemplate.setAfterReceivePostProcessors();

		return rabbitTemplate;
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
		final ConnectionFactory connectionFactory
	) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(new Jackson2JsonMessageConverter(mapper()));
		return factory;
	}

	@Bean
	public Binding orderOrderRequestWasStandbyV1() {
		return subscribeFor("order.OrderRequestWasStandbyV1");
	}

	@Bean
	public Binding suppliersInvoiceWasCreatedV1() {
		return subscribeFor("suppliers.InvoiceWasCreatedV1");
	}

	@Bean
	public ObjectMapper mapper() {
		Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
		new JacksonConfiguration()
			.jackson2ObjectMapperBuilderCustomizer()
			.customize(builder);

		return builder.build();
	}

	private Binding subscribeFor(final String routingKey) {
		return BindingBuilder
			.bind(myQueue())
			.to(topicExchange())
			.with(routingKey);
	}
}
