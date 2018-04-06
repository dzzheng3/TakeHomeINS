package com.zheng.home.injection.module

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import com.zheng.home.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton


@Module
class NetworkModule(private val context: Context) {

    var cacheSize: Long = 10 * 1024 * 1024 // 10 MB
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    var cache = Cache(context.codeCacheDir, cacheSize)

    @Provides
    @Singleton
    @Named("quiz")
    internal fun provideQuizRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.QUIZ_API_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor,
                                     chuckInterceptor: ChuckInterceptor,
                                     stethoInterceptor: StethoInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(chuckInterceptor)
                .addNetworkInterceptor(stethoInterceptor)
                .cache(cache)
                .build()

    }

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Timber.d(message)
        }.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    internal fun provideChuckInterceptor(): ChuckInterceptor {
        return ChuckInterceptor(context)
    }

    @Provides
    @Singleton
    internal fun provideStetho(): StethoInterceptor {
        return StethoInterceptor()
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

}