package com.fucct.reactivepractice.application;

import com.fucct.reactivepractice.domain.NewsLetter;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class NewsServiceSubscriber implements Subscriber<NewsLetter> {
    private final Queue<NewsLetter> mailbox = new ConcurrentLinkedQueue<>();
    private final int take;
    private final AtomicInteger remaining = new AtomicInteger();
    private Subscription subscription;

    public NewsServiceSubscriber(int take) {
        this.take = take;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(take);
    }

    @Override
    public void onNext(NewsLetter newsLetter) {
        mailbox.offer(newsLetter);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }

    public Optional<NewsLetter> eventuallyReadDigest() {
        NewsLetter letter = mailbox.poll();
        if (letter != null) {
            if (remaining.decrementAndGet() == 0) {
                subscription.request(take);
                remaining.set(take);
            }
            return Optional.of(letter);
        }
        return Optional.empty();
    }
}
