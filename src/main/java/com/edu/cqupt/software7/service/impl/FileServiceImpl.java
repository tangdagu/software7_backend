package com.edu.cqupt.software7.service.impl;

import com.edu.cqupt.software7.common.Result;
import com.edu.cqupt.software7.service.FileService;
import com.edu.cqupt.software7.service.TablesService;
import com.edu.cqupt.software7.view.UploadResult;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    private TablesService tablesService;
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;




    @Override
    @Transactional
     public Result fileUpload(MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        if (!file.getOriginalFilename().endsWith(".csv")) {
            throw new IllegalArgumentException("Only CSV files are supported.");
        }
        String fileName = file.getOriginalFilename();

        System.out.println(fileName.substring(0, fileName.lastIndexOf(".")));
        String tableName = fileName.substring(0, fileName.lastIndexOf("."));


        List<String> tableHeaders;
        List<String[]> rows = new ArrayList<>(); // 存储处理后的数据行

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] columnNames = csvReader.readNext();
            //去空格
            tableHeaders = new ArrayList<>();
            for (int i = 0; i < columnNames.length; i++) {
                String columnName = columnNames[i].trim();
                if (!columnName.isEmpty()) {
                    tableHeaders.add(columnName);
                }
            }
            String[] tableHeadersArray = tableHeaders.toArray(new String[0]);
            System.out.println(tableHeaders);
            // 读取数据行并删除空表头对应的整列数据
//            csvReader.readNext();
            String[] row;
            while ((row = csvReader.readNext()) != null) {

                String[] newRow = new String[tableHeaders.size()];
                int newIndex = 0;
                for (int i = 0; i < row.length; i++) {
                    if (!columnNames[i].trim().isEmpty()) {
                        newRow[newIndex++] = row[i];
                    }
                }
                rows.add(newRow);
            }


            try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
                createTable(connection, tableName, tableHeaders, rows);

                String insertQuery = generateInsertQuery(tableName, tableHeadersArray);
                System.out.println(insertQuery);


//                String[] data1;
                int batchCount = 0;

//                CSVReader csvReaderForData = new CSVReader(new InputStreamReader(file.getInputStream()));
//                csvReaderForData.readNext();
                for (String[] data : rows) {
                    batchCount++;
                    try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {

                        for (int i = 0; i < data.length; i++) {
                            // 去除列名中的空格
                            String columnName = tableHeaders.get(i).trim();
                            statement.setString(i + 1, data[i]);
                        }
//                        for (int i = 0; i < data.length; i++) {
//                            statement.setString(i + 1, data[i]);
//                        }
                        statement.addBatch();
                        if (batchCount % 1000 == 0) {
                            statement.executeBatch();
                        }
                        statement.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Failed to insert data into the database.", e);
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException("Failed to create table or establish connection to the database.", e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file.", e);
        }
//        tablesService.updateDataTable(tableName,disease);
//        List<dataTable> res=dataTableManagerService.upalldata();

//        return tableName;
        // 创建并返回UploadResult对象
        Result result = new Result(200,"文件上传成功"); // TODO 第三个参数是一个对象

        
//        result.setRes(res);
        return result;

    }

    //xiugai
    private void createTable(Connection connection, String tableName, List<String> tableHeaders, List<String[]> rows) throws SQLException {
        StringBuilder createTableQuery = new StringBuilder();
//        createTableQuery.append("CREATE TABLE ").append(tableName).append(" (");
        createTableQuery.append("CREATE TABLE ");
        createTableQuery.append('"'); // 添加反引号（`）开始包围列名
        createTableQuery.append(tableName);
        createTableQuery.append('"'); // 添加反引号（`）结束包围列名
        createTableQuery.append(" (");
        for (int i = 0; i < tableHeaders.size(); i++) {


            String columnName = tableHeaders.get(i);
            String columnType = determineColumnType(columnName, rows,tableHeaders);


            // 使用反引号包裹列名
            createTableQuery.append('"').append(columnName).append('"').append(" ").append(columnType);

            if (i < tableHeaders.size() - 1) {
                createTableQuery.append(", ");
            }
        }

        createTableQuery.append(");");
        System.err.println(createTableQuery);
        try (PreparedStatement statement = connection.prepareStatement(createTableQuery.toString())) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create table.", e);
        }
    }

    private String determineColumnType(String columnName, List<String[]> rows,List<String> tableHeaders) {
        // 判断数据是否为整数
        boolean isInt = true;
        // 判断数据是否为浮点数,pg数据库double类型为DOUBLE PRECISION
        boolean isDOUBLE_PRECISION = true;
        // 判断数据是否为日期，这里假设日期格式为"yyyy-MM-dd"，你可以根据实际情况修改格式
        boolean isDate = true;

        for (String[] row : rows) {
            String data = row[tableHeaders.indexOf(columnName)];
            // 判断是否为整数
            try {
                Integer.parseInt(data);
            } catch (NumberFormatException e) {
                isInt = false;
            }

            // 判断是否为浮点数
            try {
                Double.parseDouble(data);
            } catch (NumberFormatException e) {
                isDOUBLE_PRECISION = false;
            }

            // 判断是否为日期
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                LocalDate.parse(data, dateFormatter);
            } catch (DateTimeParseException e) {
                isDate = false;
            }

            // 如果有一行不符合整数、浮点数、或日期的格式，则退出循环
            if (!isInt && !isDOUBLE_PRECISION && !isDate) {
                break;
            }
        }

        // 根据判断结果返回对应的列类型
        if (isInt) {
            return "VARCHAR(255)";
        } else if (isDOUBLE_PRECISION) {
            return "VARCHAR(255)";
        } else if (isDate) {
            return "VARCHAR(255)";
        } else {
            // 如果以上条件都不满足，则返回默认的VARCHAR类型，长度为255
            return "VARCHAR(255)";
        }
    }


    private String determineColumnType(String data) {
        // 判断数据是否为整数
        try {
            Integer.parseInt(data);
            return "INT"; // 如果是整数，返回INT类型
        } catch (NumberFormatException e) {
            // 不是整数，继续判断其他类型
        }

        // 判断数据是否为浮点数
        try {
            Double.parseDouble(data);
            return "DOUBLE PRECISION"; // 如果是浮点数，返回DOUBLE类型,pg数据库double类型为DOUBLE PRECISION
        } catch (NumberFormatException e) {
            // 不是浮点数，继续判断其他类型
        }

        // 判断数据是否为日期，这里假设日期格式为"yyyy-MM-dd"，你可以根据实际情况修改格式
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(data, dateFormatter);
            return "DATE"; // 如果是日期，返回DATE类型
        } catch (DateTimeParseException e) {
            // 不是日期，继续判断其他类型
        }

        // 如果以上条件都不满足，则返回默认的VARCHAR类型，长度为255
        return "VARCHAR(255)";
    }


    private String generateInsertQuery(String tableName, String[] columnNames) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append('"'); // 添加反引号（`）开始包围列名
        sb.append(tableName);
        sb.append('"'); // 添加反引号（`）结束包围列名
        sb.append(" (");

        for (int i = 0; i < columnNames.length; i++) {
            sb.append('"'); // 添加反引号（`）开始包围列名
            sb.append(columnNames[i]);
            sb.append('"'); // 添加反引号（`）结束包围列名

            if (i < columnNames.length - 1) {
                sb.append(", ");
            }
        }

        sb.append(") VALUES (");

        for (int i = 0; i < columnNames.length; i++) {
            sb.append("?");

            if (i < columnNames.length - 1) {
                sb.append(", ");
            }
        }

        sb.append(")");
        return sb.toString();
    }

}