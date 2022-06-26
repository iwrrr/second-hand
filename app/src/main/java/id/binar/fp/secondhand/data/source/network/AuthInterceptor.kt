package id.binar.fp.secondhand.data.source.network

import id.binar.fp.secondhand.util.UserPreferences
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val prefs: UserPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        var token: String?

        runBlocking {
            token = prefs.fetchToken()
            if (!token.isNullOrEmpty()) {
                requestBuilder.addHeader("access_token", "$token")
            }
        }

        return chain.proceed(requestBuilder.build())
    }
}