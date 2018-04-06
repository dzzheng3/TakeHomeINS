package com.zheng.home.features.quiz

import android.os.Bundle
import com.zheng.home.R
import com.zheng.home.data.model.Quiz
import com.zheng.home.features.base.BaseActivity
import kotlinx.android.synthetic.main.activity_quiz.*
import javax.inject.Inject

class QuizActivity : BaseActivity(), QuizMvpView {
    @Inject
    lateinit var quizPresenter: QuizPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        quizPresenter.attachView(this)
        setSupportActionBar(toolbar)
        swipe_to_refresh.setProgressBackgroundColorSchemeResource(R.color.primary)
        swipe_to_refresh.setColorSchemeResources(R.color.white)
        swipe_to_refresh.setOnRefreshListener { quizPresenter.getQuize() }

//        mPokemonAdapter.setClickListener(this)
//        mPokemonRecycler?.layoutManager = LinearLayoutManager(this)
//        mPokemonRecycler?.adapter = mPokemonAdapter
//
//        mErrorView?.setErrorListener(this)
//        if (savedInstanceState != null) {
//            var pokemonState: ArrayList<String> = savedInstanceState.getStringArrayList(MainActivity.POKEMON_STATE)
//            showPokemon(pokemonState)
//        } else {
        quizPresenter.getQuize()
//        }
    }

    override fun showQuiz(quiz: Quiz, answer: Int) {
    }

    override fun showProgress(show: Boolean) {
    }

    override fun showError(error: Throwable) {
    }

    override val layout: Int
        get() = R.layout.activity_quiz

}
