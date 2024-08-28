package com.edu.cqupt.software7.controller;

import com.edu.cqupt.software7.common.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/python")
public class PythonRequestController {

    @GetMapping("/get-chart")
    public R getPythonResult() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://localhost:5000/get-chart")
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
                return new R(response.code(), response.message(), response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
