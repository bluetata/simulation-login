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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


/**
 * json封装类
 *
 * @date     08/27/18 15:08</br>
 * @version  simulation-login version(1.0)</br>
 * @author   bluetata / Sekito.Lv@gmail.com</br>
 * @since    JDK 1.8</br>
 */
public final class Jackson2Util {

    private static Log logger = LogFactory.getLog(Jackson2Util.class);
    private static ObjectMapper objectMapper = getMapper();

    private static ObjectMapper getMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule m = new SimpleModule();
        m.addSerializer(long.class, new ToStringSerializer());
        m.addSerializer(Long.class, new ToStringSerializer());
        objectMapper.registerModule(m);
        return objectMapper;
    }

    public static <T> String toJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Cannot get json from the object {}" + object);
        }
        return "";
    }

    public static <T> T fromJson(Class<T> clazz, String json) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error("Cannot object {} from a json {}" + clazz + "," + json);
        }
        return null;
    }

    public static <T> T fromJsonFile(Class<T> clazz, File file) {
        try {
            return objectMapper.readValue(file, clazz);
        } catch (IOException e) {
            logger.error("Error in reading config file");
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> getListFromJson(Class<T[]> clazz, String json) {
        try {
            return objectMapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            logger.error("Cannot object {} from a json {}" + clazz + "," + json);
        }
        return Collections.emptyList();
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
