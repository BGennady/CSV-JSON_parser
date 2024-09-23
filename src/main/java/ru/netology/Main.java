package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;


public class Main_CSV {
    public static void main(String[] args) {

        // создаем массив с именами колонок CSV (полями класса Employee)
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};

        // присваиваем переменной файл .csv
        String fileName = "data.csv";

        // присваиваем переменной файл .xml
        String fileName1 = "data.xml";

        // получаем список сотрудников из CSV файла
        List<Employee> list = parseCSV(columnMapping, fileName);
        // получаем список сотрудников из CSV файла
//        List<Employee> list = parseXML("data.xml");

        // преобразуем список сотрудников в формат JSON;
        String json = listToJson(list);

        // записываем JSON в файл;
        writeString(json, "data.json");

    }

    // метод для парсинга CSV файла в список объектов Employee;
    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        //создаем объект для хранения списка сотрудников;
        List<Employee> employees;

        try     // создаем объект для чтения данных  из fileName;
                (CSVReader reader = new CSVReader(new FileReader("data.csv"))) {
            // создаем объект стратегии для маппинга (присвоение "имени") колонок;
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            // указываем тип объекта который нам нужен;
            strategy.setType(Employee.class);
            // устанавливаем порядок колонокж;
            strategy.setColumnMapping(columnMapping);

            // создаем объект CsvToBean при помощи билдера;
            CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy) //стратегия
                    .withType(Employee.class)      //тип класса
                    .build();                      //запуск "строительства";

            // результат передаем в employees;
            employees = csvToBean.parse();
        } catch (IOException e) {
            e.printStackTrace();
            employees = null;
        }
        return employees;
    }

    // метод для преобразования списка сотрудников в строку JSON;
    public static String listToJson(List<Employee> employees) {
        // создаем объект GsonBuilder для настройки Gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        // на его базе создаем gson для сериализации параметров;
        Gson gson = gsonBuilder.create();

        // определяем тип списка Employee для Gson;
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();

        // преобразуем список сотрудников в строку JSON;
        String json = gson.toJson(employees, listType);
        return json;
    }

    // метод для записи строки JSON в файл;
    public static void writeString(String json, String fileName) {
        try
                (FileWriter fileWriter = new FileWriter(fileName)) {
            // записываем в файл;
            fileWriter.write(json);
            // очищаем буфер.
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}