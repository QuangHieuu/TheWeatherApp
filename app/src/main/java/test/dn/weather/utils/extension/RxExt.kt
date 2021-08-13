package test.dn.weather.utils.extension

import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import test.dn.weather.utils.InternetException
import test.dn.weather.utils.InternetManager

class SchedulerProvider : BaseSchedulerProvider {

    @NonNull
    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    @NonNull
    override fun io(): Scheduler {
        return Schedulers.io()
    }

    @NonNull
    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}

interface BaseSchedulerProvider {

    @NonNull
    fun computation(): Scheduler

    @NonNull
    fun io(): Scheduler

    @NonNull
    fun ui(): Scheduler
}

/**
 * Use SchedulerProvider configuration for Completable
 */
fun Completable.withScheduler(scheduler: BaseSchedulerProvider): Completable =
    this.subscribeOn(scheduler.io()).observeOn(scheduler.ui()).checkInternet()

/**
 * Use SchedulerProvider configuration for Single
 */
fun <T> Single<T>.withScheduler(scheduler: BaseSchedulerProvider): Single<T> =
    this.subscribeOn(scheduler.io()).observeOn(scheduler.ui()).checkInternet()

/**
 * Use SchedulerProvider configuration for Observable
 */
fun <T> Observable<T>.withScheduler(scheduler: BaseSchedulerProvider): Observable<T> =
    this.subscribeOn(scheduler.io()).observeOn(scheduler.ui()).checkInternet()

/**
 * Use SchedulerProvider configuration for Flowable
 */
fun <T> Flowable<T>.withScheduler(scheduler: BaseSchedulerProvider): Flowable<T> =
    this.subscribeOn(scheduler.io()).observeOn(scheduler.ui()).checkInternet()

fun <T> Single<T>.loading(liveData: MutableLiveData<Boolean>) =
    doOnSubscribe { liveData.postValue(true) }.doFinally { liveData.postValue(false) }

fun <T> Observable<T>.checkInternet(): Observable<T> = doOnSubscribe {
    val isConnected = InternetManager.isConnected()
    if (!isConnected) {
        throw InternetException()
    }
}

fun <T> Flowable<T>.checkInternet(): Flowable<T> = doOnSubscribe {
    val isConnected = InternetManager.isConnected()
    if (!isConnected) {
        throw InternetException()
    }
}

fun <T> Single<T>.checkInternet() = doOnSubscribe {
    val isConnected = InternetManager.isConnected()
    if (!isConnected) {
        throw InternetException()
    }
}

fun Completable.checkInternet(): Completable = doOnSubscribe {
    val isConnected = InternetManager.isConnected()
    if (!isConnected) {
        throw InternetException()
    }
}

fun <T> Observable<T>.loading(liveData: MutableLiveData<Boolean>): Observable<T> =
    doOnSubscribe { liveData.postValue(true) }.doFinally { liveData.postValue(false) }

fun <T> Flowable<T>.loading(liveData: MutableLiveData<Boolean>): Flowable<T> =
    doOnSubscribe { liveData.postValue(true) }.doFinally { liveData.postValue(false) }

fun Completable.loading(liveData: MutableLiveData<Boolean>) =
    doOnSubscribe { liveData.postValue(true) }.doFinally { liveData.postValue(false) }
