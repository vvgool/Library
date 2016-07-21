package vv.codelib.Retrofit;

import java.util.HashMap;

/**
 * Created by vvgool on 2016/5/24.
 */
public class ApiFactory {
    private static ApiFactory mApiFactory;
    private static HashMap<String,Object> mServiceMap = new HashMap<String,Object>();

    public static ApiFactory getInstance(){
        if (mApiFactory == null){
            synchronized (ApiFactory.class){
                if (mApiFactory == null){
                    mApiFactory = new ApiFactory();
                }
            }
        }
        return mApiFactory;
    }

    public <T> T create(Class<T> tClass){
        Object service = mServiceMap.get(tClass.getName());
        if (service == null){
            service = RetrofitConfig.getRetrofit().create(tClass);
            mServiceMap.put(tClass.getName(),service);
        }
        return (T) service;
    }
}
