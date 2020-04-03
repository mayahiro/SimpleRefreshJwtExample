package xyz.mayahiro.simplerefreshjwtexample

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object ApiClient {
    private lateinit var autoRefreshAdapter: Retrofit
    private lateinit var adapter: Retrofit

    fun init() {
        autoRefreshAdapter = Retrofit.Builder()
            .baseUrl("http://YOUR_SERVER_IP_ADDRESS:8080/")
            .client(createAutoRefreshHttpClient())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        adapter = Retrofit.Builder()
            .baseUrl("http://YOUR_SERVER_IP_ADDRESS:8080/")
            .client(createHttpClient())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    private fun createHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder
            .networkInterceptors()
            .add(Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()

                val token = SharedPreferenceManager.getToken()
                if (token.isNotBlank()) {
                    requestBuilder.header("TOKEN", token)
                }

                chain.proceed(requestBuilder.build())
            })

        return builder.build()
    }

    private fun createAutoRefreshHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder
            .addInterceptor(TokenCheckInterceptor())
            .networkInterceptors()
            .add(Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()

                val token = SharedPreferenceManager.getToken()
                if (token.isNotBlank()) {
                    requestBuilder.header("TOKEN", token)
                }

                chain.proceed(requestBuilder.build())
            })

        return builder.build()
    }

    fun <T> service(clazz: Class<T>): T = adapter.create(clazz)
    fun <T> autoRefreshService(clazz: Class<T>): T = autoRefreshAdapter.create(clazz)
}
