package com.android.soundlly.intern.jisu.bitsoundtest.controll;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * Created by jisu on 2017. 3. 10..
 */

public class MicPermission {
    Observable<Integer> micObservable;

    public MicPermission(){
        micObservable = Observable.create(observableOnSubscribe);
    }

    public void checkMicPermission(Integer state) {
        subscriber.onNext(state);
    }

    ObservableOnSubscribe<Integer> observableOnSubscribe = new ObservableOnSubscribe<Integer>() {
        @Override
        public void subscribe(ObservableEmitter<Integer> e) throws Exception {

        }
    };

    public interface Emitter<Integer> {

    }


    Subscriber<Integer> subscriber = new Subscriber<Integer>() {
        @Override
        public void onSubscribe(Subscription s) {

        }

        @Override
        public void onNext(Integer integer) {

        }

        @Override
        public void onError(Throwable t) {

        }

        @Override
        public void onComplete() {

        }
    };
}
