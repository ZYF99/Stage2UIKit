package com.example.stage2_uikit.main

import android.annotation.SuppressLint
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.internal.operators.observable.ObservableZip
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Callable

val list = listOf<String>("asd", "bsd", "csd")

@SuppressLint("CheckResult")
fun main() {

    Single.just(1)
        .observeOn(Schedulers.newThread())
        .flatMap {
            Single.error<Int>(RuntimeException())
                .subscribeOn(Schedulers.io())
        }
        .subscribeOn(Schedulers.computation())
        .doOnError {
            // `Schedulers.newThread()`创建的线程中
            println(Thread.currentThread())
        }
        .blockingGet()




/*    var a = 0

    Single.fromCallable(object :Callable<Int>{
        override fun call(): Int {
            return a
        }
    }).subscribe { t -> println(t) }

    a++

    Single.just(a).blockingGet()subscribe { t -> println(t) }*/


/*    Observable.fromCallable {
        listOf("sd","asdf")
    }.doOnSubscribe {
        it.dispose()
        println("onSubscribe")
    }

    Observable.create(ObservableOnSubscribe<Int> { })*/

/*

    Observable.fromCallable {
        println("1")
        listOf("1", "2")
    }.doOnSubscribe(object :Consumer<Disposable>{
            override fun accept(disposable: Disposable) {
                println(2)
            }

        })
        .doOnNext { l -> println("3") }
        .flatMap { l ->
            Observable.fromIterable(l)
                .doOnSubscribe { disposable -> println("4") }
                .doOnNext { s -> println("5") }
        }
        .doOnSubscribe { disposable -> println("6") }
        .subscribeOn(Schedulers.io())
        .doOnNext { s -> println("7") }
        .map { s -> s + "map" }
        .doOnSubscribe { disposable -> println("8") }
        .doOnNext { s -> println("9") }
        .blockingSubscribe()
*/


    //concatMapTest()

/*    Observable.create(ObservableOnSubscribe<Int> { e ->
        e.onNext(12)
        e.onNext(23)
        e.onNext(34)
    })
        .flatMap { i -> Observable.fromIterable(listOf("s:$i", "s1:${i + 1}", "s1:${i + 2}")) }
        .subscribe { t -> println(t) }*/

}
/*
@SuppressLint("CheckResult")
fun callableTest() {
    Observable.fromCallable {
        println("1")
        listOf("1", "2")
    }.flatMap {  }
        .subscribe({ s -> println(s) }, { e -> e.printStackTrace() })
}*/


/*@SuppressLint("CheckResult")
fun concatMapTest() {

    Observable.fromArray(list)
        .concatMap(Function<List<String>, Observable<String>> { t ->

            createObservableByStr(it)


        })
        .subscribe { println(it) }


}*/

fun createObservableByStr(string: String): Observable<String> {
    return Observable.create<String> { e -> e.onNext("funtrans $string") }
}


fun flatTest() {
    Observable.just("asd", "bsd", "csd").flatMap(object : Function<String, ObservableSource<String>> {
        override fun apply(t: String): ObservableSource<String> {
            if (t.equals("asd")) {
                return Observable.just("aaaaa", "sdasdsadasfas")
            }

            return Observable.just(t)
        }

    }).subscribe { println(it) }
}


@SuppressLint("CheckResult")
fun mapTest() {
    Observable.just(list).map { t -> t }.subscribe { s -> println(s) }
}




class Test {


}