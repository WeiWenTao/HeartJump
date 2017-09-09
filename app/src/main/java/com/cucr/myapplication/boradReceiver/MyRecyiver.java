package com.cucr.myapplication.boradReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cucr.myapplication.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by cucr on 2017/9/8.
 */

public class MyRecyiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d("onReceive", "onReceive - " + intent.getAction());

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
                .getAction())) {
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
            String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);

            System.out.println("收到了自定义消息@@消息内容是:"+ content);
            System.out.println("收到了自定义消息@@消息extra是:"+ extra);

            //**************解析推送过来的json数据并存放到集合中 begin******************
            Map<String, Object> map = new HashMap<String, Object>();
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(extra);
                String type = jsonObject.getString("type");
                map.put("type", type);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            map.put("content", content);
            //获取接收到推送时的系统时间
            Calendar rightNow = Calendar.getInstance();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            String date = fmt.format(rightNow.getTime());
            map.put("date", date);
//            MyApp.data.add(map);
            //**************解析推送过来的json数据并存放到集合中 end******************


        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                .getAction())) {
            System.out.println("收到了通知");
            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {
            String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            System.out.println("用户点击打开了通知");
            System.out.println("content"+content);
            System.out.println("extra"+extra);
            // 在这里可以自己写代码去定义用户点击后的行为
            Intent i = new Intent(context, MainActivity.class); // 自定义打开的界面
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
            Log.d("onReceive", "Unhandled intent - " + intent.getAction());
        }
    }
}
