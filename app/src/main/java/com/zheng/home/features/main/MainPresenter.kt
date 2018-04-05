package com.zheng.home.features.main

import com.zheng.home.data.DataManager
import com.zheng.home.data.model.Quiz
import com.zheng.home.injection.ConfigPersistent
import com.zheng.home.features.base.BasePresenter
import com.zheng.home.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

@ConfigPersistent
class MainPresenter @Inject
constructor(private val mDataManager: DataManager) : BasePresenter<MainMvpView>() {

    override fun attachView(mvpView: MainMvpView) {
        super.attachView(mvpView)
    }

    fun getPokemon(limit: Int) {
        checkViewAttached()
        mvpView?.showProgress(true)
        mDataManager.getPokemonList(limit)
                .compose(SchedulerUtils.ioToMain<List<String>>())
                .subscribe({ pokemons ->
                    mvpView?.showProgress(false)
                    mvpView?.showPokemon(pokemons)
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }
    }

    fun getQuize() {
        mDataManager.response
                .compose<String>(SchedulerUtils.ioToMain<String>())
                .subscribe({ json ->
                    var quiz:Quiz = mDataManager.getRandomQuiz(mDataManager.getQuizList(json))
                    var currentAnswer:Int = mDataManager.getQuizAnswer(quiz)

                })
    }
}