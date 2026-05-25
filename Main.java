import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String fileName = "students.bin";

        // Создаём массив студентов
        Student[] students = {
                new Student("Амонов Алишер", 19, 4.8),
                new Student("Стародубцев Егор", 20, 4.2),
                new Student("Якимова Ольга", 18, 4.9),
                new Student("Коничева София", 21, 3.8),
                new Student("Артамонов Лев", 19, 4.5),
                new Student("Егор Мальков", 20, 4.0),
                new Student("Максимова Дарья", 18, 4.7),
                new Student("Костылёв Павел", 22, 3.9),
                new Student("Шутов Илья", 19, 4.6),
        };

        System.out.println("=== ИСХОДНЫЕ ДАННЫЕ ===");
        printStudents(students);
        writeStudentsToBinaryFile(fileName, students);
        Student[] readStudents = readStudentsFromBinaryFile(fileName);

        System.out.println("\n=== ПРОЧИТАННЫЕ ДАННЫЕ ИЗ ФАЙЛА ===");
        printStudents(readStudents);
        System.out.println("\n=== ПРОВЕРКА ===");
        if (Arrays.equals(students, readStudents)) {
            System.out.println("Данные успешно записаны и прочитаны!");
        } else {
            System.out.println("Ошибка: данные не совпадают!");
        }
    }
    public static boolean writeStudentsToBinaryFile(String filename, Student[] students) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            dos.writeInt(students.length);
            for (Student s : students) {
                dos.writeUTF(s.name);
                dos.writeInt(s.age);
                dos.writeDouble(s.grade);
            }
            System.out.println("\n✓ Запись в файл '" + filename + "' выполнена успешно!");
            return true;
        } catch (IOException e) {
            System.out.println("✗ Ошибка при записи в файл: " + e.getMessage());
            return false;
        }
    }
    public static Student[] readStudentsFromBinaryFile(String filename) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            int count = dis.readInt();
            Student[] students = new Student[count];
            for (int i = 0; i < count; i++) {
                String name = dis.readUTF();
                int age = dis.readInt();
                double grade = dis.readDouble();
                students[i] = new Student(name, age, grade);
            }

            System.out.println("✓ Чтение из файла '" + filename + "' выполнено успешно!");
            return students;

        } catch (IOException e) {
            System.out.println("✗ Ошибка при чтении из файла: " + e.getMessage());
            return null;
        }
    }
    public static void printStudents(Student[] students) {
        if (students == null) {
            System.out.println("Список студентов пуст!");
            return;
        }

        System.out.printf("%-3s %-20s %-6s %-6s\n", "№", "Имя", "Возраст", "Оценка");
        System.out.println("----------------------------------------");
        //вывод каждого студента
        for (int i = 0; i < students.length; i++) {
            System.out.printf("%-3d %-20s %-6d %-6.2f\n",
                    i + 1, students[i].name, students[i].age, students[i].grade);
        }
    }
}

class Student {
    String name;
    int age;
    double grade;
    public Student(String name, int age, double grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return age == student.age &&
                Double.compare(student.grade, grade) == 0 &&
                name.equals(student.name);
    }
}