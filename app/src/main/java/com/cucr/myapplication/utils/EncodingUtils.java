package com.cucr.myapplication.utils;

import android.content.Context;
import android.util.Base64;

import com.cucr.myapplication.constants.SpConstant;
import com.yanzhenjie.nohttp.tools.MultiValueMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 911 on 2017/8/14.
 */

public class EncodingUtils {
    public static String getEdcodingSReslut(Context context, MultiValueMap<String, Object> multiValueMap) {

        //第二步,定义List用于存储所有请求参数的key
        List<String> keyList = new ArrayList<>();

        //第三步:定义Map用于存储所有请求参数的value
        Map<String, String> keyValueMap = new HashMap<>();

        //第四步:拿到所有具体请求参数
        for (Map.Entry<String, List<Object>> paramsEntry : multiValueMap.entrySet()) {
            String key = paramsEntry.getKey();
            List<Object> values = paramsEntry.getValue();
            for (Object value : values) {
                if (value instanceof String) {

                    //第五步:将请求参数的key添加到list中用于排序
                    keyList.add(key);

                    //第六步:将请求参数的value添加到Map中
                    keyValueMap.put(key, (String) value);
                    MyLogger.jLog().i("key:"+key+",Value"+ (String) value);
                }
            }
        }

        //第七步:对请求参数key进行排序
        Collections.sort(keyList);

        StringBuilder paramsBuilder = new StringBuilder();

        //第八步:依次取出排序之后的key-value,并拼接
        for (String key : keyList) {
            String value = keyValueMap.get(key);
            paramsBuilder.append(value);
        }

//        String params = "";
//        if (paramsBuilder.length() > 0) {
//            //去掉最后一个&
//            params = paramsBuilder.toString().substring(0, paramsBuilder.length() - 1);
//        }

        //第九步:对拼接好的参数进行MD5加密
        paramsBuilder.append(SpUtil.getParam(context, SpConstant.SIGN, "error"));


        String params = paramsBuilder.toString();
        MyLogger.jLog().i("sign:params:"+params);
        String decodeBase64 = Base64.encodeToString(paramsBuilder.toString().getBytes(), Base64.DEFAULT);
        decodeBase64 = decodeBase64.replace("\n","");
        MyLogger.jLog().i("sign:decodeBase64:"+decodeBase64);
        String md5 = MD5Util.md5(decodeBase64).toUpperCase();
        MyLogger.jLog().i("sign:md5:"+md5);

        return md5;
    }
}
