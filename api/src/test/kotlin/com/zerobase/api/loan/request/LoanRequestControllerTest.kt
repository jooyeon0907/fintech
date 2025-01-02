package com.zerobase.api.loan.request

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.zerobase.api.loan.GenerateKey
import com.zerobase.api.loan.encrypt.EncryptComponent
import com.zerobase.domain.domain.UserInfo
import com.zerobase.domain.repository.UserInfoRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@WebMvcTest(LoanRequestController::class)
    // 테스트 클래스가 돌아갈 때, RestController 나 Controller 어노테이션이 붙은 클래스만 빈을 띄워줌
    // LoanRequestController::class 처럼 argument 로 타겟을 지정하여 더 가볍게 해줄 수 있음
internal class LoanRequestControllerTest {

    private lateinit var mockMvc: MockMvc // 테스트를 위한 servlet 생성

    private lateinit var loanRequestController: LoanRequestController
        // 생성자에 LoanRequestServiceImpl 으로 의존성 주입을 받고 있음

    private lateinit var generateKey: GenerateKey

    private lateinit var encryptComponent: EncryptComponent

    private val userInfoRepository: UserInfoRepository = mockk() // mock 처리된 레파지토리 생성

    private lateinit var mapper: ObjectMapper

    @MockBean // mock 처리된 빈을 띄워줌 -
    private lateinit var loanRequestServiceImpl: LoanRequestServiceImpl
        // 의존성 주입을 받고 있는 것들을 따로 생성해줘야됨 (GenerateKey, UserInfoRepository, EncryptComponent)

    companion object {
        private const val baseUrl = "/fintech/api/v1"
    }

    @BeforeEach
    fun init() {
        generateKey = GenerateKey()

        encryptComponent = EncryptComponent()

        loanRequestServiceImpl = LoanRequestServiceImpl(
                generateKey, userInfoRepository, encryptComponent

        )

        loanRequestController = LoanRequestController(loanRequestServiceImpl)

        mockMvc = MockMvcBuilders.standaloneSetup(loanRequestController).build()
            // 해당 컨트롤러가 띄워줘서 테스트할 수 있도록 해줌

        mapper = ObjectMapper().registerModule(KotlinModule.Builder().build())
    }


    @Test
    @DisplayName("유저 요청이 들어오면 정상 응답을 주어야 한다.")
    fun testNormalCase() {
        // given -> 테스트 코드에 주어진 값들이 있고
        val loanRequestInfoDto: LoanRequestDto.LoanRequestInputDto =
                LoanRequestDto.LoanRequestInputDto(
                    userName = "TEST",
                    userIncomeAmount =  10000,
                    userRegistrationNumber = "000101-1234567"
                )

        every {
            userInfoRepository.save(any())
        } returns UserInfo("", "", "", 1)

        // when -> 이 값들이 어떻게 실행 되었을 때
        //then -> 어떤 응답을 내야한다.
        mockMvc.post(
                "$baseUrl/request"
        ){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(loanRequestInfoDto)
        }.andExpect {
            status { isOk() }
        }

    }

}