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

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * @date     01/29/19 19:33</br>
 * @version  simulation-login version(1.0)</br>
 * @author   bluetata / Sekito.Lv@gmail.com</br>
 * @since    JDK 1.8</br>
 */
public class SSLClientWithCerFactory {
    public static CloseableHttpClient getClient()
            throws KeyStoreException, NoSuchAlgorithmException,
            CertificateException, IOException, KeyManagementException {
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream in = SSLClientWithCerFactory.class
                .getResourceAsStream("/clientTrust.keystore");
        trustStore.load(in, "123456".toCharArray());

        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                .build();

        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(
                sslContext, new String[] { "TLSv1" }, null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        // SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(
        // sslContext, new String[] { "TLSv1" }, null,
        // SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient closeableHttpClient = HttpClients.custom()
                .setSSLSocketFactory(factory).build();
        return closeableHttpClient;
    }
}