package com.example.myapp.api;

import android.util.Log;

import com.example.myapp.util.AppConfig;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Api {
    private static OkHttpClient client;
    private static String requestUrl;
    private static HashMap<String,Object> mParams;
    public static Api api = new Api();

    public Api (){

    }

    public static Api config (String url, HashMap<String,Object> params){
        client = new OkHttpClient.Builder()
                .build();
        requestUrl = ApiConfig.BASE_URL + url;
        mParams = params;
        return api;
    }

    public void postRequest(final Callback callback){
        JSONObject jsonObject = new JSONObject(mParams);
        String jsonStr = jsonObject.toString();
        RequestBody requestBodyJson =
                RequestBody.create(MediaType.parse("application/json;charset=UTF-8")
                        ,jsonStr);

        // 第二步创建request
        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("contentType","application/json;charset=UTF-8")
                .post(requestBodyJson)
                .build();

        //第三步创建call回调对象
        final Call call = client.newCall(request);

        //第四步发起请求
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("onFailure",e.getMessage());
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                callback.onSuccess(result);
            }
        });
    }
}
