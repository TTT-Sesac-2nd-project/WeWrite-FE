import com.wewrite.android.api.APIFactory
import com.wewrite.android.api.data.com.wewrite.android.api.data.LoginResponse
import com.wewrite.android.api.service.LoginService

class LoginRepository(private val loginService: LoginService) {

    suspend fun sendAccessTokenToServer(accessToken: String): LoginResponse {
        return loginService.kakaoLogin(accessToken)
    }

    companion object {
        fun create(): LoginRepository {
            val loginService = APIFactory.getInstance().create(LoginService::class.java)
            return LoginRepository(loginService)
        }
    }
}

//@Author: 이승민
