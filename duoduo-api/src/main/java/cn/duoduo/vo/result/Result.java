/**
 * FileName: Result
 * Author: xivin
 * Date: 2019-09-09 16:30
 * Description:
 */
package cn.duoduo.vo.result;

import java.io.Serializable;

public class Result<T> implements Serializable {

    private Integer code;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result(Integer code, T data) {
        this.code = code;
        this.data = data;
    }
}
