package xyz.mayahiro.simplerefreshjwtexample

import retrofit2.http.GET

interface NetworkService {
    @GET("token")
    suspend fun getToken(): String

    @GET("secret")
    suspend fun getSecret(): String
}
