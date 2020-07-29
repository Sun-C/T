package com.jt.test;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestHttpClient {
    /**
     * 案例说明:                           万能用法
     * 	1.利用httpClient机制 访问百度服务器.   http://www.baidu.com
     * 	2.实现步骤: (了解即可)
     * 		1.定义请求网址
     * 		2.定义httpClient工具API对象
     * 		3.定义请求的类型
     * 		4.发起请求,获取返回值结果
     * 		5.校验返回值
     * 		6.获取返回值结果数据.
     * @throws IOException
     * //@throws ClientProtocolException
     */
    @Test
    public void HttpClient() throws IOException {
       // String url = "http://www.baidu.com";//任意网络资源,包括我们业务服务器
        String url = "http://manage.jt.com/web/testCors";//获取的是json数据
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);//不能保证网络请求一定能成功
        //定义请求之后需要判断返回值是否正确 一般条件下判断响应的状态信息是否是200

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode==200){
            //标识响应正常 获取返回值实体对象
            HttpEntity httpEntity = httpResponse.getEntity();
            //将远程返回的信息,转化为字符串, 方便调用 1.json 2.html代码段
            String result = EntityUtils.toString(httpEntity,"utf8");
            User user = ObjectMapperUtil.toObject(result, User.class);
            System.out.println(user);
        }
    }
}
