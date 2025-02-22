import java.util.*;

class Student {
    String firstName, lastName, birthDate, address, className;
    Map<String, Double> subjectScores; // Môn học và điểm số

    public Student(String firstName, String lastName, String birthDate, String address, String className) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.className = className;
        this.subjectScores = new HashMap<>();
    }

    public void addScore(String subject, double score) {
        subjectScores.put(subject, score);
    }

    public String getRank() {
        double avg = subjectScores.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);
        if (avg >= 8.5) return "A";
        else if (avg >= 7) return "B";
        else if (avg >= 5.5) return "C";
        else if (avg >= 4) return "D";
        else return "E";
    }

    public void displayInfo() {
        System.out.println(firstName + " " + lastName + " | DOB: " + birthDate + " | Address: " + address + " | Rank: " + getRank());
    }
}

class Classroom {
    String className;
    List<Student> students;

    public Classroom(String className) {
        this.className = className;
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void displayClassInfo() {
        System.out.println("\n--- Danh sách sinh viên lớp " + className + " ---");
        Map<String, Integer> rankCount = new HashMap<>();
        for (String rank : Arrays.asList("A", "B", "C", "D", "E")) {
            rankCount.put(rank, 0);
        }

        for (Student s : students) {
            s.displayInfo();
            rankCount.put(s.getRank(), rankCount.get(s.getRank()) + 1);
        }

        System.out.println("\nTổng kết theo rank:");
        for (Map.Entry<String, Integer> entry : rankCount.entrySet()) {
            System.out.println("Rank " + entry.getKey() + ": " + entry.getValue() + " sinh viên");
        }
    }
}

public class SchoolManagement {
    static Map<String, Classroom> classes = new HashMap<>();

    public static void addSampleStudents(Classroom classroom) {
        String[][] studentData = {
            {"Nguyen", "An", "2003-05-10", "Ha Noi"},
            {"Tran", "Binh", "2003-09-22", "HCM"},
            {"Le", "Chi", "2004-01-15", "Da Nang"},
            {"Pham", "Duc", "2003-07-30", "Hai Phong"},
            {"Hoang", "Anh", "2002-11-10", "Can Tho"},
            {"Vu", "Lam", "2003-12-25", "Hue"}
        };
        
        double[][] scores = {
            {9.0, 8.5}, {7.0, 6.5}, {5.5, 6.0}, {4.0, 5.0}, {3.5, 4.0}, {2.5, 3.0}
        };

        for (int i = 0; i < 6; i++) {
            Student s = new Student(studentData[i][0], studentData[i][1], studentData[i][2], studentData[i][3], classroom.className);
            s.addScore("Lập trình hướng đối tượng", scores[i][0]);
            s.addScore("Quản lý dự án", scores[i][1]);
            classroom.addStudent(s);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Tạo một số lớp học với 6 sinh viên mỗi lớp
        classes.put("CNTT1", new Classroom("CNTT1"));
        classes.put("CNTT2", new Classroom("CNTT2"));
        addSampleStudents(classes.get("CNTT1"));
        addSampleStudents(classes.get("CNTT2"));

        while (true) {
            System.out.print("\nNhập mã lớp cần xem danh sách (hoặc 'exit' để thoát): ");
            String classCode = scanner.nextLine();
            
            if (classCode.equalsIgnoreCase("exit")) {
                System.out.println("Thoát chương trình.");
                break;
            }

            if (!classes.containsKey(classCode)) {
                System.out.println("Lớp không tồn tại! Bạn có muốn tạo lớp mới không? (yes/no): ");
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("yes")) {
                    classes.put(classCode, new Classroom(classCode));
                    addSampleStudents(classes.get(classCode));
                    System.out.println("Đã tạo lớp " + classCode + " với 6 sinh viên.");
                } else {
                    continue;
                }
            }

            classes.get(classCode).displayClassInfo();
        }

        scanner.close();
    }
}
