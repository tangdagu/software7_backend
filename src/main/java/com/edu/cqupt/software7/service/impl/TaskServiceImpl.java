package com.edu.cqupt.software7.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.edu.cqupt.software7.common.R;
import com.edu.cqupt.software7.entity.Task;
import com.edu.cqupt.software7.mapper.TaskMapper;
import com.edu.cqupt.software7.service.TaskService;
import com.edu.cqupt.software7.vo.ExportVo;
import okhttp3.*;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.util.ASMUtils.type;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> exportByTableNameAndFactors(ExportVo exportVo) {
        String[] fieldArray = exportVo.getFactor().split(",");
        // 将 "id" 插到第一个位置，将id一并导出
        String[] updatedArray = ArrayUtils.addFirst(fieldArray, "id");

        String fields = String.join(",", updatedArray);
        String sql = "SELECT " + fields + " FROM " + exportVo.getTableName();

        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public R run(Task task) {
        if(task.getAlgorithmName().equals("logistic")){
            // 1. 创建OkHttpClient实例
            OkHttpClient client = new OkHttpClient();

            // 2. 构造RequestBody对象，封装要发送的POST数据
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(task)); // 替换为实际的JSON数据

            // 3. 创建Request对象，指定URL、请求方法（POST）和请求体
            String url = "http://localhost:5000/runFactureConstruction"; // 替换为实际的后端URL
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json") // 如果后端需要，添加其他请求头
                    .build();

            // 4. 使用OkHttpClient执行请求，获取Response对象

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.err.println("Unexpected code " + response);
                    throw new IOException("Unexpected code " + response);
                }
                String data = response.body().string();
                System.out.println("Response Code: " + response.code());
                System.out.println("Response Message: " + response.message());
                System.out.println("Response Body: " + data);
                R r = JSON.parseObject(data, R.class);// 反序列化
                return  r;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if(task.getAlgorithmName().equals("logistic2")){
            // 1. 创建OkHttpClient实例
            OkHttpClient client = new OkHttpClient();

            // 2. 构造RequestBody对象，封装要发送的POST数据
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(task)); // 替换为实际的JSON数据

            // 3. 创建Request对象，指定URL、请求方法（POST）和请求体
            String url = "http://localhost:5000/runLogistic2"; // 替换为实际的后端URL
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json") // 如果后端需要，添加其他请求头
                    .build();

            // 4. 使用OkHttpClient执行请求，获取Response对象

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.err.println("Unexpected code " + response);
                    throw new IOException("Unexpected code " + response);
                }
                String data = response.body().string();
                System.out.println("Response Code: " + response.code());
                System.out.println("Response Message: " + response.message());
                System.out.println("Response Body: " + data);
                R r = JSON.parseObject(data, R.class);// 反序列化
                return  r;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if(task.getAlgorithmName().equals("cox")){
            // 1. 创建OkHttpClient实例
            OkHttpClient client = new OkHttpClient();

            // 2. 构造RequestBody对象，封装要发送的POST数据
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(task)); // 替换为实际的JSON数据

            // 3. 创建Request对象，指定URL、请求方法（POST）和请求体
            String url = "http://localhost:5000/runCox"; // 替换为实际的后端URL
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json") // 如果后端需要，添加其他请求头
                    .build();

            // 4. 使用OkHttpClient执行请求，获取Response对象

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.err.println("Unexpected code " + response);
                    throw new IOException("Unexpected code " + response);
                }
                String data = response.body().string().replace("\n", "");

                Gson gson = new Gson();
                // 定义类型为二维数组的List<List<Integer>>
                Type listType = new TypeToken<List<List<Float>>>(){}.getType();
                List<List<Integer>> dataList = gson.fromJson(data, listType);

                System.out.println("Response Code: " + response.code());
                System.out.println("Response Message: " + response.message());
                System.out.println("Response Body: " + dataList);
                return  new R<>(200,"success", dataList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            return new R(0, "error", null);
        }
    }



    @Override
    public List<Task> getByCreateUserName(String name) {
        QueryWrapper<Task> queryWrapper = new QueryWrapper<Task>().eq("task_creation_people", name);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public Boolean saveTaskFeatureByLowerCase(Task task) {
        task.setFactor(task.getFactor().toLowerCase());
        task.setTaskCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        return SqlHelper.retBool(taskMapper.insert(task));
    }
}
