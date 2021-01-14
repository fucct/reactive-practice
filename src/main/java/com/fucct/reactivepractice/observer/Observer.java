package com.fucct.reactivepractice.observer;

public interface Observer<T> {
    void observe(T event);
}
