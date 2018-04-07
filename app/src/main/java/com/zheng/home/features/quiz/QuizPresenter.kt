package com.zheng.home.features.quiz

import com.zheng.home.data.DataManager
import com.zheng.home.data.model.Quiz
import com.zheng.home.features.base.BasePresenter
import com.zheng.home.injection.ConfigPersistent
import com.zheng.home.util.rx.scheduler.SchedulerUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@ConfigPersistent
class QuizPresenter @Inject
constructor(private val mDataManager: DataManager) : BasePresenter<QuizMvpView>() {
    var disposable: Disposable? = null
    fun getQuize() {
        if (disposable != null && !(disposable?.isDisposed as Boolean))
            disposable?.dispose()
        mvpView?.showProgress(true)
        mDataManager.response
                .compose(SchedulerUtils.ioToMain<Pair<Int, Quiz>>())
                .subscribe({ pair ->
                    countDown()
                    mvpView?.showProgress(false)
                    mvpView?.showQuiz(pair.second, pair.first)
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }
    }

    private fun countDown() {
        disposable = Observable.interval(1, TimeUnit.SECONDS)
                .take(30) // up to 30 items
                .map({ v -> v + 1 }) // shift it to 1 .. 30
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ time ->
                    mvpView?.countDown(time)
                }, {}, {
                    mvpView?.showTimeOut()
                })
    }
}