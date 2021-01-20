package com.fucct.reactivepractice.domain;

import org.junit.jupiter.api.Test;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RxJavaTest {

    @Test
    void rxJava() {
        Observable<Object> observable = Observable.create(sub -> {
            sub.onNext("Hello, reactive world!");
            sub.onCompleted();
        });
        observable.subscribe(System.out::println, System.out::println, () -> System.out.println("Done!"));
        observable.subscribe(System.out::println, System.out::println, () -> System.out.println("Done!"));
    }

    @Test
    void concat() {
        Observable<String> hello = Observable.fromCallable(() -> "hello");
        Future<String> future = Executors.newCachedThreadPool().submit(() -> "World");
        Observable<String> world = Observable.from(future);
        Observable.concat(hello, world, Observable.just("!"))
                .forEach(System.out::print);
    }

    @Test
    void asyncSequence() throws InterruptedException {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(e -> System.out.println("Received: " + e));
        Thread.sleep(5000);
    }

    @Test
    void zip() {
        Observable.zip(Observable.just("A", "B", "C"), Observable.just(1, 2, 3), (x, y) -> x + y).forEach(System.out::print);
    }
}