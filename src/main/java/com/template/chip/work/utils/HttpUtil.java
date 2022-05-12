package com.template.chip.work.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by juzhihai on 2020/11/26.
 */
public class HttpUtil {

    private static HttpClient httpClient;
    private static PoolingHttpClientConnectionManager manager; // 连接池管理类
    private static ScheduledExecutorService monitorExecutor; // 监控
    private static HttpClientContext httpClientContext = null;
    static CookieStore cookieStore = null;
    public static HttpClient getHttpClient(String url) {
        if(httpClient==null)
        {
            httpClient = HttpClients.createDefault();
        }
        return httpClient;
    }
    public static HttpClientContext getClientContext() {
        if(httpClientContext==null)
        {
            httpClientContext = new HttpClientContext();
        }
        return httpClientContext;
    }


    /**
     * get请求
     * @return
     */
    public static String doGet(String url) {
        try {
            HttpClient client = getHttpClient(url);


            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request,getClientContext());

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());

                return strResult;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * get请求
     * @return
     */
    public static String doGet(String url,String cookie) {
        try {
            HttpClient client = getHttpClient(url);


            //发送get请求
            HttpGet request = new HttpGet(url);
            request.setHeader("Cookie",cookie);
            HttpResponse response = client.execute(request,getClientContext());


            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());

                return strResult;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void setRequestConfig(HttpRequestBase httpRequestBase) {
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(10000)
                .setConnectTimeout(10000).setSocketTimeout(10000).build();
        httpRequestBase.setConfig(requestConfig);
    }

    public static String doPostForm(String url, Map<String, String> params) {
        HttpPost httpPost = new HttpPost(url);
        setRequestConfig(httpPost);
        String resultString = "";

        httpPost.addHeader("origin","https://uuap.vipkid.com.cn");
        httpPost.addHeader("referer","https://uuap.vipkid.com.cn/cas/login");
        httpPost.addHeader("sec-fetch-dest","document");
        httpPost.addHeader("sec-fetch-mode","navigate");
        httpPost.addHeader("sec-fetch-site","same-origin");
        httpPost.addHeader("sec-fetch-user","?1");
        httpPost.addHeader("upgrade-insecure-requests","1");
        httpPost.addHeader("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.67 Safari/537.36");



        HttpResponse response = null;
        try {

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            if (params != null) {
                for (String key : params.keySet()) {
                    builder.addPart(key,
                            new StringBody(params.get(key), ContentType.create("text/plain", Consts.UTF_8)));
                }
            }

            HttpEntity reqEntity = builder.build();
            httpPost.setEntity(reqEntity);



            // 发起请求 并返回请求的响应
            response = getHttpClient(url).execute(httpPost, getClientContext());
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

    public static String doPost(String url, String jsonstr){
        String result = "";
        try{

            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("Host","api.vipkid-inc.com");
            httpPost.addHeader("Origin","https://ad-mr.vipkid-inc.com");
            httpPost.addHeader("Referer","https://ad-mr.vipkid-inc.com");
            httpPost.addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.67 Safari/537.36");

            //此处是将请求体封装成为了StringEntity,若乱码则指定utf-8
            StringEntity se = new StringEntity(jsonstr);
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
            httpPost.setEntity(se);
            HttpResponse response = getHttpClient(url).execute(httpPost);

            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,"utf-8");
                }
            }
        }catch(Exception ex){
            //连接超时或者数据传输时间超时
            String apiResult="timeOut";
            return  apiResult;
        }
        return result;
    }

    public static void main(String[] args) {
        String url = "https://uuap.vipkid.com.cn/cas/login";
        String username = "juzhihai";
        String password = "Jiangmeiling123@";
        String login = "https://uuap.vipkid.com.cn/cas/login";

        Map<String, String> params = new HashMap<>();
        params.put("userName","juzhihai");
        params.put("password","Jiangmeiling123@");
        params.put("modile","login");
        params.put("mobile","");


        JSONObject root = new JSONObject();
        root.put("subject","会议");
        root.put("source","mr_web");
        root.put("deptIds",new JSONArray());
        root.put("demandIds",new JSONArray());
        root.put("remark","会议");
        String orderNo = UUID.randomUUID().toString().replaceAll("-","");
        root.put("orderNo",orderNo);
        root.put("attach",new JSONArray());
        JSONArray userArray = new JSONArray();
        JSONObject userObject = new JSONObject();
        userObject.put("recUserName","王磊");
        userObject.put("recUser","R0019826");
        userObject.put("recEmail","wanglei10@vipkid.com.cn");
        userObject.put("recDeptName","LP研发组");
        userArray.add(userObject);
        root.put("users",userArray);



        String pp = HttpUtil.doPostForm(login,params);
        String cc = doGet("https://api.vipkid-inc.com/eim-ad-meetingroom/api/am/mr/mroom/getTime?id=65&startTime=2020-11-26&endTime=2020-11-26&source=mr_web");
        //String hh =doPost("https://api.vipkid-inc.com/eim-ad-meetingroom/api/am/mr/subscribe/add",root.toJSONString());

        System.out.println(pp);
        System.out.println("============================================================================================================================================================");
        System.out.println(cc);
//        System.out.println("============================================================================================================================================================");
//        System.out.println(hh);

    }
}
