package com.zerobase.api.exception

class CustomException(val customErrorCode: CustomErrorCode) : RuntimeException()
// CustomException 이 발생했을 때 exception Handler 를 통해 가져오고 customErrorCode 를 보고 어떤 응답을 보내줄지 정함