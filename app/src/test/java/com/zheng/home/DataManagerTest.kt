package com.zheng.home

import com.zheng.home.data.DataManager
import com.zheng.home.data.remote.QuizApi
import com.zheng.home.util.RxSchedulersOverrideRule
import com.zheng.home.util.TestUtils
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataManagerTest {

    @Rule
    @JvmField
    val mOverrideSchedulersRule = RxSchedulersOverrideRule()
    @Mock
    lateinit var quizApi: QuizApi

    private var mDataManager: DataManager? = null

    @Before
    fun setUp() {
        mDataManager = DataManager(quizApi)
    }

    @Test
    fun getPokemonListAndTestComplete() {
        var testUtils: TestUtils = TestUtils()

        `when`(quizApi.getQuizItemList())
                .thenReturn(Single.just(testUtils.loadJson("mock/quiz.json")))

        mDataManager?.response
                ?.test()
                ?.assertComplete()
//                ?.assertValue()
    }
}
