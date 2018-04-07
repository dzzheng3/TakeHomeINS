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
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class DataManagerTest {

    @Rule
    @JvmField
    val mOverrideSchedulersRule = RxSchedulersOverrideRule()
    @Mock
    lateinit var quizApi: QuizApi
    var testUtils = TestUtils()
    private var mDataManager: DataManager? = null

    @Before
    fun setUp() {
        mDataManager = DataManager(quizApi)
    }

    @Test
    fun getListAndTestComplete() {
        `when`(quizApi.getQuizItemList())
                .thenReturn(Single.just(testUtils.loadJson("mock/quiz.json")))

        mDataManager?.response
                ?.test()
                ?.assertComplete()
    }

    @Test
    fun getRandomQuiz() {
        val quizList = mDataManager?.getQuizList(testUtils.loadJson("mock/quiz.json"))
        val randomQuiz = mDataManager?.getRandomQuiz(quizList)
        assertNotNull(randomQuiz)

    }

    @Test
    fun getQuizAnswer() {
        val quizList = mDataManager?.getQuizList(testUtils.loadJson("mock/quiz.json"))
        val randomQuiz = mDataManager?.getRandomQuiz(quizList)
        val quizAnswer = mDataManager?.getQuizAnswer(randomQuiz)
        assertNotNull(quizAnswer)
        assertTrue(quizAnswer!! >= 0 && quizAnswer < 4)
    }

    @Test
    fun getQuizList() {
        val quizList = mDataManager?.getQuizList(testUtils.loadJson("mock/quiz.json"))
        assertNotNull(quizList)
    }
}
