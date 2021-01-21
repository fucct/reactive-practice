package com.fucct.reactivepractice.reactor;

import org.assertj.core.internal.IntArrays;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ReactorTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    void fluxSequenceTest() {
        Flux<Integer> range = Flux.range(2020, 5);

        StepVerifier.create(range)
                .expectNextSequence(Arrays.asList(2020, 2021, 2022, 2023, 2024))
                .verifyComplete();
    }

    @Test
    void subscribeTest() {
        Flux<Integer> pub = Flux.range(2020, 5);
        pub.subscribe(
                data -> logger.info("onNext: {}", data),
                err -> logger.info("onError: {}", err),
                () -> logger.info("onComplete"),
                subscription -> {
                    subscription.request(4);
                    subscription.cancel();
                }
        );

        StepVerifier.create(pub)
                .expectNext(2020)
                .expectNext(2021)
                .expectNext(2022)
                .expectNext(2023)
                .expectNext(2024)
                .verifyComplete();
    }

    @Test
    void indexTest() {
        Flux.range(2018, 5)
                .timestamp()
                .index()
                .subscribe(e -> logger.info("index: {}, ts: {}, value: {}", e.getT1(), Instant.ofEpochMilli(e.getT2().getT1()), e.getT2().getT2()));
    }

    @Test
    void filter() {
        Mono<Long> startCommand = Mono.delay(Duration.ofSeconds(2));
        Mono<Long> stopCommand = Mono.delay(Duration.ofSeconds(3));
        Flux.range(1994, 28)
                .repeat()
                .skipUntilOther(startCommand)
                .takeUntilOther(stopCommand)
                .subscribe(System.out::println);
    }

    @Test
    void collect() {
        Mono<List<Integer>> sortedList = Flux.just(1, 6, 2, 8, 3, 1, 5, 1)
                .collectSortedList(Comparator.reverseOrder());
        sortedList.subscribe(System.out::println);

        StepVerifier.create(sortedList)
                .expectNext(Lists.newArrayList(8, 6, 5, 3, 2, 1, 1, 1))
                .verifyComplete();
    }

    @Test
    void reduce() {
        Mono<Integer> sum = Flux.range(1, 5)
                .reduce(0, Integer::sum);
        sum
                .subscribe(result -> logger.info("Result: {}", result));

        StepVerifier.create(sum)
                .expectNext(15)
                .verifyComplete();
    }

    @Test
    void scan() {
        Flux<Integer> sum = Flux.range(1, 5)
                .scan(0, Integer::sum);
        sum
                .subscribe(result -> logger.info("Result: {}", result));

        StepVerifier.create(sum)
                .expectNextSequence(Arrays.asList(0, 1, 3, 6, 10, 15))
                .verifyComplete();
    }

    @Test
    void thenMany() {
        Flux.just(1, 2, 3)
                .thenMany(Flux.just(4, 5))
                .subscribe(e -> logger.info("onNext: {}", e));
    }
}
