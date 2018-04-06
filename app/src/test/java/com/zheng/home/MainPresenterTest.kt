package com.zheng.home

import com.zheng.home.data.DataManager
import com.zheng.home.data.model.Quiz
import com.zheng.home.features.quiz.QuizMvpView
import com.zheng.home.features.quiz.QuizPresenter
import com.zheng.home.util.RxSchedulersOverrideRule
import com.zheng.home.util.TestUtils
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @Mock
    lateinit var quizMvpView: QuizMvpView
    @Mock
    lateinit var mMockDataManager: DataManager
    private var quizPresenter: QuizPresenter? = null

    @JvmField
    @Rule
    val mOverrideSchedulersRule = RxSchedulersOverrideRule()

    @Before
    fun setUp() {
        quizPresenter = QuizPresenter(mMockDataManager)
        quizPresenter?.attachView(quizMvpView)
    }

    @After
    fun tearDown() {
        quizPresenter?.detachView()
    }

    @Test
    @Throws(Exception::class)
    fun getPokemonReturnsPokemonNames() {
        val testUtils = TestUtils()
        val loadJson = testUtils.loadJson("mock/quiz.json")
        `when`(mMockDataManager.response)
                .thenReturn(Single.just(loadJson))

        quizPresenter?.getQuize()

        verify<QuizMvpView>(quizMvpView, times(2)).showProgress(anyBoolean())
        verify<QuizMvpView>(quizMvpView).showQuiz(Quiz("", listOf()), 1)
        verify<QuizMvpView>(quizMvpView, never()).showError(RuntimeException())

    }

    @Mock
    lateinit var quiz: Quiz

    @Test
    @Throws(Exception::class)
    fun getPokemonReturnsError() {
        `when`(mMockDataManager.response)
                .thenReturn(Single.error<String>(RuntimeException()))

        quizPresenter?.getQuize()

        verify<QuizMvpView>(quizMvpView, times(2)).showProgress(anyBoolean())
//        verify<MainMvpView>(quizMvpView).showError(RuntimeException())
        verify<QuizMvpView>(quizMvpView, never()).showQuiz(quiz, 1)
    }

}