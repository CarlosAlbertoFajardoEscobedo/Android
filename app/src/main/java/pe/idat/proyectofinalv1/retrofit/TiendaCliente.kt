package pe.idat.proyectofinalv1.retrofit

import okhttp3.OkHttpClient
import pe.idat.proyectofinalv1.utilitarios.Constantes
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object TiendaCliente {

    private var okHttpCliente= OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        //.addInterceptor(ApiInterceptor())
        .build()

    private fun buildRetrofit()= Retrofit.Builder()
        .baseUrl(Constantes().BASE_URL)
        .client(okHttpCliente)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val retrofiteService: TiendaServicio by lazy {
        buildRetrofit().create(TiendaServicio::class.java)
    }
}