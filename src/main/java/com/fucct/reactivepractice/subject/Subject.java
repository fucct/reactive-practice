package com.fucct.reactivepractice.subject;

import com.fucct.reactivepractice.observer.Observer;

public interface Subject<T> {
    void registerObserver(Observer<T> observer);

    void unregisterObserver(Observer<T> observer);

    void notifyObservers(T event);
}
