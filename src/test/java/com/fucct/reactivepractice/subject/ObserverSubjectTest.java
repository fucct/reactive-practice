package com.fucct.reactivepractice.subject;

import com.fucct.reactivepractice.observer.ConcreteObserverA;
import com.fucct.reactivepractice.observer.ConcreteObserverB;
import com.fucct.reactivepractice.observer.Observer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ObserverSubjectTest {

    @Test
    void observersHandleEventsFromString() {
        Subject<String> subject = new ConcreteSubject();
        Observer<String> observerA = spy(new ConcreteObserverA());
        Observer<String> observerB = spy(new ConcreteObserverB());

        subject.notifyObservers("No listeners");

        subject.registerObserver(observerA);
        subject.notifyObservers("Message for A");

        subject.unregisterObserver(observerA);
        subject.registerObserver(observerB);
        subject.notifyObservers("Message for B");

        subject.registerObserver(observerA);
        subject.notifyObservers("Message for A, B");

        verify(observerA, times(1)).observe("Message for A");
        verify(observerA, times(1)).observe("Message for A, B");
        verifyNoMoreInteractions(observerA);

        verify(observerB, times(1)).observe("Message for B");
        verify(observerB, times(1)).observe("Message for A, B");
        verifyNoMoreInteractions(observerB);
    }
}