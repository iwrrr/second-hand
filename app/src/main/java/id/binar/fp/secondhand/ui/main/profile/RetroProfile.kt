package id.binar.fp.secondhand.ui.main.profile

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroProfile {
    fun getRetroProfile(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl("https://market-final-project.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}