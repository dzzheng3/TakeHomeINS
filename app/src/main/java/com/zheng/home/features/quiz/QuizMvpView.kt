package com.zheng.home.features.quiz

import com.zheng.home.data.model.Quiz
import com.zheng.home.features.base.MvpView

interface QuizMvpView : MvpView {

    fun showQuiz(quiz: Quiz, answer: Int)

    fun showProgress(show: Boolean)

    fun showError(error: Throwable)

}