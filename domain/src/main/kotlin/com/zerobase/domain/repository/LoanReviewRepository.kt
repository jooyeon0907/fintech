package com.zerobase.domain.repository

import com.zerobase.domain.domain.LoanReview
import org.springframework.data.jpa.repository.JpaRepository

interface LoanReviewRepository : JpaRepository<LoanReview, Long> {
    fun findByUserKey(userKey: String): LoanReview?
    // 코틀린에서 ? 를 붙여주면 null 값이 들어올 수 있도록 함 -> nullable 해짐
}