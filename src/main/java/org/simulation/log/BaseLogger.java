/**
 * Copyright (c) 2017-2018 Sekito Lv(bluetata) <sekito.lv@gmail.com>
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
package org.simulation.log;

import org.apache.logging.log4j.LogManager;

/**
 * Base日志处理类
 *
 * @date     12/18/2018 16:35
 * @version  simulation-login version(1.0)</br>
 * @author   bluetata / Sekito.Lv@gmail.com</br>
 * @since    JDK 1.8</br>
 */
public class BaseLogger {

    //服务状态日志
    public static org.apache.logging.log4j.Logger infoLogger = LogManager.getLogger("infoLogger");
    public static org.apache.logging.log4j.Logger warnLogger = LogManager.getLogger("warnLogger");
    public static org.apache.logging.log4j.Logger debugLogger = LogManager.getLogger("debugLogger");
    public static org.apache.logging.log4j.Logger errorLogger = LogManager.getLogger("errorLogger");

}

