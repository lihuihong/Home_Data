package com.home.pojo;

/**
 * 返回结果集
 */
public class Result {

    // code 状态码： 成功：0，失败：1
    private Integer code;
    // 错误信息
    private String msg;
    // 返回的数据
    private Object data;
    //数据条数
    private Integer count;

    public void setSuccess(String msg) {
        this.setCode(0);
        this.setMsg(msg);
    }

    public void setError(String msg) {
        this.setCode(1);
        this.setMsg(msg);
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
}
