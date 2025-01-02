package com.zerobase.api.loan.review

import com.zerobase.api.exception.CustomException
import com.zerobase.api.exception.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackageClasses = [LoanReviewController::class]) // LoanReviewController 에 대한 예외처리
class LoanReviewControllerAdvice {

	@ExceptionHandler(CustomException::class) // 특정 exception 을 받아서 해당 exception 발생 시 처리함
	fun customExceptionHandler(customException: CustomException) =
			ErrorResponse(customException).toResponseEntity()

}