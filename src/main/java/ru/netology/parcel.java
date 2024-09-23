package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class parcel {
    // метод для парсинга CSV файла в список объектов Employee;
    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        //создаем объект для хранения списка сотрудников;
        List<Employee> employees;

        try     // создаем объект для чтения данных  из fileName;
                (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            // создаем объект стратегии для маппинга (присвоение "имени") колонок;
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            // указываем тип объекта который нам нужен;
            strategy.setType(Employee.class);
            // устанавливаем порядок колонок;
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

    // метод для парсинга XML файла в список объектов Employee;
    public static List<Employee> parseXML(String fileName1) {
        //создаем пустой список для хранения данных сотрудников;
        List<Employee> employees = new ArrayList<>();

        try {
            //создаем фабрику для построения документов;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //создаем билдер для построения документов;
            DocumentBuilder builder = factory.newDocumentBuilder();
            // парсим XML-файл и получаем документ;
            Document doc = builder.parse(new File(fileName1));

            //нормализуем структуру документаж
            doc.getDocumentElement().normalize();

            // получаем корневой элемент (тзг) документа;
            Node root = doc.getDocumentElement();
            // получаем список дочерних (вложеных тэгов) узлов;
            NodeList nodeList = root.getChildNodes();
            // проходимся по всем элементам;
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                //проверяем узлы на наличие эдементов (тэгов);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    long id = Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent());
                    String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                    String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
                    String country = element.getElementsByTagName("country").item(0).getTextContent();
                    int age = Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent());

                    // создаем новый объект Employee, и заливаем туда все данные.
                    Employee employee = new Employee(id, firstName, lastName, country, age);
                    employees.add(employee);
                }
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // метод для преобразования списка сотрудников в строку JSON;
    public static String listToJson(List<Employee> employees) {
        // создаем объект GsonBuilder для настройки Gson (с отступами);
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();;
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