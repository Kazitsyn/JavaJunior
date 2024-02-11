package ru.geekbrains.junior.lesson3.homework3;

import ru.geekbrains.junior.lesson3.task2.ToDoListApp;
import ru.geekbrains.junior.lesson3.task2.ToDoV2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static ru.geekbrains.junior.lesson3.task2.ToDoListApp.*;

public class App {
    public static void main(String[] args) {
        StudentList studentList = new StudentList();
        List<Student> students;
        File f = new File(StudentList.FILE_JSON);
        if (f.exists() && !f.isDirectory()){
            students = StudentList.loadStudentFromFile(StudentList.FILE_JSON);
        }
        else{
            students = new ArrayList<>();
//            studentList.saveStudentFile(FILE_JSON, students);
//            studentList.saveStudentFile(FILE_BIN, students);
//            studentList.saveStudentFile(FILE_XML, students);
        }


        studentList.displayStudents(students);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Добавить студента");
//            System.out.println("2. Удалить студента");
            System.out.println("0. Выйти");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    studentList.addNewStudent(scanner, students);
                    break;
                case "0":
                    studentList.saveStudentFile(StudentList.FILE_JSON, students);
                    studentList.saveStudentFile(StudentList.FILE_BIN, students);
                    studentList.saveStudentFile(StudentList.FILE_XML, students);
                    System.out.println("Список студентов сохранен.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
                    break;
            }

            studentList.displayStudents(students);
        }

    }
}
