package xyz.mayahiro.simplerefreshjwtexample

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class JwtPayload(
    @Json(name = "exp") val exp: Long
)
