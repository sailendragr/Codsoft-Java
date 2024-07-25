import java.util.*;

class Course {
    String courseCode;
    String title;
    String description;
    int capacity;
    int enrolled;
    String schedule;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolled = 0;
        this.schedule = schedule;
    }

    public boolean isFull() {
        return enrolled >= capacity;
    }

    public void enrollStudent() {
        if (!isFull()) {
            enrolled++;
        }
    }

    public void dropStudent() {
        if (enrolled > 0) {
            enrolled--;
        }
    }

    @Override
    public String toString() {
        return courseCode + ": " + title + "\nDescription: " + description + "\nCapacity: " + capacity +
               "\nEnrolled: " + enrolled + "\nSchedule: " + schedule + "\nAvailable Slots: " + (capacity - enrolled);
    }
}

class Student {
    String studentID;
    String name;
    List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public void registerCourse(Course course) {
        if (!course.isFull()) {
            registeredCourses.add(course);
            course.enrollStudent();
        } else {
            System.out.println("Course is full. Cannot register.");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.dropStudent();
        } else {
            System.out.println("Course not found in registered courses.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student ID: ").append(studentID).append("\nName: ").append(name).append("\nRegistered Courses:\n");
        for (Course course : registeredCourses) {
            sb.append(course.courseCode).append(" - ").append(course.title).append("\n");
        }
        return sb.toString();
    }
}

public class CourseManagementSystem {
    private static Map<String, Course> courses = new HashMap<>();
    private static Map<String, Student> students = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Adding sample courses
        addCourse(new Course("CS101", "Introduction to Computer Science", "Basic concepts of computer science", 30, "MWF 10:00-11:00"));
        addCourse(new Course("MA101", "Calculus I", "Introduction to calculus", 40, "TTh 12:00-13:30"));

        // Adding sample students
        addStudent(new Student("S001", "Alice"));
        addStudent(new Student("S002", "Bob"));

        while (true) {
            System.out.println("Course Management System");
            System.out.println("1. List Courses");
            System.out.println("2. Register for Course");
            System.out.println("3. Drop Course");
            System.out.println("4. View Student Info");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    listCourses();
                    break;
                case 2:
                    System.out.print("Enter student ID: ");
                    String studentID = scanner.nextLine();
                    Student student = students.get(studentID);
                    if (student != null) {
                        System.out.print("Enter course code: ");
                        String courseCode = scanner.nextLine();
                        Course course = courses.get(courseCode);
                        if (course != null) {
                            student.registerCourse(course);
                        } else {
                            System.out.println("Course not found.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter student ID: ");
                    studentID = scanner.nextLine();
                    student = students.get(studentID);
                    if (student != null) {
                        System.out.print("Enter course code: ");
                        String courseCode = scanner.nextLine();
                        Course course = courses.get(courseCode);
                        if (course != null) {
                            student.dropCourse(course);
                        } else {
                            System.out.println("Course not found.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter student ID: ");
                    studentID = scanner.nextLine();
                    student = students.get(studentID);
                    if (student != null) {
                        System.out.println(student);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addCourse(Course course) {
        courses.put(course.courseCode, course);
    }

    private static void addStudent(Student student) {
        students.put(student.studentID, student);
    }

    private static void listCourses() {
        System.out.println("Available Courses:");
        for (Course course : courses.values()) {
            System.out.println(course);
            System.out.println();
        }
    }
}
