package com.edu.cqupt.software7.common;

public class FileResult {
    private Object data;
    private String msg;
    private int code;

    public FileResult() {
    }

    // getter setter 省略，构造方法省略
    // 操作成功返回数据
    public static FileResult succ(Object data) {
        return succ(200, "操作成功", data);
    }

    public static FileResult succ(String msg) {
        return succ(200, msg, null);
    }


    public static FileResult succ(int code, String msg, Object data) {
        FileResult r = new FileResult();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static FileResult succ(String msg, Object data) {
        return succ(200, msg, data);
    }

    // 操作异常返回
    public static FileResult fail(int code, String msg, Object data) {
        FileResult r = new FileResult();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static FileResult fail(String msg) {
        return fail(400, msg, null);
    }

    public static FileResult fail(int code, String msg) {
        return fail(code, msg, "null");
    }

    public static FileResult fail(String msg, Object data) {
        return fail(400, msg, data);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
