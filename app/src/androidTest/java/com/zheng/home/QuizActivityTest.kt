package com.zheng.home

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.zheng.home.common.TestComponentRule
import com.zheng.home.data.remote.QuizApi
import com.zheng.home.features.quiz.QuizActivity
import com.zheng.home.util.ErrorTestUtil
import com.zheng.home.util.TestUtils
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class QuizActivityTest {

    private val mComponent = TestComponentRule(InstrumentationRegistry.getTargetContext())
    private val rule = ActivityTestRule(QuizActivity::class.java, false, false)
    @Mock
    lateinit var quizApi: QuizApi
    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    @JvmField
    var chain: TestRule = RuleChain.outerRule(mComponent).around(rule)

    @Test
    fun checkPokemonsDisplay() {
        var testUtils = TestUtils()
        `when`(quizApi.getQuizItemList())
                .thenReturn(Single.just(testUtils.loadJson("mock/quiz.json")))

//        var loadJson = testUtils.loadJson("mock/quiz.json")
//
//        stubDataManagerGetResponse(Single.just(loadJson))
//        rule.launchActivity(null)
//
//        onView(withId(R.id.tv_name))
//                .check(matches(isDisplayed()))
    }

    @Test
    fun checkErrorViewDisplays() {
        stubDataManagerGetPokemonList(Single.error<String>(RuntimeException()))
        rule.launchActivity(null)
        ErrorTestUtil.checkErrorViewsDisplay()
    }

    fun stubDataManagerGetPokemonList(single: Single<String>) {
        val mockDataManager = mComponent.mockDataManager
        `when`(mockDataManager.response)
                .thenReturn(single)
    }

    fun stubDataManagerGetResponse(single: Single<String>) {
        `when`(mComponent.mockDataManager.response)
                .thenReturn(single)
    }
}