package com.superflower.common.utils;

import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

public class CodeUtils {
    public static Boolean sendCode(String phone, String verifyCode) {
        String host = "https://feginesms.market.alicloudapi.com";// 【1】请求地址 支持http 和 https 及 WEBSOCKET
        String path = "/codeNotice";// 【2】后缀
        String appcode = "072d3913ed4d4848a45115e5a8306e89"; // 【3】开通服务后 买家中心-查看AppCode
        String sign = "175622"; // 【4】请求参数，详见文档描述
        String skin = "1"; // 【4】请求参数，详见文档描述
        String param = verifyCode; // 【4】请求参数，详见文档描述
        String urlSend = host + path + "?sign=" + sign + "&skin=" + skin + "&param=" + param + "&phone=" + phone; // 【5】拼接请求链接
        try {
            URL url = new URL(urlSend);
            HttpURLConnection httpURLCon = (HttpURLConnection) url.openConnection();
            httpURLCon.setRequestProperty("Authorization", "APPCODE " + appcode);
            int httpCode = httpURLCon.getResponseCode();
            if (httpCode == 200) {
                return true;
            } else return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static String generateRandom() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            result.append(random.nextInt(10));
        }

        return result.toString();
    }

    public static <T> HashMap<String, Object> entityToMap(T obj) {
        HashMap<String, Object> map = new HashMap<>();
        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            // 开启访问权限
            field.setAccessible(true);
            String name = field.getName();
            if (name.equals("serialVersionUID")) continue;
            try {
                Object value = field.get(obj);
                if (value == null) continue;
                map.put(name, value);
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        return map;
    }

    public static void main(String[] args) {
        Pattern p = Pattern.compile(".*\\d+.*");
        System.out.println(p.matcher("/asd/asd").find());
    }
}