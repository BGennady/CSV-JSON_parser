package ru.netology;

import java.util.List;

import static ru.netology.ReadJson.readString;


public class Main {
    public static void main(String[] args) {

        //cоздаем маccив c именами колонок CSV (полями класса Employee)
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};

        // присваиваем переменной файл .csv
        String fileName = "data.csv";
        // получаем список сотрудников из CSV файла;
        List<Employee> list = parcel.parseCSV(columnMapping, fileName);
        // преобразуем список сотрудников в формат JSON;
        String json = parcel.listToJson(list);
        // записываем JSON в файл;
        parcel.writeString(json, "data.json");


        // присваиваем переменной файл .xml
        String fileName2 = "data.xml";
        // получаем список сотрудников из XML файла
        List<Employee> list2 = parcel.parseXML(fileName2);
        // преобразуем список сотрудников в формат JSON;
        String json2 = parcel.listToJson(list2);
        // записываем JSON в файл;
        parcel.writeString(json2, "data2.json");


        // присваиваем переменной файл .json
        String read = readString("data.json");
        // передаем объкт для чтения;
        List<Employee> list3 = ReadJson.jsonToList(read);
        // выводим в консоль.
        for (Employee employee : list3) {
            System.out.println(employee);
        }
    }
}