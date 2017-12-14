package com.adatafun.airportshop.order.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class HttpClientUtils {
    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    private HttpClientUtils() {

    }


    public static JSONObject getJSONObject(String url) {
        try {
            String str = get(url);
            JSONObject json = JSON.parseObject(str);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return null;

    }

    /**
     * @return
     */
    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                /**
                 * @param chain
                 * @param authType
                 * @return
                 * @throws CertificateException
                 */
                //信任所有
                public boolean isTrusted(X509Certificate[] chain,
                                         String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    /**
     * 生成Httpclient的键值对
     *
     * @param nameValues
     * @return
     */
    private static List<NameValuePair> generateNameValues(Map<String, String> nameValues) {

        if (nameValues.isEmpty()) {
            return null;
        }

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();

        Set<String> keySet = nameValues.keySet();

        for (String s : keySet) {
            formparams.add(new BasicNameValuePair(s, nameValues.get(s)));
        }
        return formparams;
    }


    /**
     * 获取响应内容
     *
     * @param response
     * @return
     * @throws IOException
     */
    private static String getResponseContent(CloseableHttpResponse response) throws IOException {
        String content = "";
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                content = EntityUtils.toString(entity, "UTF-8");
                logger.info("response status =====  " + response.getStatusLine() + "");

                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    logger.error("response content ===== " + content);
                }
            }
        } finally {
            response.close();
        }

        return content;
    }


    /**
     * @param url
     * @return
     */
    public static String get(String url) throws IOException {
        return get(url, -1);
    }

    /**
     * Get方式获取网络资源
     *
     * @param url
     * @param socketTimeout 单位：毫秒 -1 不设置超时
     * @return
     */
    public static String get(String url, int socketTimeout) throws IOException {
        CloseableHttpClient httpclient = createSSLClientDefault();

        String content = null;

        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);

            if (socketTimeout > -1) {
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(socketTimeout).build();//设置请求和传输超时时间
                httpget.setConfig(requestConfig);
            }

            logger.info("executing request " + httpget.getURI());

            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);

            content = getResponseContent(response);

        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return content;
    }

    /**
     * POST 方式
     *
     * @param url
     * @param nameValues
     * @return
     */
    public static String post(String url, Map<String, String> nameValues) throws IOException {
        return post(url, nameValues, -1);
    }

    /**
     * @param url
     * @param nameValues
     * @param socketTimeout
     * @return
     */
    public static String post(String url, Map<String, String> nameValues, int socketTimeout) throws IOException {

        // 创建参数队列
        List<NameValuePair> formparams = generateNameValues(nameValues);

        return post(url, formparams, socketTimeout);
    }


    /**
     * @param url
     * @param formparams
     * @return
     */
    public static String post(String url, List<NameValuePair> formparams) throws IOException {
        return post(url, formparams, -1);
    }

    /**
     * POST 方式
     *
     * @param url
     * @param formparams
     * @param socketTimeout 单位：毫秒 -1 不设置超时
     * @return
     */
    public static String post(String url, List<NameValuePair> formparams, int socketTimeout) throws IOException {

        String content = "";

        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = createSSLClientDefault();

        // 创建httppost
        HttpPost httppost = new HttpPost(url);

        if (socketTimeout > -1) {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(socketTimeout).build();//设置请求和传输超时时间
            httppost.setConfig(requestConfig);
        }

        // 创建参数队列
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            logger.info("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);

            content = getResponseContent(response);

            if (response.getStatusLine().getStatusCode() == 302) {
                Header locationHeader = response.getFirstHeader("Location");
                if (locationHeader != null) {
                    content = get(locationHeader.getValue());
                }
            }


        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    /**
     * @param url
     * @param body
     * @return
     * @throws IOException
     */
    public static String post(String url, String body) throws IOException {
        return post(url, body, "UTF-8", -1);
    }

    /**
     * @param url
     * @param body
     * @return
     * @throws IOException
     */
    public static String post(String url, String body, String charset) throws IOException {
        return post(url, body, charset, -1);
    }

    /**
     * @param url
     * @param body
     * @param socketTimeout
     * @return
     */
    public static String post(String url, String body, int socketTimeout) throws IOException {
        return post(url, body, "UTF-8", socketTimeout);
    }

    /**
     * @param url
     * @param body
     * @param socketTimeout
     * @return
     */
    public static String post(String url, String body, String charset, int socketTimeout) throws IOException {

        String content = "";

        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        try {
            httpClient = createSSLClientDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(socketTimeout).build();
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            StringEntity s = new StringEntity(body, charset);
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(s);

            CloseableHttpResponse response = httpClient.execute(httpPost);

            content = getResponseContent(response);

        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            try {
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }


    public static void download(String urlString, String filename, String savePath) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为5s
        con.setConnectTimeout(5 * 1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }
}
