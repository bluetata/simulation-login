/**
 * Copyright 2017 [https://github.com/bluetata] All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the 'License'); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.simulation.login.impl;

import org.simulation.login.AbstractLogin;

/**
 * @date     08/24/18 17:22
 * @version  simulation-login version(1.0)</br>
 * @author   bluetata / Sekito.Lv@gmail.com</br>
 * @since    JDK 1.8</br>
 */
public class GitHubLogin implements AbstractLogin {

    /**
     * 准备登录
     *
     * @throws Exception
     */
    protected abstract void readyLogin() throws Exception;

    /**
     * 执行登陆
     *
     * @return 登陆的结果
     */
    protected abstract int executeLogin() throws Exception;

    /**
     * 登陆成功，进行测试
     *
     * @throws Exception
     */
    protected abstract void testLogin() throws Exception;
}
