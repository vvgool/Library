package vv.httplibrary;

import okhttp3.*;

import java.io.IOException;

/**
 * Created by ljdy on 2016/4/21.
 */
public class HttpClientUtils {
    private static HttpClientUtils mHttpClientUtils;
    private static Request.Builder mBuilder;
    private final OkHttpClient mOkHttpClient;

    private HttpClientUtils(){
        mOkHttpClient = new OkHttpClient();
    }

    public static HttpClientUtils getInstance(){
        if (mHttpClientUtils == null){
            mHttpClientUtils = new HttpClientUtils();
        }
        mBuilder =new Request.Builder();
        return mHttpClientUtils;
    }

    public HttpClientUtils doGet(String url){
        mBuilder.url(url);
        return this;
    }

    public HttpClientUtils doPost(String url, RequestBody requestBody){
        mBuilder.url(url)
                .post(requestBody);
        return this;
    }

    public HttpClientUtils header(String name,String value){
        mBuilder.header(name,value);
        return this;
    }

    public HttpClientUtils addHeader(String name,String value){
        mBuilder.addHeader(name,value);
        return this;
    }

    public HttpClientUtils method(String method,RequestBody requestBody){
        mBuilder.method(method,requestBody);
        return this;
    }

    /**
     * 异步
     */
    public void asynchronizationExcute(Callback responseCall){
        mOkHttpClient.newCall(mBuilder.build()).enqueue(responseCall);
    }

    /**
     * 同步
     */
    public Response synchronizationExcute() throws IOException {
        return mOkHttpClient.newCall(mBuilder.build()).execute();
    }
}
