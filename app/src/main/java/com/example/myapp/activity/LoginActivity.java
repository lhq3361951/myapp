package com.example.myapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.api.Api;
import com.example.myapp.api.ApiConfig;
import com.example.myapp.api.Callback;
import com.example.myapp.util.AppConfig;
import com.example.myapp.util.StringUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    private EditText etAccount;
    private EditText etPwd;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etAccount = findViewById(R.id.etAccount);
        etPwd = findViewById(R.id.etPwd);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etAccount.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                login(account,pwd);
            }
        });
    }

    private void login(String account,String pwd){
        if(TextUtils.isEmpty(account)){
            showToast("请输入账号");
            return;
        }
        if (StringUtils.isEmpty(pwd)){
            showToast("请输入密码");
            return;
        }
        HashMap<String,Object> params = new HashMap();
        params.put("mobile",account);
        params.put("password",pwd);
        Api.config(ApiConfig.LOGIN,params).postRequest(new Callback() {
            @Override
            public void onSuccess(final String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(res);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

  /*  private void login(String account, String pwd){
        if(TextUtils.isEmpty(account)){
            showToast("请输入账号");
            return;
        }
        if (StringUtils.isEmpty(pwd)){
            showToast("请输入密码");
            return;
        }
         //第一步创建OKhttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Map m = new HashMap();
        m.put("mobile",account);
        m.put("password",pwd);
        JSONObject jsonObject = new JSONObject(m);
        String jsonStr = jsonObject.toString();
        RequestBody requestBodyJson =
                RequestBody.create(MediaType.parse("application/json;charset=UTF-8")
                        ,jsonStr);

        // 第二步创建request
        Request request = new Request.Builder()
                .url(AppConfig.BASE_URL+"/app/login")
                .addHeader("contentType","application/json;charset=UTF-8")
                .post(requestBodyJson)
                .build();

        //第三步创建call回调对象
        final Call call = client.newCall(request);

        //第四步发起请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("onFailure",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(result);
                    }
                });
            }
        });
    }*/

}
