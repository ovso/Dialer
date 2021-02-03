package io.github.ovso.dialer.utils.rx

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

object SchedulerProvider {
    fun io(): Scheduler = Schedulers.newThread()
    fun ui(): Scheduler = AndroidSchedulers.mainThread()
}
