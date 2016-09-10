package com.ahao.wnacg.util;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Avalon on 2016/8/26.
 */
public class RxBus {
    private RxBus() {
        _bus = new SerializedSubject<>(PublishSubject.create());
    }

    private static volatile RxBus instance;

    public static RxBus getDefault() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    private final Subject<Object, Object> _bus;

    /** 发送事件 */
    public void post(Object o) {
        _bus.onNext(o);
    }

    /** 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者 */
    public <T> Observable<T> toObservable (Class<T> eventType) {
        return _bus.ofType(eventType);
    }

}

