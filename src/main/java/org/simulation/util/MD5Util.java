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
package org.simulation.util;

import java.security.MessageDigest;

/**
 * 采用MD5加密解密
 *
 * @date     08/24/18 17:22
 * @version  simulation-login version(1.0)</br>
 * @author   bluetata / Sekito.Lv@gmail.com</br>
 * @since    JDK 1.8</br>
 */
public class MD5Util {

    private static MD5Util md5Util = null;

    public synchronized static MD5Util getInstance() {
        if (md5Util == null) {
            md5Util = new MD5Util();
        }
        return md5Util;
    }

    private MD5Util() {
    }


    /***
     * MD5加码 生成32位md5码
     * @param inStr
     */
    public static String md5Encode(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


    /**
     * MD5加密(标准)
     *
     * @param s
     * @return
     */
    public String standardMD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            //logger.error(e.getMessage(), e);
            return null;
        }
    }


    // 测试
    public static void main(String args[]) {

        MD5Util md5Util = MD5Util.getInstance();

        String password = "P@ssw0rd";
        String secretKey = "TEST";

        String strMD5 = md5Util.md5Encode(password + secretKey);
        String strMD5S = md5Util.standardMD5(password + secretKey);

        System.out.println(strMD5);
        System.out.println(strMD5S);
    }
}
