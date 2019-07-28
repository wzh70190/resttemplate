package com.example.resttem.rest.http;

import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;

@RestController
public class testMain {


    @Autowired
    private HttpExecutors httpClient;

    @ResponseBody
    @GetMapping("test1")
    public void test1() {

        String x = "http://test.jubao56.com/general?method=userlogin&app_id=zhongchu&app_secret=mlk34kz98u23lkjsa932lksa&app_key=M1ulq7IP&param=K8RbQIUPG1LTPg%2FHjpLa%2FjSW4OiNx4rHW2yqmLOYwSf%2F111ry9D3oeK1%2Bv2bXxb0";
        try {
            JSONObject excutor = httpClient.executor((RestTemplate r) -> r.getForObject(new URI(x), JSONObject.class),
                    (RetryException e) -> {
                        if (null != e.getResult()) {
                            String errCode = ((JSONObject) (e.getResult())).getString("err_code");
                            if (!StringUtils.isEmpty(errCode) && "0".equals(errCode)) {
                                return false;
                            }
                            return true;
                        }
                        return true;
                    },
                    new Retryer.RetryMeta(2, 2000), RestTemplateType.COMMON);
            System.out.println(excutor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void asdasd() {
        Object requestObject = new Object();
        //重试机制为全部重试，重试1次，间隔2秒
        try {
            RuntimeNode.Request excutor = httpClient.executor((RestTemplate r) -> r.getForObject("xxxxxxxxx", RuntimeNode.Request.class, requestObject),
                    (RetryException e) -> true,
                    new Retryer.RetryMeta(1, 2000), RestTemplateType.LONG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //重试机制抛出异常，并且异常为NullPointerException时重试，重试3次，间隔1秒
        try {
            HashMap excutor1 = httpClient.executor((RestTemplate r) -> r.getForObject("xxxxxxxxx", HashMap.class, requestObject),
                    (RetryException e) -> e.getException() instanceof NullPointerException,
                    new Retryer.RetryMeta(3, 1000), RestTemplateType.LONG);
        } catch (Exception e) {
            e.printStackTrace();

        }

        //重试机制为返回值为HashMap，并且HashMap包含111的key时进行重试，重试3次，间隔1秒
        try {
            HashMap excutor1 = httpClient.executor((RestTemplate r) -> r.getForObject("xxxxxxxxx", HashMap.class, requestObject),
                    (RetryException e) -> {
                        HashMap result = (HashMap) e.getResult();
                        return result.containsKey("111");
                    },
                    new Retryer.RetryMeta(3, 1000), RestTemplateType.LONG);
        } catch (Exception e) {
            e.printStackTrace();

        }

        //重试机制为全部重试，重试0次，间隔2秒
        try {
            HashMap excutor2 = httpClient.executor((RestTemplate r) -> r.getForObject("xxxxxxxxx", HashMap.class, requestObject),
                    (RetryException e) -> true,
                    new Retryer.RetryMeta(0, 2000), RestTemplateType.LONG);
        } catch (Exception e) {
            e.printStackTrace();

        }

        //重试机制为全部重试，无重试元数据 不进行重试
        try {
            HashMap excutor3 = httpClient.executor((RestTemplate r) -> r.getForObject("xxxxxxxxx", HashMap.class, requestObject),
                    (RetryException e) -> true, null, RestTemplateType.LONG);
        } catch (Exception e) {
            e.printStackTrace();

        }

        //无重试机制，重试3次，间隔1秒 不进行重试
        try {
            HashMap excutor4 = httpClient.executor((RestTemplate r) -> r.getForObject("xxxxxxxxx", HashMap.class, requestObject),
                    null,
                    new Retryer.RetryMeta(3, 1000), RestTemplateType.LONG);
        } catch (Exception e) {
            e.printStackTrace();

        }

        //无重试机制，无重试元数据 不进行重试
        try {
            HashMap excutor5 = httpClient.executor((RestTemplate r) -> r.getForObject("xxxxxxxxx", HashMap.class, requestObject),
                    null,
                    null, RestTemplateType.LONG);
        } catch (Exception e) {
            e.printStackTrace();

        }

        //needRetry为false，不进行重试
        try {
            HashMap excutor6 = httpClient.executor((RestTemplate r) -> r.getForObject("xxxxxxxxx", HashMap.class, requestObject),
                    false, RestTemplateType.LONG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //needRetry为true，按默认机制和默认元数据重试
        try {
            HashMap excutor7 = httpClient.executor((RestTemplate r) -> r.getForObject("xxxxxxxxx", HashMap.class, requestObject),
                    true, RestTemplateType.LONG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //重试机制为全部不重试，重试次数为0，间隔为2秒 不进行重试
        try {
            HashMap excutor8 = httpClient.executor((RestTemplate r) -> r.getForObject("xxxxxxxxx", HashMap.class, requestObject),
                    (RetryException e) -> false,
                    new Retryer.RetryMeta(0, 2000), RestTemplateType.LONG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //重试机制为全部不重试，重试次数为3，间隔为2秒 不进行重试
        try {
            HashMap excutor9 = httpClient.executor((RestTemplate r) -> r.getForObject("xxxxxxxxx", HashMap.class, requestObject),
                    (RetryException e) -> false,
                    new Retryer.RetryMeta(3, 2000), RestTemplateType.LONG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //重试机制为全部不重试，无重试元数据 不进行重试
        try {
            HashMap excutor10 = httpClient.executor(r -> r.getForObject("xxxxxxxxx", HashMap.class, requestObject),
                    (RetryException e) -> false,
                    null, RestTemplateType.LONG);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
