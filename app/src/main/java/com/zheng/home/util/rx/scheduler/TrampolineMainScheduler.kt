package com.zheng.home.util.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class TrampolineMainScheduler<T> protected constructor() : BaseScheduler<T>(Schedulers.trampoline(), AndroidSchedulers.mainThread())
