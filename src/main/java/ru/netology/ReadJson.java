package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadJson {

    // метод для чтения файла в строку;
    public static String readString(String fileName) {
        // для накопления данных;
        StringBuilder stringBuilder = new StringBuilder();

        try     // открываем файл для чтения;
                (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            // считывааем файл по строчно добавляя StringBuilder;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    //    метод для преобразования строки в список объектов Employee;
    public static List<Employee> jsonToList(String read) {
//        создаем список для сотрулников;
        List<Employee> employees = new ArrayList<>();
        try {
//          cоздаем объект JSONParser для парсинга строки JSON
            JSONParser parser = new JSONParser();
//          парсим строку и приводим к JSONArray;
            JSONArray jsonArray = (JSONArray) parser.parse(read);

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            // перебираем эдементы массива JSON;
            for (Object object : jsonArray) {
                // приводим каждый объект к JSONObject;
                JSONObject jsonObject = (JSONObject) object;
                // преобразуем JSONObject в объект Employee с помощью Gson;
                Employee employee = gson.fromJson(jsonObject.toString(), Employee.class);
                // добавляем сотрудника в список
                employees.add(employee);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
