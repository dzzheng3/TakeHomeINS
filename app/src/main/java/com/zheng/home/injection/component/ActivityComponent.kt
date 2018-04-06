package com.zheng.home.injection.component

import com.zheng.home.features.base.BaseActivity
import com.zheng.home.features.detail.DetailActivity
import com.zheng.home.features.main.MainActivity
import com.zheng.home.features.quiz.QuizActivity
import com.zheng.home.injection.PerActivity
import com.zheng.home.injection.module.ActivityModule
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)

    fun inject(mainActivity: MainActivity)

    fun inject(detailActivity: DetailActivity)

    fun inject(quizActivity: QuizActivity)
}
