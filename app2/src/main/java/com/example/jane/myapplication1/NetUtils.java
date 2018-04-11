package com.example.jane.myapplication1;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

/**
 * Created by jane on 2017/11/29.
 */

public class NetUtils {

    /**
     * 判断是否连接网络  true:连接  false:未连接
     * @param context
     * @return
     */
    public static boolean isConnNet(Context context){
        boolean result=true;
        //1.得到系统服务
        ConnectivityManager manager=(ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        //2.得到网络实体信息
        NetworkInfo info = (NetworkInfo) manager.getActiveNetworkInfo();
        if(info!=null && info.isAvailable()){//代表已经连接网络
            result=true;
        }else {
            result=false;
        }
        return result;
    }

    /**
     * 打开系统设置页面 设置网络
     * @param context
     */
    public static void openSetNetDg(final Context context){
        //创建一个dialog对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("未连接网络，是否进行设置?");
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //跳转到系统的设置网络的页面
                Intent intent;
                // 判断手机系统的版本 即API大于10 就是3.0或以上版本
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(
                            android.provider.Settings.ACTION_WIFI_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName(
                            "com.android.settings",
                            "com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                context.startActivity(intent);

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();


    }
}
