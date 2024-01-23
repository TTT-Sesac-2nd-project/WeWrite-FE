package com.wewrite.android.ui.Login

import LoginRepository
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.wewrite.android.api.APIFactory
import com.wewrite.android.ui.MainActivity
import com.wewrite.android.databinding.ActivityLoginBinding
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.kakaoLoginButton.setOnClickListener {
            try {
                kakaoLogin()
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, e.toString())
            }
        }
    }

    private fun kakaoLogin() {
        val loginRepository = LoginRepository.create()

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Toast.makeText(this, "카카오 계정으로 로그인 실패", Toast.LENGTH_SHORT).show()
            } else if (token != null) {
                Toast.makeText(this, "카카오 계정으로 로그인 성공", Toast.LENGTH_SHORT).show()
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Toast.makeText(this, "카카오톡으로 로그인 실패", Toast.LENGTH_SHORT).show()
                    Log.e(ContentValues.TAG, error.toString())

                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    } else {
                        Toast.makeText(this, "카카오 로그인 시도 실패", Toast.LENGTH_SHORT).show()
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    Log.e(ContentValues.TAG, "카카오 로그인 시도")
                } else if (token != null) {
                    Log.e(ContentValues.TAG, "엑세스 토큰: ${token.accessToken}")
                    try {
                        runBlocking {
                            Log.e(ContentValues.TAG, "Test12")
                            val response = loginRepository.sendAccessTokenToServer(token.accessToken)
                            Log.e(ContentValues.TAG, response.toString())
                            // 서버로 전송 후의 동작을 여기에 추가
                            if (response.code == 200) {
                                //TODO: sharedpreference에 토큰 저장
                                Log.e("tokenResponse", response.toString())
                            } else {
                                Toast.makeText(this@LoginActivity, "로그인 에러, 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                            }
                            Log.e(ContentValues.TAG, "엑세스 토큰: ${token.accessToken}")
                            goToMainPage()
                        }
                    } catch (e: Exception) {
                        Log.e(ContentValues.TAG, e.toString())
                    }
                    goToMainPage()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }


    private fun goToMainPage() {
        // myPageActivity로 이동
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
//@author: 이승민