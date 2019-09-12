package project.delivery.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.joda.money.Money;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JacksonConfiguration {

	public static ObjectMapper mapper() {
		Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
		new JacksonConfiguration()
			.jackson2ObjectMapperBuilderCustomizer()
			.customize(builder);

		return builder.build();
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
		return jacksonObjectMapperBuilder ->
			jacksonObjectMapperBuilder
				.serializationInclusion(JsonInclude.Include.NON_NULL)
				.serializerByType(Money.class, new MoneySerializer())
				.deserializerByType(Money.class, new MoneyDeserializer())
				.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer())
				.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer());
	}

	private static class MoneySerializer
		extends StdSerializer<Money> {
		MoneySerializer() {
			super(Money.class);
		}

		@Override
		public void serialize(Money value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException {
			jgen.writeString(value.toString());
		}
	}

	private static class MoneyDeserializer
		extends StdDeserializer<Money> {
		MoneyDeserializer() {
			super(Money.class);
		}

		@Override
		public Money deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException {
			return Money.parse(jp.readValueAs(String.class));
		}
	}

	private static class LocalDateTimeSerializer
		extends StdSerializer<LocalDateTime> {
		LocalDateTimeSerializer() {
			super(LocalDateTime.class);
		}

		@Override
		public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException {
			jgen.writeString(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(value));
		}
	}

	private static class LocalDateTimeDeserializer
		extends StdDeserializer<LocalDateTime> {
		LocalDateTimeDeserializer() {
			super(LocalDateTime.class);
		}

		@Override
		public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException {
			return LocalDateTime.parse(jp.getValueAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		}
	}
}
