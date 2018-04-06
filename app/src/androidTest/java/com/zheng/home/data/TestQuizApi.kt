package com.zheng.home.data

import com.zheng.home.data.remote.QuizApi
import com.zheng.home.util.TestUtils
import io.reactivex.Single

class TestQuizApi : QuizApi {
    var testUtils: TestUtils = TestUtils()

    override fun getQuizItemList(): Single<String> {
        val loadJson = testUtils.loadJson("mock/quiz.json")
        return Single.just(loadJson)
    }

}