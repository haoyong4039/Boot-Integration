package com.boot.integration.util;

import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * <pre>
 * <一句话功能简述>
 * 远程接口调用httpclient工具类
 * <功能详细描述>
 * </pre>
 *
 * @author haoyong
 */
public class HttpUtils
{
    /**
     * 请求编码utf-8
     */
    private static String charset = "UTF-8";

    /**
     * 请求数据格式
     */
    private static String contentType = "application/json";

    /**
     * 忽略证书的TrustManager
     */
    private static X509TrustManager tm = new X509TrustManager()
    {
        public void checkClientTrusted(X509Certificate[] xcs, String string)
            throws CertificateException
        {
        }

        public void checkServerTrusted(X509Certificate[] xcs, String string)
            throws CertificateException
        {
        }

        public X509Certificate[] getAcceptedIssuers()
        {
            return null;
        }
    };

    /**
     * 发送put请求
     *
     * @param url          请求url
     * @param headerParams 请求头参数
     * @param jsonStr      参数 封装在json里面
     */
    public static String sendByPut(String url, Map<String, String> headerParams, String jsonStr)
        throws Exception
    {
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 响应体
        HttpResponse httpResponse = null;
        // 返回结果
        String resultJson = null;

        try
        {
            HttpPut httpPut = new HttpPut(url);

            StringEntity reqEntity = new StringEntity(jsonStr, charset);
            reqEntity.setContentEncoding(charset);
            reqEntity.setContentType(contentType);
            if (headerParams != null && !headerParams.isEmpty())
            {
                Set<String> keys = headerParams.keySet();
                for (String key : keys)
                {
                    httpPut.setHeader(key, headerParams.get(key));
                }
            }

            httpPut.setEntity(reqEntity);

            httpResponse = httpClient.execute(httpPut);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                // 返回json格式
                resultJson = EntityUtils.toString(httpResponse.getEntity());
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            closeHttpResponse(httpResponse);

            closeableHttpClient(httpClient);
        }

        return resultJson;
    }

    /**
     * 发送post请求
     *
     * @param url          请求url
     * @param headerParams 请求头参数
     * @param jsonStr      参数 封装在json里面
     */
    public static String sendByPost(String url, Map<String, String> headerParams, String jsonStr)
        throws Exception
    {
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 响应体
        HttpResponse httpResponse = null;
        // 返回结果
        String resultJson = null;

        try
        {
            HttpPost httpPost = new HttpPost(url);

            StringEntity reqEntity = new StringEntity(jsonStr, charset);
            reqEntity.setContentEncoding(charset);
            reqEntity.setContentType(contentType);
            if (headerParams != null && !headerParams.isEmpty())
            {
                Set<String> keys = headerParams.keySet();
                for (String key : keys)
                {
                    httpPost.setHeader(key, headerParams.get(key));
                }
            }

            httpPost.setEntity(reqEntity);

            httpResponse = httpClient.execute(httpPost);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                // 返回json格式
                resultJson = EntityUtils.toString(httpResponse.getEntity());
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            closeHttpResponse(httpResponse);

            closeableHttpClient(httpClient);
        }

        return resultJson;
    }

    /**
     * 发送post请求（忽略证书）
     *
     * @param url          请求url
     * @param headerParams 请求头参数
     * @param jsonStr      参数 封装在json里面
     */
    public static String sendByPostIgnoreSSL(String url, Map<String, String> headerParams, String jsonStr)
        throws Exception
    {
        // 创建httpClient对象
        CloseableHttpClient httpClient = getHttpClientIgnoreSSL();
        // 响应体
        HttpResponse httpResponse = null;
        // 返回结果
        String resultJson = null;
        // 请求
        HttpPost httpPost = null;

        try
        {
            httpPost = new HttpPost(url);

            StringEntity reqEntity = new StringEntity(jsonStr, charset);
            reqEntity.setContentEncoding(charset);
            reqEntity.setContentType(contentType);
            if (headerParams != null && !headerParams.isEmpty())
            {
                Set<String> keys = headerParams.keySet();
                for (String key : keys)
                {
                    httpPost.setHeader(key, headerParams.get(key));
                }
            }

            httpPost.setEntity(reqEntity);

            httpResponse = httpClient.execute(httpPost);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                // 返回json格式
                resultJson = EntityUtils.toString(httpResponse.getEntity());
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            closeHttpResponse(httpResponse);

            closeableHttpClient(httpClient);
        }

        return resultJson;
    }

    /**
     * 获取忽略证书校验的 HttpClient
     */
    public static CloseableHttpClient getHttpClientIgnoreSSL()
        throws KeyManagementException, NoSuchAlgorithmException
    {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        SSLContext sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(null, new TrustManager[] {tm}, null);

        // 忽略域名验证，避免SSLPeerUnverifiedException异常
        SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(sslcontext, NoopHostnameVerifier.INSTANCE);
        httpClientBuilder.setSSLSocketFactory(ssf);

        return httpClientBuilder.build();
    }

    /**
     * 关闭bufferReader流
     *
     * @param closeableHttpClient
     */
    private static void closeableHttpClient(CloseableHttpClient closeableHttpClient)
    {
        if (null != closeableHttpClient)
        {
            try
            {
                closeableHttpClient.close();
            }
            catch (IOException e)
            {
            }
        }
    }

    /**
     * 关闭HttpResponse 流
     *
     * @param response
     */
    private static void closeHttpResponse(HttpResponse response)
    {
        if (null != response)
        {
            try
            {
                EntityUtils.consume(response.getEntity());
            }
            catch (IOException e)
            {
            }
        }
    }

}
