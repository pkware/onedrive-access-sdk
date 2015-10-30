package com.microsoft.onedriveaccess;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import org.json.simple.parser.JSONParser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

public class JsonSimpleConverterFactory extends Converter.Factory {

    private final JSONParser parser = new JSONParser();

    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return new JsonSimpleResponseBodyConverter<>(type, parser);
    }

    @Override
    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        return new JsonSimpleRequestBodyConverter<>();
    }
}
