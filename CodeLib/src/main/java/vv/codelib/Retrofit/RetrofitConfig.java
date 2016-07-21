package vv.codelib.Retrofit;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import vv.codelib.config.Configuration;
import vv.codelib.http.HttpLoggingInterceptor;

/**
 * Retrofit 的基础配置
 */
public class RetrofitConfig {
    private static Retrofit mRetrofit;
    private static String mBaseUrl;

    public static Retrofit getRetrofit(){
        if (mRetrofit == null){
            if (mBaseUrl == null || mBaseUrl.length()<=0)
                throw new IllegalStateException("请设置baseUrl！");
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(mBaseUrl)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());
            if (Configuration.isShowNetworkParams()){
                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor()).build();
                builder.client(client);
            }
            mRetrofit = builder.build();
        }
        return mRetrofit;
    }

    public static void setBaseUrl(String url){
        mBaseUrl = url;
    }
}
