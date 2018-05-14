package com.example.android.projetoparceiro.network

import com.example.android.projetoparceiro.data.Lancamento
import com.example.android.projetoparceiro.model.Login
import com.example.android.projetoparceiro.model.ResponseToken
import com.example.android.projetoparceiro.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitInterface {
    @POST("auth/token")
    fun login(@Body user: Login): Call<ResponseToken>

    @POST("auth/new")
    fun register(@Body user: User): Call<Void>

    @GET("auth/user")
    fun getProfile(): Call<User>

    @GET("auth/refresh")
    fun refresh(): Call<ResponseToken>

    @GET("/v1/lancamento/lista")
    fun getList(): Call<Array<Lancamento>>

    @POST("/lancamento/alt/{idLancamento}")
    fun alterarLancamento(@Path("idLancamento") idLancamento: Long, @Body lancamento: Lancamento): Call<Lancamento>

    @POST("/lancamento/novo")
    fun novoLancamento(@Body lancamento: Lancamento): Call<Lancamento>
//
//    @PUT("users/{email}")
//    fun changePassword(@Path("email") email: String, @Body user: Usuario): Observable<ResponseToken<com.example.android.logindefault.model.ResponseToken>>
//
//    @POST("users/{email}/password")
//    fun resetPasswordInit(@Path("email") email: String): Observable<ResponseToken<com.example.android.logindefault.model.ResponseToken>>
//
//    @POST("users/{email}/password")
//    fun resetPasswordFinish(@Path("email") email: String, @Body user: Usuario): Observable<ResponseToken<com.example.android.logindefault.model.ResponseToken>>
}