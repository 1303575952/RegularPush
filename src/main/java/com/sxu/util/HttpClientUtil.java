package com.sxu.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sxu.entity.JsonEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lifei
 */
public class HttpClientUtil {
    /**
     * 使用httpclient进行http通信
     * <p>
     * Get请求
     */
    public static void httpclientGet(String urlStr) throws Exception {

        System.out.println(urlStr);

        // 创建HttpClient对象
        HttpClient client = HttpClients.createDefault();

        // 创建GET请求（在构造器中传入URL字符串即可）
        HttpGet get = new HttpGet(urlStr);

        // 调用HttpClient对象的execute方法获得响应
        HttpResponse response = client.execute(get);

        // 调用HttpResponse对象的getEntity方法得到响应实体
        HttpEntity httpEntity = response.getEntity();

        // 使用EntityUtils工具类得到响应的字符串表示
        String result = EntityUtils.toString(httpEntity, "utf-8");
        System.out.println(result);
    }

    /**
     * 使用httpclient进行http通信
     * <p>
     * Post请求
     */
    public static void httpclientPost(String urlStr, List<NameValuePair> parameters) throws Exception {

        System.out.println(urlStr);

        // 创建HttpClient对象
        HttpClient client = HttpClients.createDefault();

        // 创建POST请求
        HttpPost post = new HttpPost(urlStr);

        // 创建一个List容器，用于存放基本键值对（基本键值对即：参数名-参数值）--> parameters

        // 向POST请求中添加消息实体
        post.setEntity(new UrlEncodedFormEntity(parameters, "utf-8"));

        // 得到响应并转化成字符串
        HttpResponse response = client.execute(post);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity, "utf-8");
        System.out.println(result);
    }

    public static JSONObject httpPostWithJSON(String url, String json) {
        // 创建默认的httpClient实例
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httppost
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("Content-type", "application/json; charset=utf-8");
            System.out.println("executing request " + httppost.getURI());

            // 向POST请求中添加消息实体
            StringEntity se = new StringEntity(json, "UTF-8");
            httppost.setEntity(se);
            System.out.println("request parameters " + json);

            // 执行post请求
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应内容
                    System.out.println("--------------------------------------");
                    //System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));

                    //return JSON.parseObject(EntityUtils.toString(entity, "UTF-8"));
                    JsonEntity.jsonEntity = JSON.parseObject(EntityUtils.toString(entity, "UTF-8"));
                    Thread.sleep(1000);
                    System.out.println("post之后：" + JsonEntity.jsonEntity);
                    System.out.println("--------------------------------------");
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            System.out.println("executing httpPostWithJSON error: " + e.getMessage());
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                System.out.println("executing httpPostWithJSON error: " + e.getMessage());
            }
        }
        return null;
    }

    public static void main(String[] args) {
        List<NameValuePair> parametersHttpclientPost = new ArrayList<NameValuePair>();
        parametersHttpclientPost.add(new BasicNameValuePair("aqi", "3.3"));
        parametersHttpclientPost.add(new BasicNameValuePair("co", "33"));
        String jsonStr = "{\n" +
                "\"async\":0,\n" +
                "\"callback\":\"\",\n" +
                "\"method\":\"常规管控\",\n" +
                "\"param\": {\"企业名称\":\"沁源县兴茂煤化有限公司\",\n" +
                "\"监控点名称\":\"焦炉烟囱排口\",\n" +
                "\"起始日期\":\"2018年12月16日\",\n" +
                "\"天数\":\"1\"}\n" +
                "}";
        String jsonEnterpriseOutletInfoJson = "{\n" +
                "\"async\": 0,\n" +
                "\"callback\": \"\",\n" +
                "\"method\": \"企业列表\",\n" +
                "\"param\": {}\n" +
                "}";
        try {
            //HttpClientUtil.httpclientGet("http://127.0.0.1:8080/sysaqiinfo/findSysAqiInfoNoQuery?pageIndex=1&pageSize=1");
            //HttpClientUtil.httpclientPost("http://127.0.0.1:8080/sysaqiinfo/add", parametersHttpclientPost);
            HttpClientUtil.httpPostWithJSON("http://119.90.57.34:9680/channel/do", jsonEnterpriseOutletInfoJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}