package com.module.questionnaire.utils.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.module.questionnaire.bean.response.AnswerResponse;
import com.module.questionnaire.bean.response.AppConfigResponse;
import com.module.questionnaire.bean.response.LoginResponse;
import com.module.questionnaire.bean.response.QuestionResponse;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import rx.Observable;

public class NewApiRetrofit extends BaseApiRetrofit {

    public Api mApi;
    public static NewApiRetrofit mInstance;

    private NewApiRetrofit() {
        super();
        Gson gson = new GsonBuilder().setLenient().create();

        //在构造方法中完成对Retrofit接口的初始化
        mApi = new Retrofit.Builder()
                .baseUrl(Constant.URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(Api.class);
    }

    public static NewApiRetrofit getInstance() {
        if (mInstance == null) {
            synchronized (NewApiRetrofit.class) {
                if (mInstance == null) {
                    mInstance = new NewApiRetrofit();
                }
            }
        }
        return mInstance;
    }

    @Multipart
    private RequestBody getRequestBody(Object obj) {
        String route = new Gson().toJson(obj);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), route);
        return body;
    }

    //登录
    public Observable<LoginResponse> login(Map<String, String> params) {
        return mApi.login(getRequestBody(params));
    }


    public Observable<QuestionResponse> getQuestionList(Map<String, String> params) {
        return mApi.getQuestionList(getRequestBody(params));
    }

    public Observable<AnswerResponse> getAnswerList() {
        return mApi.getAnswerList();
    }
}