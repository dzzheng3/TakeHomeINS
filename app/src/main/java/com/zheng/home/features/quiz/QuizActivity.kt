package com.zheng.home.features.quiz

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.zheng.home.R
import com.zheng.home.data.model.Quiz
import com.zheng.home.features.base.BaseActivity
import com.zheng.home.features.common.ErrorView
import kotlinx.android.synthetic.main.activity_quiz.*
import javax.inject.Inject


class QuizActivity : BaseActivity(), QuizMvpView, ErrorView.ErrorListener, QuizAdapter.OnClickListenr {

    @Inject
    lateinit var quizPresenter: QuizPresenter
    @Inject
    lateinit var quizAdapter: QuizAdapter
    private var QUIZ: String? = "quiz"
    private var quizPair: Pair<Int, Quiz>? = null
    private var dialog: AlertDialog.Builder? = null
    private var isConfigchanged: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        quizPresenter.attachView(this)
        setSupportActionBar(toolbar)
        swipe_to_refresh.setProgressBackgroundColorSchemeResource(R.color.primary)
        swipe_to_refresh.setColorSchemeResources(R.color.white)
        swipe_to_refresh.setOnRefreshListener { quizPresenter.getQuize() }
        swipe_to_refresh.isRefreshing = true
        quizAdapter.setOnClickListener(this)
        rv_quiz.layoutManager = GridLayoutManager(this, 2)
        rv_quiz.adapter = quizAdapter
        view_error.setErrorListener(this)
        if (savedInstanceState != null) {
            val answer = savedInstanceState.getInt(QUIZ)
            val quiz = savedInstanceState.getParcelable<Quiz>(QUIZ)
            isConfigchanged = savedInstanceState.getBoolean(QUIZ)
            showQuiz(quiz, answer)
        } else {
            quizPresenter.getQuize()
        }
        if (!isConfigchanged) {
            dialog = AlertDialog.Builder(this)
            dialog?.setCancelable(false)
        }

    }

    override fun showQuiz(quiz: Quiz, answer: Int) {
        quizPair = Pair(answer, quiz)
        swipe_to_refresh.isRefreshing = false
        view_error.visibility = View.INVISIBLE
        quizAdapter.setQuizAnswers(quiz.images)
        quizAdapter.notifyDataSetChanged()
    }

    override fun showProgress(show: Boolean) {
        view_error.visibility = View.INVISIBLE
        swipe_to_refresh.isRefreshing = true
    }

    override fun showError(error: Throwable) {
        swipe_to_refresh.isRefreshing = false
        view_error.visibility = View.VISIBLE
    }

    override fun onReloadData() {
        swipe_to_refresh.isRefreshing = true
        view_error.visibility = View.INVISIBLE
        quizPresenter.getQuize()
    }

    override val layout: Int
        get() = R.layout.activity_quiz

    override fun onSaveInstanceState(outState: Bundle) {
        isConfigchanged = true
        if (quizPair != null) {
            outState.putInt(QUIZ, quizPair!!.first)
            outState.putBoolean(QUIZ, isConfigchanged)
            outState.putParcelable(QUIZ, quizPair!!.second)
        }
        super.onSaveInstanceState(outState)
    }

    override fun clickItem(answer: Int) {
        if (answer == quizPair?.first) {
            dialog?.setMessage("Congratulations! You have selected correct answer! Would you like to try again?")
            dialog?.setPositiveButton("Yes",
                    { dialog, _ ->
                        quizPresenter.getQuize()
                        dialog.dismiss()
                    })
            dialog?.setNegativeButton("No",
                    { dialog, _ ->
                        dialog.dismiss()
                        finish()
                    })
        } else {
            dialog?.setMessage("Nope. You are wrong! Would you like to try again?")
            dialog?.setPositiveButton("Yes",
                    { dialog, _ ->
                        dialog.dismiss()
                    })
            dialog?.setNegativeButton("No",
                    { dialog, _ ->
                        dialog.dismiss()
                        finish()
                    })
        }
        dialog?.show()
    }
}
