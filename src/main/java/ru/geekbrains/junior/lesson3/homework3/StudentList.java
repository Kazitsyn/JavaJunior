package ru.geekbrains.junior.lesson3.homework3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;



import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentList {
    public static final String FILE_JSON = "students.json";
    public static final String FILE_BIN = "students.bin";
    public static final String FILE_XML = "students.xml";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final XmlMapper xmlMapper = new XmlMapper();

    public void addNewStudent(Scanner scanner, List<Student> students) {
        System.out.println("Введите имя студента");
        String newStudentName = scanner.nextLine();
        System.out.println("Введите возвраст студента");
        int newStudentAge = scanner.nextInt();
        System.out.println("Введите средний бал студента");
        double newStudentGPA = scanner.nextDouble();
        if (Student.create(newStudentName,newStudentAge,newStudentGPA) != null){
            students.add(Student.create(newStudentName,newStudentAge,newStudentGPA));
            saveStudentFile(FILE_JSON, students);
            saveStudentFile(FILE_BIN, students);
            saveStudentFile(FILE_XML, students);
            System.out.println("Студент добавлен.");
        }else {
            System.err.println("Студент не добавлен");
        }

    }

    public void saveStudentFile(String fileName, List<Student> students) {
        try {
            if (fileName.endsWith(".json")) {
                objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                objectMapper.writeValue(new File(fileName), students);
            } else if (fileName.endsWith(".bin")) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
                    oos.writeObject(students);
                }
            } else if (fileName.endsWith(".xml")) {
                xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                xmlMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                xmlMapper.writeValue(new File(fileName), students);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void displayStudents(List<Student> students) {
        students.forEach(System.out::println);
    }
    public static List<Student> loadStudentFromFile(String fileName) {
        List<Student> students = new ArrayList<>();

        File file = new File(fileName);
        if (file.exists()) {
            try {
                if (fileName.endsWith(".json")) {
                    students = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
                } else if (fileName.endsWith(".bin")) {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                        students = (List<Student>) ois.readObject();
                    }
                } else if (fileName.endsWith(".xml")) {
                    students = xmlMapper.readValue(file, xmlMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return students;
    }
}
