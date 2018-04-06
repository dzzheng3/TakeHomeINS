package com.zheng.home.features.quiz

import android.util.Log
import com.zheng.home.data.DataManager
import com.zheng.home.features.base.BasePresenter
import com.zheng.home.injection.ConfigPersistent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

@ConfigPersistent
class QuizPresenter @Inject
constructor(private val mDataManager: DataManager) : BasePresenter<QuizMvpView>() {
    //    fun getQuize() {
//        mvpView?.showProgress(true)
//        mDataManager.response
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ json ->
//                    mvpView?.showProgress(false)
//                    var quiz: Quiz = mDataManager.getRandomQuiz(mDataManager.getQuizList(json))
//                    var currentAnswer: Int = mDataManager.getQuizAnswer(quiz)
//                    mvpView?.showQuiz(quiz, currentAnswer)
//                }) { throwable ->
//                    mvpView?.showProgress(false)
//                    mvpView?.showError(throwable)
//                }
//    }
    fun getQuize() {
        mDataManager.response
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processData) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }
    }

    private fun processData(jsonResponse: String) {
        try {
            val jsonObject = convertToJson(jsonResponse)
            val items = getKeys(jsonObject)
//            currentQuiz = selectRandomItem(items, jsonObject)
//            correctAnswer = QuizUtil.randomizeAnswerOptions(currentQuiz)
//            startQuiz(currentQuiz, TimerService.QUIZ_INTERVAL)

        } catch (e: JSONException) {
            Log.e("test", "error parsing data: " + e.toString())
        }

    }

    @Throws(JSONException::class)
    private fun convertToJson(jsonResponse: String): JSONObject {
        return JSONObject(jsonResponse)
    }

    @Throws(JSONException::class)
    private fun getKeys(`object`: JSONObject): List<String> {
        val list = ArrayList<String>()
        val keys = `object`.keys()

        while (keys.hasNext()) {
            val key = keys.next() as String
            list.add(key)
        }

        return list
    }
}