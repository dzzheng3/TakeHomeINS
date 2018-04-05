package com.zheng.home.data.remote

import io.reactivex.Single
import retrofit2.http.GET

interface QuizApi {
    @GET("/mataanin/8d1afd0ba11f0d50f5b7/raw/4a1573c3089c109d2aefcf488f0bf8fbf89e7753/zquestions.json")
    fun getQuizItemList(): Single<String>
}