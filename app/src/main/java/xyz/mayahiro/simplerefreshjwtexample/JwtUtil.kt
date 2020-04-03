package xyz.mayahiro.simplerefreshjwtexample

import android.util.Base64
import com.squareup.moshi.Moshi

fun getExpFromJwt(jwt: String): Long? {
    return try {
        val payloadJson = String(Base64.decode(jwt.split(".")[1], Base64.DEFAULT))
        val payload = Moshi.Builder().build()
            .adapter(JwtPayload::class.java)
            .fromJson(payloadJson)
        payload?.exp
    } catch (e: Exception) {
        // Error Handling
        null
    }
}
