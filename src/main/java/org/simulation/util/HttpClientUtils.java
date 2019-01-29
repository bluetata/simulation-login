/**
 * Copyright (c) 2017-2019 Sekito Lv(bluetata) <sekito.lv@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the 'License'); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.simulation.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @date     01/28/19 20:17</br>
 * @version  simulation-login version(1.0)</br>
 * @author   bluetata / Sekito.Lv@gmail.com</br>
 * @since    JDK 1.8</br>
 */
public class HttpClientUtils {

    private static Log logger = LogFactory.getLog(HttpClientUtils.class);

    /**
     * @Description
     * @param url
     * @param protocol
     * @param charset
     * @return
     */
    public static String sendRequest(String method, String url, String protocol,
                                     String requestData, String charset) {
        HttpClientBuilder httpClientBuilder = null;
        CloseableHttpClient closeableHttpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClientBuilder = HttpClientBuilder.create();
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(15 * 1000).setSocketTimeout(30 * 1000)
                    .build();
            httpClientBuilder.setDefaultRequestConfig(config);

            closeableHttpClient = httpClientBuilder.build();

            httpPost = new HttpPost(url);
            EntityBuilder entity = EntityBuilder.create();
            entity.setText(requestData);
            entity.setContentType(ContentType.APPLICATION_JSON);
            entity.setContentEncoding(charset);
            httpPost.setEntity(entity.build());

            HttpResponse response = closeableHttpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                } else {
                    logger.info("HttpEntity resEntity响应信息为空");
                }
            } else {
                logger.info("HttpResponse response 响应信息为空");
            }
        } catch (IOException ioe) {
            logger.error("Method sendRequest execute exception...");
            ioe.printStackTrace();
        } finally {
            try {
                // 关闭流并释放资源
                if (closeableHttpClient != null)
                    closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(
                        "Method sendRequest release resource exception...");
            }
        }
        return result;
    }

    /**
     * @Description 绕过证书发送https请求
     * @param url
     * @param protocol
     * @param charset
     * @return
     */
    public static String sendRequestTrustall(String method, String url,
                                             String protocol, Map<String, String> requestData, String charset) {
        CloseableHttpClient closeableHttpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {

            // closeableHttpClient = new SSLClientWithoutCerFactory();
            closeableHttpClient = SSLClientWithoutCerFactory
                    .getTrustallClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> data = MapToNameValuePair(requestData);
            if (data.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(data,
                        charset);
                httpPost.setEntity(entity);
            }

            HttpResponse response = closeableHttpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                } else {
                    logger.info("HttpEntity resEntity响应信息为空");
                }
            } else {
                logger.info("HttpResponse response 响应信息为空");
            }
        } catch (IOException ioe) {
            logger.error("Method sendRequest execute exception...");
            ioe.printStackTrace();
        } catch (Exception e) {
            logger.error("Method sendRequest execute exception...");
            e.printStackTrace();
        }

        finally {
            try {
                // 关闭流并释放资源
                if (closeableHttpClient != null)
                    closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(
                        "Method sendRequest release resource exception...");
            }
        }
        return result;
    }

    /**
     * @Description 绕过证书发送https请求
     * @param url
     * @param protocol
     * @param charset
     * @return
     */
    public static String sendRequestTrustself(String method, String url,
                                              String protocol, Map<String, String> requestData, String charset) {
        CloseableHttpClient closeableHttpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {

            closeableHttpClient = new SSLClientWithoutCerTrustself();
            httpPost = new HttpPost(url);
            List<NameValuePair> data = MapToNameValuePair(requestData);
            if (data.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(data,
                        charset);
                httpPost.setEntity(entity);
            }

            HttpResponse response = closeableHttpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                } else {
                    logger.info("HttpEntity resEntity响应信息为空");
                }
            } else {
                logger.info("HttpResponse response 响应信息为空");
            }
        } catch (IOException ioe) {
            logger.error("Method sendRequest execute exception...");
            ioe.printStackTrace();
        } catch (Exception e) {
            logger.error("Method sendRequest execute exception...");
            e.printStackTrace();
        }

        finally {
            try {
                // 关闭流并释放资源
                if (closeableHttpClient != null)
                    closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(
                        "Method sendRequest release resource exception...");
            }
        }
        return result;
    }

    /**
     * @Description 加载证书发送https请求
     * @param url
     * @param protocol
     * @param charset
     * @return
     */
    public static String sendRequestWithCer(String method, String url,
                                            String protocol, Map<String, String> requestData, String charset) {
        CloseableHttpClient closeableHttpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {

            closeableHttpClient = SSLClientWithCerFactory.getClient();

            httpPost = new HttpPost(url);
            List<NameValuePair> data = MapToNameValuePair(requestData);
            if (data.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(data,
                        charset);
                httpPost.setEntity(entity);
            }

            HttpResponse response = closeableHttpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                } else {
                    logger.info("HttpEntity resEntity响应信息为空");
                }
            } else {
                logger.info("HttpResponse response 响应信息为空");
            }
        } catch (IOException ioe) {
            logger.error("Method sendRequest execute exception...");
            ioe.printStackTrace();
        } catch (Exception e) {
            logger.error("Method sendRequest execute exception...");
            e.printStackTrace();
        }

        finally {
            try {
                // 关闭流并释放资源
                if (closeableHttpClient != null)
                    closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(
                        "Method sendRequest release resource exception...");
            }
        }
        return result;
    }

    private static List<NameValuePair> MapToNameValuePair(
            Map<String, String> param) {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        Iterator<String> keys = param.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = param.get(key);
            list.add(new BasicNameValuePair(key, value));
        }
        return list;
    }
}
