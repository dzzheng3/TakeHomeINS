package com.zheng.home.features.quiz

import com.zheng.home.data.DataManager
import com.zheng.home.data.model.Quiz
import com.zheng.home.features.base.BasePresenter
import com.zheng.home.injection.ConfigPersistent
import com.zheng.home.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

@ConfigPersistent
class QuizPresenter @Inject
constructor(private val mDataManager: DataManager) : BasePresenter<QuizMvpView>() {
    fun getQuize() {
        mvpView?.showProgress(true)
        mDataManager.response
                .compose(SchedulerUtils.ioToMain<String>())
                .subscribe({ json ->
                    mvpView?.showProgress(false)
                    var quiz: Quiz = mDataManager.getRandomQuiz(mDataManager.getQuizList(json))
                    var currentAnswer: Int = mDataManager.getQuizAnswer(quiz)
                    mvpView?.showQuiz(quiz, currentAnswer)
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }
    }
}