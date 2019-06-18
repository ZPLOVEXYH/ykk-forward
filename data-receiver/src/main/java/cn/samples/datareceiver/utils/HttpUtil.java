package cn.samples.datareceiver.utils;

import com.alibaba.fastjson.JSON;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import java.util.Map;

/**
 * @author ZhangPeng
 */
public class HttpUtil {

    /**
     * 发送Get请求
     *
     * @param url    : 请求的连接
     * @param params ： 请求参数，无参时传null
     * @return
     */
    public static String sendGet(String url, Map<String, String> params) {
        HttpRequest request = HttpRequest.get(url);
        if (params != null) {
            request.query(params);
        }
        HttpResponse response = request.send();
        String respJson = response.bodyText();
        return respJson;
    }


    /**
     * 发送Post请求-json数据
     *
     * @param url    : 请求的连接
     * @param params ：  请求参数，无参时传null
     * @return
     */
    public static String sendPostToJson(String url, Map<String, Object> params) {
        HttpRequest request = HttpRequest.post(url);
        request.contentType("application/json");
        request.charset("utf-8");

        //参数详情
        if (params != null) {
            System.out.println("请求接口" + url + "的请求报文为：" + JSON.toJSONString(params));
            request.body(JSON.toJSONString(params));
        }

        HttpResponse response = request.send();
        String respJson = response.bodyText();
        return respJson;
    }

    /**
     * 发送Post请求-json数据
     *
     * @param url    : 请求的连接
     * @param params ：  请求参数，无参时传null
     * @return
     */
    public static String sendPostToJson(String url, String jsonString) {
        HttpRequest request = HttpRequest.post(url);
        request.contentType("application/json");
        request.charset("utf-8");

        //参数详情
        if (jsonString != null) {
            System.out.println("请求接口" + url + "的请求报文为：" + jsonString);
            request.body(jsonString);
        }

        HttpResponse response = request.send();
        String respJson = response.bodyText();
        return respJson;
    }

    /**
     * 发送Post请求
     *
     * @param url    : 请求的连接
     * @param params ：  请求参数，无参时传null
     * @return
     */
    public static String sendPost(String url, Map<String, Object> params) {
        HttpRequest request = HttpRequest.post(url);

        //参数详情
        if (params != null) {
            request.form(params);
        }
        HttpResponse response = request.send();
        String respJson = response.bodyText();
        return respJson;
    }

    /**
     * 发送Delete请求
     *
     * @param url    : 请求的连接
     * @param params ：  请求参数，无参时传null
     * @return
     */
    public static String sendDelete(String url, Map<String, Object> params) {
        HttpRequest request = HttpRequest.delete(url);

        if (params != null) {
            request.form(params);
        }
        HttpResponse response = request.send();
        String respJson = response.bodyText();
        return respJson;
    }
}
