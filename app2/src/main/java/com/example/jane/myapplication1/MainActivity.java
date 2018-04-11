package com.example.jane.myapplication1;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnGet;

    @SuppressLint("HandlerLeak")
    private Handler myHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //得到数据
            Result data=(Result) msg.obj;

            List<String> datas=new ArrayList<>();
            
            List<List<String>> result = data.getResult();
            for (int i=0;i<result.size();i++){
                List<String> strings = result.get(i);
                String d=strings.get(0);
                Log.d("zzz","--"+d);
                datas.add(d);
            }

            //设置适配器
            listView.setAdapter(new MyAdapter(MainActivity.this,datas));
        }
    };
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGet = (Button) findViewById(R.id.btn_get);
        listView = (ListView) findViewById(R.id.lv);

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1.首先判断网络是否连接
                boolean connNet = NetUtils.isConnNet(MainActivity.this);
                if(connNet){//是连网状态
                    //开启子线程
                    new Thread(){
                        @Override
                        public void run() {
                            requestNetData();
                        }
                    }.start();

                }else{
                    //跳转到设置界面
                    NetUtils.openSetNetDg(MainActivity.this);
                }

            }
        });

    }

    public void requestNetData(){

        try {//ctrl+alt+t ...try..catch
            //1.创建一个URL,根据接口
            URL url=new URL("https://suggest.taobao.com/sug?code=utf-8&q=%E6%89%8B%E6%9C%BA");
            //2.打开连接，进行请求  alt+enter 自动补全,HttpURLConnection继承自 URLConnection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //3.进行网络请求相关设置
            urlConnection.setRequestMethod("GET");//方法一定要大写 POST
            urlConnection.setConnectTimeout(5000);//服务器连接超时时间
            urlConnection.setReadTimeout(5000);//客户端读取超时时间

            //4.得到服务器的响应码
            int responseCode = urlConnection.getResponseCode();
            if(responseCode==200){//请求成功
                //5.得到服务器返回的数据,以流的形式返回数据
                InputStream inputStream = urlConnection.getInputStream();

                //1.将流转换成字符串，，
                InputStreamReader reader=new InputStreamReader(inputStream);
                BufferedReader br=new BufferedReader(reader);
                StringBuilder builder=new StringBuilder();
                String str;
                while ((str=br.readLine())!=null){
                    builder.append(str);
                }
                br.close();

                //2.进行解析
                Gson  gson=new Gson();
                Result result = gson.fromJson(builder.toString(), Result.class);

                //3.将数据显示在listview的列表

                //将数据发回给主线程
                Message msg=Message.obtain();
                msg.obj=result;
                myHandler.sendMessage(msg);



            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
