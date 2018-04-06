package com.zheng.home.injection.component

import android.app.Application
import android.content.Context
import com.zheng.home.data.DataManager
import com.zheng.home.data.remote.QuizApi
import com.zheng.home.injection.ApplicationContext
import com.zheng.home.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun dataManager(): DataManager

    fun quizApi(): QuizApi
}
