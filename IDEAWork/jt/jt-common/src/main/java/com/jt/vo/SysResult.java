package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class SysResult implements Serializable {
    /**
     * 属性1: status : 200正常/201失败
     * 属性2: msg 服务器返回的相关说明信息
     * 属性3: 服务器返回的查询数据
     */
    private Integer status;
    private String msg;
    private Object data;
    //服务API 方便使用
    //成功
    public static SysResult success(Object data){
        return SysResult.success(null,data);
    }
    //成功
    public static SysResult success(String msg){
        return SysResult.success(msg,null);
    }
    //成功
    public static SysResult success(String msg,Object data){
        return new SysResult(200,msg,data);
    }
    //失败
    public static SysResult fail(Object data){
        return SysResult.success(null,data);
    }
    //失败
    public static SysResult fail(Throwable e){
        return new SysResult(201,e.getMessage(),null);
    }

    //失败
    public static SysResult fail(String msg){
        return new SysResult(201,"业务调用失败",null);
    }
}
