package com.example.resttem.rest.http;

/**
 * Created by wangxindong on 18/4/20
 */
public interface Router<T,K> {

    T choose(K key);

}
