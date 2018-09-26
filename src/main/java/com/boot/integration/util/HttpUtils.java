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
     * response响应code码
     */
    public static final String RET_CODE = "retCode";

    /**
     * response响应数据
     */
    public static final String RET_DATA = "retData";

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
     * get请求提交
     *
     * @param uri          -请求的路径
     * @param headerParams -请求头参数map
     * @param urlParams    -请求参数map
     */
    public static String sendByGet(String uri, Map<String, String> headerParams, Map<String, String> urlParams)
        throws Exception
    {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 结果返回
        String result = null;

        // get请求
        HttpGet get = null;

        // 响应的值
        HttpResponse response = null;

        try
        {
            get = new HttpGet();

            // 请求头参数设置
            if (headerParams != null && !headerParams.isEmpty())
            {
                Set<String> keys = headerParams.keySet();
                // 设置请求头
                for (String key : keys)
                {
                    get.setHeader(key, headerParams.get(key));
                }
            }
            // 拼装get请求参数
            List<NameValuePair> params = null;

            // 设置请求参数
            if (urlParams != null && !urlParams.isEmpty())
            {
                params = new ArrayList<NameValuePair>();
                Set<String> keys = urlParams.keySet();
                for (String key : keys)
                {
                    params.add(new BasicNameValuePair(key, urlParams.get(key)));
                }
                // 如果有中文进行编码
                // String param = URLEncodedUtils.format(params,charset);
                String param = EntityUtils.toString(new UrlEncodedFormEntity(params, charset));

                // 组装请求参数
                get.setURI(new URI(uri + "?" + param));
            }
            else
            {
                get.setURI(new URI(uri));
            }
            // 执行请求
            response = httpClient.execute(get);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                // 结果返回
                result = EntityUtils.toString(response.getEntity());
            }

        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            // 关闭response
            closeHttpResponse(response);

            //关闭client
            closeableHttpClient(httpClient);
        }
        return result;
    }

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
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 请求
        HttpPut httpPut = null;
        // 返回结果
        String resultJson = null;
        // 响应体
        HttpResponse response = null;
        try
        {
            httpPut = new HttpPut(url);

            StringEntity reqEntity = new StringEntity(jsonStr, charset);

            reqEntity.setContentEncoding(charset);

            // 发送json数据需要设置contentTypes
            reqEntity.setContentType(contentType);
            if (headerParams != null && !headerParams.isEmpty())
            {
                Set<String> keys = headerParams.keySet();
                // 设置请求头
                for (String key : keys)
                {
                    httpPut.setHeader(key, headerParams.get(key));
                }
            }
            httpPut.setEntity(reqEntity);

            response = httpClient.execute(httpPut);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                resultJson = EntityUtils.toString(response.getEntity());// 返回json格式：
            }

        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            // 关闭response
            closeHttpResponse(response);

            //关闭client
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
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 请求
        HttpPost post = null;
        // 返回结果
        String resultJson = null;
        // 响应体
        HttpResponse response = null;
        try
        {
            post = new HttpPost(url);

            StringEntity reqEntity = new StringEntity(jsonStr, charset);

            reqEntity.setContentEncoding(charset);

            // 发送json数据需要设置contentTypes
            reqEntity.setContentType(contentType);
            if (headerParams != null && !headerParams.isEmpty())
            {
                Set<String> keys = headerParams.keySet();
                // 设置请求头
                for (String key : keys)
                {
                    post.setHeader(key, headerParams.get(key));
                }
            }
            post.setEntity(reqEntity);

            response = httpClient.execute(post);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                resultJson = EntityUtils.toString(response.getEntity());// 返回json格式：
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            // 关闭response
            closeHttpResponse(response);

            //关闭client
            closeableHttpClient(httpClient);
        }

        return resultJson;
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param headerParams
     * @param bodyParams
     */
    public static String sendByPost(String url, Map<String, String> headerParams, Map<String, String> bodyParams)
        throws Exception
    {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 响应数据字符串
        StringBuilder responseBuilder = new StringBuilder();

        // 读buffer流
        BufferedReader reader = null;

        // http 响应对象
        HttpResponse response = null;

        // http请求方式
        HttpPost httpPost = null;
        try
        {
            httpPost = new HttpPost(url);

            // 请求头参数设置
            if (headerParams != null && !headerParams.isEmpty())
            {
                Set<String> keys = headerParams.keySet();
                // 设置请求头
                for (String key : keys)
                {
                    httpPost.setHeader(key, headerParams.get(key));
                }
            }
            // 拼装get请求参数
            List<NameValuePair> params = null;

            // 设置请求参数
            if (bodyParams != null && !bodyParams.isEmpty())
            {
                params = new ArrayList<NameValuePair>();
                Set<String> keys = bodyParams.keySet();
                for (String key : keys)
                {
                    params.add(new BasicNameValuePair(key, bodyParams.get(key)));
                }
            }
            HttpEntity entity = new UrlEncodedFormEntity(params);

            httpPost.setEntity(entity);

            response = httpClient.execute(httpPost);
            reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String lines = "";
            while ((lines = reader.readLine()) != null)
            {
                responseBuilder.append(lines);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            // 关闭response
            closeHttpResponse(response);

            //关闭client
            closeableHttpClient(httpClient);
        }

        return responseBuilder.toString();
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
        CloseableHttpClient httpClient = getHttpClientIgnoreSSL();

        // 请求
        HttpPost post = null;
        // 返回结果
        String resultJson = null;
        // 响应体
        HttpResponse response = null;
        try
        {
            post = new HttpPost(url);

            StringEntity reqEntity = new StringEntity(jsonStr, charset);

            reqEntity.setContentEncoding(charset);

            // 发送json数据需要设置contentTypes
            reqEntity.setContentType(contentType);
            if (headerParams != null && !headerParams.isEmpty())
            {
                Set<String> keys = headerParams.keySet();
                // 设置请求头
                for (String key : keys)
                {
                    post.setHeader(key, headerParams.get(key));
                }
            }
            post.setEntity(reqEntity);

            response = httpClient.execute(post);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                resultJson = EntityUtils.toString(response.getEntity());// 返回json格式：
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            // 关闭response
            closeHttpResponse(response);

            // 关闭client
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
     * <pre>
     * <一句话功能简述>
     * 关闭bufferReader 流
     * <功能详细描述>
     * </pre>
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
     * <pre>
     * <一句话功能简述>
     * 关闭HttpResponse 流
     * <功能详细描述>
     * </pre>
     *
     * @param response
     * @see [类、类#方法、类#成员]
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
