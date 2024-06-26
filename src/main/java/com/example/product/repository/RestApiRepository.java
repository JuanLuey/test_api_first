package com.example.product.repository;

import com.example.product.entity.Login;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Repository
public class RestApiRepository {

    @Value("${api.url}")
    public String api_url;

    @Value("${api.username}")
    public String api_username;

    @Value("${api.password}")
    public String api_password;

    public String api_autorization;

    public String getKeyToken() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        String stringBody = String
                .format("{\r\n  \"username\": \"" + api_username + "\",\r\n  \"password\": \"" + api_password
                        + "\"\r\n}");
        RequestBody body = RequestBody.create(stringBody, mediaType);

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Request request = new Request.Builder()
                .url(api_url + "/api/v1/login/SCA")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        if (response.code() != 200) {
            return "NO";
        }

        Login login = objectMapper.readValue(response.body().byteStream(), Login.class);
        api_autorization = login.getAccess_token();
        String return_access = getAccess();
        return return_access;

    }

    public String getAccess() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        String stringBody = String
                .format("{\r\n  \"access_token\": \"" + api_autorization + "\",\r\n  \"option_code\": 2\r\n}");

        RequestBody body = RequestBody.create(stringBody, mediaType);

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Request request = new Request.Builder()
                .url(api_url + "/api/v1/login/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        if (response.code() != 200) {
            return "NO";
        }

        return "OK";

    }

}
