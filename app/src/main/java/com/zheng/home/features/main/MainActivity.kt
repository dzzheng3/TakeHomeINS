package com.zheng.home.features.main

import com.zheng.home.R
import com.zheng.home.features.base.BaseActivity
import com.zheng.home.features.common.ErrorView
import com.zheng.home.features.detail.DetailActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ProgressBar
import butterknife.BindView
import com.zheng.home.data.model.Pokemon
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMvpView, PokemonAdapter.ClickListener, ErrorView.ErrorListener {

    @Inject
    lateinit var mPokemonAdapter: PokemonAdapter
    @Inject
    lateinit var mMainPresenter: MainPresenter

    @BindView(R.id.view_error)
    @JvmField
    var mErrorView: ErrorView? = null
    @BindView(R.id.progress)
    @JvmField
    var mProgress: ProgressBar? = null
    @BindView(R.id.recycler_pokemon)
    @JvmField
    var mPokemonRecycler: RecyclerView? = null
    @BindView(R.id.swipe_to_refresh)
    @JvmField
    var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    @BindView(R.id.toolbar)
    @JvmField
    var mToolbar: Toolbar? = null

    var pokemons: ArrayList<String>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mMainPresenter.attachView(this)

        setSupportActionBar(mToolbar)

        mSwipeRefreshLayout?.setProgressBackgroundColorSchemeResource(R.color.primary)
        mSwipeRefreshLayout?.setColorSchemeResources(R.color.white)
        mSwipeRefreshLayout?.setOnRefreshListener { mMainPresenter.getPokemon(POKEMON_COUNT) }

        mPokemonAdapter.setClickListener(this)
        mPokemonRecycler?.layoutManager = LinearLayoutManager(this)
        mPokemonRecycler?.adapter = mPokemonAdapter

        mErrorView?.setErrorListener(this)
        if (savedInstanceState != null) {
            var pokemonState: ArrayList<String> = savedInstanceState.getStringArrayList(POKEMON_STATE)
            showPokemon(pokemonState)
        } else {
            mMainPresenter.getPokemon(POKEMON_COUNT)
        }
    }

    override val layout: Int
        get() = R.layout.activity_main

    override fun onDestroy() {
        super.onDestroy()
        mMainPresenter.detachView()
    }

    override fun showPokemon(pokemon: List<String>) {
        pokemons?.clear()
        pokemons?.addAll(pokemon)
        mPokemonAdapter.setPokemon(pokemon)
        mPokemonAdapter.notifyDataSetChanged()

        mPokemonRecycler?.visibility = View.VISIBLE
        mSwipeRefreshLayout?.visibility = View.VISIBLE
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            if (mPokemonRecycler?.visibility == View.VISIBLE && mPokemonAdapter.itemCount > 0) {
                mSwipeRefreshLayout?.isRefreshing = true
            } else {
                mProgress?.visibility = View.VISIBLE

                mPokemonRecycler?.visibility = View.GONE
                mSwipeRefreshLayout?.visibility = View.GONE
            }

            mErrorView?.visibility = View.GONE
        } else {
            mSwipeRefreshLayout?.isRefreshing = false
            mProgress?.visibility = View.GONE
        }
    }

    override fun showError(error: Throwable) {
        mPokemonRecycler?.visibility = View.GONE
        mSwipeRefreshLayout?.visibility = View.GONE
        mErrorView?.visibility = View.VISIBLE
        Timber.e(error, "There was an error retrieving the pokemon")
    }

    override fun onPokemonClick(pokemon: String) {
        startActivity(DetailActivity.getStartIntent(this, pokemon))
    }

    override fun onReloadData() {
        mMainPresenter.getPokemon(POKEMON_COUNT)
    }

    companion object {
        private val POKEMON_STATE: String = "pokemons";
        private val POKEMON_COUNT = 20
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState?.putStringArrayList(POKEMON_STATE, pokemons)
        super.onSaveInstanceState(outState)
    }
}