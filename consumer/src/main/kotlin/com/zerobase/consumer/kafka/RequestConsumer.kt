package com.zerobase.consumer.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.zerobase.kafka.dto.LoanRequestDto
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class LoanRequestConsumer(
	private val objectMapper: ObjectMapper
	// TODO: CB 사 호출 로직
) {
	@KafkaListener(topics = ["loan_request"], groupId = "fintech")
		// 토픽과 groupId를 지정해주면 해당 토픽의 groupId로 묶여있는 것들끼리 묶여서 카프카 토픽을 읽어오게 됨
	fun loanRequestTopicConsumer(message: String) {
		val loanRequestKafkaDto = objectMapper.readValue(message, LoanRequestDto::class.java)
		// json 형태로 온 String 이 mapper(LoanRequestDto) 에 따라서 그대로 담기게 됨
//		println(loanRequestKafkaDto) // 계속 토픽을 consume 하는지 확인
	}
	// api 와 consumer 어플리케이션을 실행하고,
	// kafka 토픽을 발행하면
	// loanRequestTopicConsumer() 가 계속 토픽을 consume 함
}