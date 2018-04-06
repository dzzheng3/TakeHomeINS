package com.zheng.home.injection.component

import com.zheng.home.features.base.BaseActivity
import com.zheng.home.features.quiz.QuizActivity
import com.zheng.home.injection.PerActivity
import com.zheng.home.injection.module.ActivityModule
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)
    fun inject(quizActivity: QuizActivity)
}
