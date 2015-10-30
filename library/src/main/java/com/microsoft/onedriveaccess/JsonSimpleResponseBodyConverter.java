package com.microsoft.onedriveaccess;

import com.microsoft.onedriveaccess.model.FromJson;
import com.squareup.okhttp.ResponseBody;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

import retrofit.Converter;

public class JsonSimpleResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private static final String TYPE_NAME_PREFIX = "class ";

    private final JSONParser parser;
    private final Type type;

    JsonSimpleResponseBodyConverter(Type type, JSONParser parser) {
        this.parser = parser;
        this.type = type;
    }

    private static <T> T newInstance(Type type)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<T> clazz = (Class<T>) getClass(type);
        if (clazz == null) {
            return null;
        }
        return clazz.newInstance();
    }

    private static String getClassName(Type type) {
        if (type == null) {
            return "";
        }
        String className = type.toString();
        if (className.startsWith(TYPE_NAME_PREFIX)) {
            className = className.substring(TYPE_NAME_PREFIX.length());
        }
        return className;
    }

    private static Class<?> getClass(Type type)
            throws ClassNotFoundException {
        String className = getClassName(type);
        if (className == null || className.isEmpty()) {
            return null;
        }
        return Class.forName(className);
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        Reader reader = value.charStream();
        try {
            JSONObject json = (JSONObject) parser.parse(reader);
            T target = newInstance(type);
            if (target instanceof FromJson) {
                FromJson fromJson = (FromJson) target;
                fromJson.fromJson(json);
            } else {
                throw new AssertionError("Unhandled case.");
            }
            return target;
        } catch (ParseException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new IOException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException ignored) {
            }
        }
    }
}
