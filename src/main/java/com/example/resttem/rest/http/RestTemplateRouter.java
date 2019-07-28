package com.example.resttem.rest.http;

import org.springframework.web.client.RestTemplate;

/**
 * Created by wangxindong on 18/5/22
 */
public abstract class RestTemplateRouter<T extends RestTemplate> implements Router<RestTemplate,RestTemplateType> {

    @Override
    public abstract RestTemplate choose(RestTemplateType key);
}
