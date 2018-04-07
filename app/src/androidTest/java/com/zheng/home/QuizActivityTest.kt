package com.zheng.home

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.zheng.home.common.TestComponentRule
import com.zheng.home.data.model.Quiz
import com.zheng.home.features.quiz.QuizActivity
import com.zheng.home.util.ErrorTestUtil
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class QuizActivityTest {

    private val mComponent = TestComponentRule(InstrumentationRegistry.getTargetContext())
    private val rule = ActivityTestRule(QuizActivity::class.java, false, false)
    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    @JvmField
    var chain: TestRule = RuleChain.outerRule(mComponent).around(rule)

    @Test
    fun checkPokemonsDisplay() {
        stubDataManagerGetResponse(Single.just(Pair(1, Quiz("aa", listOf()))))
        rule.launchActivity(null)
        onView(withText("aa"))
                .check(matches(isDisplayed()))
    }

    @Test
    fun checkErrorViewDisplays() {
        stubDataManagerGetPokemonList(Single.error<Pair<Int, Quiz>>(RuntimeException()))
        rule.launchActivity(null)
        ErrorTestUtil.checkErrorViewsDisplay()
    }

    fun stubDataManagerGetPokemonList(single: Single<Pair<Int, Quiz>>) {
        val mockDataManager = mComponent.mockDataManager
        `when`(mockDataManager.response)
                .thenReturn(single)
    }

    fun stubDataManagerGetResponse(single: Single<Pair<Int, Quiz>>) {
        `when`(mComponent.mockDataManager.response)
                .thenReturn(single)
    }
}