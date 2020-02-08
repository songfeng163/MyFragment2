package org.bzu.sf.myfragment2;

import com.alibaba.fastjson.JSON;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 解析json文件信息
 */
public class WeatherService {
    // 解析json文件返回的天气信息的集合
    public static List<WeatherInfo> getInfosFromJson(InputStream is) throws IOException {
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        String json = new String(buffer, "utf-8");

        List<WeatherInfo> list = null;

        list = JSON.parseArray(json, WeatherInfo.class);   // 一句话完成数据的解析

        return list;
    }
}
