package xyz.mayahiro.simplerefreshjwtexample

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

class TokenCheckInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (chain.request().url().toString().contains("token")) {
            return chain.proceed(chain.request())
        }

        val exp = getExpFromJwt(SharedPreferenceManager.getToken())
        if (exp != null && exp < (ZonedDateTime.now(ZoneId.of("UTC")).toEpochSecond())) {
            runBlocking {
                launch(Dispatchers.IO) {
                    try {
                        val token = ApiClient.service(NetworkService::class.java).getToken()
                        SharedPreferenceManager.setToken(token)
                    } catch (e: Exception) {
                        // Error Handling
                        Log.d("TokenCheckInterceptor", e.toString())
                    }
                }
            }
        }
        return chain.proceed(chain.request())
    }
}
