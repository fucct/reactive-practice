package com.fucct.reactivepractice.presentation;

import com.fucct.reactivepractice.domain.Temperature;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import rx.Subscriber;

import java.io.IOException;

public class RxSseEmitter extends SseEmitter {
    private static final long SSE_SESSION_TIMEOUT = 30 * 60 * 1000L;
    private final Subscriber<Temperature> subscriber;

    public RxSseEmitter() {
        super(SSE_SESSION_TIMEOUT);
        this.subscriber = new Subscriber<Temperature>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(Temperature temperature) {
                try {
                    RxSseEmitter.this.send(temperature);
                } catch (IOException e) {
                    unsubscribe();
                }
                onCompletion(subscriber::unsubscribe);
                onTimeout(subscriber::unsubscribe);
            }

            Subscriber<Temperature> getSubscriber() {
                return subscriber;
            }
        };
    }

    public Subscriber<Temperature> getSubscriber() {
        return subscriber;
    }
}
