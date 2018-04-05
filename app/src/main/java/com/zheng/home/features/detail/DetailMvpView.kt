package com.zheng.home.features.detail

import com.zheng.home.data.model.Pokemon
import com.zheng.home.data.model.Statistic
import com.zheng.home.features.base.MvpView

interface DetailMvpView : MvpView {

    fun showPokemon(pokemon: Pokemon)

    fun showStat(statistic: Statistic)

    fun showProgress(show: Boolean)

    fun showError(error: Throwable)

}