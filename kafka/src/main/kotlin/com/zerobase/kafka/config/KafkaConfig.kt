package com.zerobase.kafka.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*

@Configuration
@EnableKafka
class KafkaConfig {
	companion object {
		const val bootstrapServer = "localhost:9092"
	}

	@Bean // producer 관련 config 설정
	fun producerFactory(): ProducerFactory<String, String> {
		val configurationProperties = HashMap<String, Any>()
		configurationProperties[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServer
		configurationProperties[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
		configurationProperties[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java

		return DefaultKafkaProducerFactory(configurationProperties)
	}

	@Bean // consumer 관련 config 설정
	fun consumerFactory(): ConsumerFactory<String, String> {
		val configurationProperties = HashMap<String, Any>()
		configurationProperties[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServer
		configurationProperties[ConsumerConfig.GROUP_ID_CONFIG] = "fintech"
		configurationProperties[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
			// 어디서부터 읽을 것인지
				// earliest	 : 해당 토픽 파티션 내에서 가장 먼저 쌓인 것을 가져옴
				// latest	 : 가장 최근에 쌓인 것을 offset 으로 설정해서 가져옴
				// none : exception 발생
		configurationProperties[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
		configurationProperties[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java

		return DefaultKafkaConsumerFactory(configurationProperties)
	}

	@Bean
	fun kafkaTemplate(): KafkaTemplate<String, String> {
		return KafkaTemplate(producerFactory())
	}

	@Bean
	fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
		val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
		factory.consumerFactory = consumerFactory()
		return factory
	}

}



/*
producerFactory 통해서 카프카에 연결하고서
kafkaTemplate 생성해서 메세지 발행 할 수 있음

consumerFactory 생성하고,
kafkaListenerContainerFactory 통해서 카프카 메세지들을 읽을 수 있도록 설정함

 */