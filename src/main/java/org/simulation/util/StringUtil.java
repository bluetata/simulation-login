/**
 * Copyright (c) 2017-2019 Sekito Lv(bluetata) <sekito.lv@gmail.com>
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

// import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringUtil
 *
 * @date     08/24/18 17:22
 * @version  simulation-login version(1.0)</br>
 * @author   bluetata / Sekito.Lv@gmail.com</br>
 * @since    JDK 1.8</br>
 */
public class StringUtil {
    public static boolean isEmpty(String str) {
        if (str == null || str.trim().equals(""))
            return true;
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

//    public static String getJsonStr(JSONObject jso, String key) {
//        Object o = jso.get(key);
//        if (o != null)
//            return o.toString();
//        return null;
//    }
//
//    public static String getJsonStr(String json, String key) {
//        try {
//            JSONObject jso = JSONObject.fromObject(json);
//            Object o = jso.get(key);
//            if (o != null)
//                return o.toString();
//            return null;
//        } catch (Exception e) {
//            return null;
//        }
//    }

    public static String regex(String str, String pat) {
        Pattern pattern = Pattern.compile(pat);
        Matcher m = pattern.matcher(str);
        if (m.find()) {
            return m.group();
        }
        return null;
    }

    public static List<Double> parseNumbers(String str) {
        Pattern pattern = Pattern.compile("\\d+\\.*\\d*");
        Matcher m = pattern.matcher(str);

        List<Double> res = new ArrayList<Double>();
        while (m.find()) {
            String tmp = m.group().trim();
            res.add(Double.parseDouble(tmp));
        }
        return res.isEmpty() ? null : res;
    }

    public static List<Double> parsePercentages(String str) {
        str = str.replaceAll("\\s", "").replaceAll(",", "").replaceAll("O", "0");
        List<Double> arr = new ArrayList<Double>();
        String pat = "(\\d+\\.*\\d*(?=-\\d*\\.*\\d*%|％))|(\\d+\\.*\\d*(?=%|％))";
        Pattern p = Pattern.compile(pat);
        Matcher m = p.matcher(str);
        while (m.find()) {
            String tmp = m.group().trim();
            // System.out.println(tmp);
            arr.add(Double.parseDouble(tmp));
        }

        return arr.isEmpty() ? null : arr;
    }

    public static double findMax(List<Double> nums) {
        double m = Double.MIN_VALUE;
        for (Double x : nums) {
            if (m < x)
                m = x;
        }
        return m;
    }

    public static double findMin(List<Double> nums) {
        double m = Double.MAX_VALUE;
        for (Double x : nums) {
            if (m > x)
                m = x;
        }
        return m;
    }

    public static String sqlSingleQuote(String str) {
        return new StringBuffer("`").append(str).append("`").toString();
    }

}