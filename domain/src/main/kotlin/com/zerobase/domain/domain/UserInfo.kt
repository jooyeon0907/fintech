package com.zerobase.domain.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "USR_INFO")
class UserInfo(// 생성자에 필요한 값들 추가
    @Column(name = "usr_key")  // 테이블의 칼럼 네임 (DB 에서는 축약된 명칭 사용)
    val userKey: String,
    // build.gradle 에 plugins { kotlin("plugin.jpa") } 설정하여 초기값을 설정하지 않아도 됨

    @Column(name = "usr_reg_num")
    val userRegistrationNumber: String,

    @Column(name = "usr_nm")
    val userName: String,

    @Column(name = "usr_icm_amt")
    val userIncomeAmount: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
