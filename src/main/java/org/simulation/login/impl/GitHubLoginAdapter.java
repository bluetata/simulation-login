/**
 * Copyright (c) 2017-2019 Sekito Lv(bluetata) <sekito.lv@gmail.com>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the 'License'); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.simulation.login.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.simulation.login.AbstractLogin;
import org.simulation.util.Constants;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author bluetata / Sekito.Lv@gmail.com</br>
 * @version simulation-login version(1.0)</br>
 * @date 08/24/18 17:22
 * @since JDK 1.8</br>
 */
public class GitHubLoginAdapter extends AbstractLogin {

    private static Log logger = LogFactory.getLog(GitHubLoginAdapter.class);

    // Define default constructor
    public GitHubLoginAdapter(String userName, String password) {
        super(userName, password);
    }

    /**
     * 准备登录
     *
     * @throws Exception
     */
    @Override
    protected void readyLogin() throws Exception {
        HttpGet readyGet = null;
        try {
            String readyUrl = getReadyLoginUrl();
            readyGet = new HttpGet(readyUrl);
            HttpResponse response = getUserClient().execute(readyGet);
            HttpEntity entity = response.getEntity();
            String info = EntityUtils.toString(entity);
            Document doc = Jsoup.parseBodyFragment(info);
            List<Element> elesList = doc.select("form");  // 获取提交form表单，可以通过查看页面源码代码得知
            // 获取表单信息
            // lets make data map containing all the parameters and its values found in the form

            if (elesList != null && elesList.size() > 0) {

                for (Element e : elesList.get(0).getAllElements()) {
                    // 设置用户名
                    if (e.attr("name").equals("login")) {
                        e.attr("value", getUserName());
                    }
                    // 设置用户密码
                    if (e.attr("name").equals("password")) {
                        e.attr("value", getPassword());
                    }
                    // 排除空值表单属性
                    if (e.attr("name").length() > 0) {
                        readyParams.put(e.attr("name"), e.attr("value"));
                    }
                }
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (readyGet != null) {
                readyGet.releaseConnection();
            }
        }
    }

    /**
     * 执行登陆
     *
     * @return 登陆的结果
     */
    @Override
    protected int executeLogin() throws Exception {
        HttpPost loginPost = null;
        try {
            String loginUrl = getLoginUrl();
            logger.info("请求login-------->" + loginUrl);
            loginPost = new HttpPost(loginUrl);

            List<NameValuePair> loginNameValues = new ArrayList<>();
            loginNameValues.add(new BasicNameValuePair("loginname", getUserName()));
            loginNameValues.add(new BasicNameValuePair("nloginpwd", getPassword()));
//            loginNameValues.add(new BasicNameValuePair("loginpwd", getPassword()));
//            loginNameValues.add(new BasicNameValuePair("authcode", getAuthCode()));
            Iterator<String> iterator = readyParams.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next().toString();
                String value = readyParams.get(key).toString();
                loginNameValues.add(new BasicNameValuePair(key, value));
            }

            loginPost.setEntity(new UrlEncodedFormEntity(loginNameValues, Consts.UTF_8));

            HttpResponse response = getUserClient().execute(loginPost);
            String loginRespInfoStr = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
            logger.info(loginRespInfoStr);
            if (loginRespInfoStr.contains("success")) {
                return Constants.SUCCESS;
            }
            return Constants.FAIL;
        } catch (Exception e) {
            throw e;
        } finally {
            loginPost.releaseConnection();
        }
    }

    /**
     * 进行测试，是否模拟登陆成功 / Test if the simulation login is successful.
     *
     * @throws Exception
     */
    @Override
    protected void testLogin() throws Exception {

    }

    /**
     * 准备login之前的准备login的url，注意该方法为非正式login的url
     * 该方法会通常被使用在`readyLogin()`方法中，用于获取login前的准备数据
     *
     * @return
     */
    @Override
    protected String getReadyLoginUrl() {
        // XXX: it's better to dynamically get the url. 08/28/2018 17:50 bluetata
        return "https://github.com/login";
    }

    /**
     * 获取验证码 / get Auth Code
     * 对于GitHub暂时没有使用
     *
     * @return 验证码的url / String url of auto code
     */
    @Override
    protected String getAuthCodeImageUrl() {
        return null;
    }

    /**
     * 正式login用的url，该方法通常用于`executeLogin()`方法中。
     * 用于模拟登陆
     *
     * @return
     */
    @Override
    protected String getLoginUrl() {
        return "https://github.com/session";
    }
}
