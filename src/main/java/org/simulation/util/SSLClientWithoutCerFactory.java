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

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @date     01/29/19 19:29</br>
 * @version  simulation-login version(1.0)</br>
 * @author   bluetata / Sekito.Lv@gmail.com</br>
 * @since    JDK 1.8</br>
 */
public class SSLClientWithoutCerFactory {
    /**
     * 绕过所有证书验证的client
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static CloseableHttpClient getTrustallClient()
            throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext ctx = SSLContext.getInstance("TLS");
        X509TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        ctx.init(null, new TrustManager[] { tm }, null);

// Fixing deprecated code to use current HttpClient implementations         Sekito.Lv 02/06/2019 17:40     Start
//        4.4 之前用法，已经过期的API
//        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(ctx,
//                new String[] { "TLSv1" }, null,
//                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(ctx,
                new String[] { "TLSv1" }, null,
                new NoopHostnameVerifier());
// Fixing deprecated code to use current HttpClient implementations         Sekito.Lv 02/06/2019 17:40     End

        CloseableHttpClient client = HttpClients.custom()
                .setSSLSocketFactory(factory).build();

        return client;
    }

    /**
     *
     * @return
     */
    public static CloseableHttpClient getTrustselfClient() {
        CloseableHttpClient client = null;

        return client;
    }
}
