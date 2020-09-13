package com.superflower.common.entity.vo;

import java.io.Serializable;
import java.util.HashMap;

public class R extends HashMap implements Serializable {
    public static R success() {
        R r = new R();
        r.put("message", StatusCode.SUCCESS.getMessage());
        r.put("code", StatusCode.SUCCESS.getCode());
        r.put("data", null);
        return r;
    }

    @Deprecated
    public static R success(String message, Long code, Object data) {
        R r = new R();
        r.put("message", message);
        r.put("code", code);
        r.put("data", data);
        return r;
    }

    public static R success(StatusCode statusCode , Object data) {
        R r = new R();
        r.put("message", statusCode.getMessage());
        r.put("code", statusCode.getCode());
        r.put("data", data);
        return r;
    }

    public static R error() {
        R r = new R();
        r.put("message", StatusCode.ERROR.getMessage());
        r.put("code", StatusCode.ERROR.getCode());
        r.put("data", null);
        return r;
    }

    public static R error(StatusCode statusCode) {
        R r = new R();
        r.put("message", statusCode.getMessage());
        r.put("code", statusCode.getCode());
        r.put("data", null);
        return r;
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
