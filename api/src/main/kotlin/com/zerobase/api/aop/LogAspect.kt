package com.zerobase.api.aop

import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch

@Aspect
@Component
class LogAspect {
    val logger = KotlinLogging.logger { }

    @Pointcut("within(com.zerobase.api..*)") // 경로 지정하여 특정 시점을 가져옴 -> com.zerobase.api 패키지 하위 모두 가져옴
    private fun isApi() {} // api 모듈인지 체크


    @Around("isApi()") // // -> com.zerobase.api 패키지의 하위 모듈이 실행될 때마다 조인 포인트를(isApi) 가져옴
    fun loggingAspect(joinPoint: ProceedingJoinPoint): Any {
        val stopWatch = StopWatch() // 성능 검사를 위해 사용 -> joinPoint 가 얼마나 걸렸는지 측정
        stopWatch.start()

        val result = joinPoint.proceed()

        stopWatch.stop()

        logger.info { "${joinPoint.signature.name} ${joinPoint.args[0]} ${stopWatch.lastTaskTimeMillis}" }
            // 어떤 모듈에서 실행되었고, 어떤 argument 로 실행이 되었고, 얼마나 시간이 걸렸는지 로깅

        return result
    }
}