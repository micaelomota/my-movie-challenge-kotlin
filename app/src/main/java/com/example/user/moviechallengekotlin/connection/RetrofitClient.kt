package com.example.user.moviechallengekotlin.connection

import com.example.user.moviechallengekotlin.connection.service.MovieService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

fun Retrofit.movieService(): MovieService = this.create(MovieService::class.java)

object RetrofitClient {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w300"
    const val APIKEY = "PUT YOUR KEY gHERE"
    var instance: Retrofit? = null
        get() {
            if (field == null) {
                    val interceptor = HttpLoggingInterceptor()

                    interceptor.level = HttpLoggingInterceptor.Level.BODY

                    val client = OkHttpClient.Builder()
                            .addInterceptor(interceptor)
                            .addInterceptor(object : Interceptor {
                                @Throws(IOException::class)
                                override fun intercept(chain: Interceptor.Chain): Response {
                                    val original = chain.request()

                                    val url = original.url().newBuilder()
                                            .addQueryParameter("api_key", APIKEY)
                                            .build()

                                    val requestBuilder = original.newBuilder().url(url)
                                    val request = requestBuilder.build()

                                    return chain.proceed(request)
                                }
                            }).build()

                    field = Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build()
            }

            return field
        }
    }
