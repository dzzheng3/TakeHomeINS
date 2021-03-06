package com.zheng.home.util.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ComputationMainScheduler<T> protected constructor() : BaseScheduler<T>(Schedulers.computation(), AndroidSchedulers.mainThread())
