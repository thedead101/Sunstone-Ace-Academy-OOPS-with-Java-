import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;

class Student {
    private String studentId;
    private String name;
    private int age;
    private List<Course> courses;
    private Map<Course, String> grades; // Course and Grade map

    public Student(String studentId, String name, int age) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.courses = new ArrayList<>();
        this.grades = new HashMap<>();
    }

    // Getters and Setters

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Map<Course, String> getGrades() {
        return grades;
    }

    // Methods
    public void enrollCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            course.addStudent(this);
        }
    }

    public void assignGrade(Course course, String grade) {
        if (courses.contains(course)) {
            grades.put(course, grade);
        }
    }
}


class Course {
    private String courseId;
    private String courseName;
    private Teacher teacher;
    private List<Student> students;

    public Course(String courseId, String courseName, Teacher teacher) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacher = teacher;
        this.students = new ArrayList<>();
    }

    // Getters and Setters

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    // Methods
    public void addStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
        }
    }
}


class Teacher {
    private String teacherId;
    private String name;
    private List<Course> courses;

    public Teacher(String teacherId, String name) {
        this.teacherId = teacherId;
        this.name = name;
        this.courses = new ArrayList<>();
    }

    // Getters and Setters

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    // Methods
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }
}


class StudentManagementSystem {

    private List<Student> students;
    private List<Course> courses;
    private List<Teacher> teachers;

    public StudentManagementSystem() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
        teachers = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void deleteStudent(String studentId) {
        students.removeIf(student -> student.getStudentId().equals(studentId));
    }

    public void updateStudent(String studentId, String name, int age) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                student.setName(name);
                student.setAge(age);
                break;
            }
        }
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void enrollStudentInCourse(String studentId, String courseId) {
        Student student = findStudentById(studentId);
        Course course = findCourseById(courseId);
        if (student != null && course != null) {
            student.enrollCourse(course);
        }
    }

    public void assignGrade(String studentId, String courseId, String grade) {
        Student student = findStudentById(studentId);
        Course course = findCourseById(courseId);
        if (student != null && course != null) {
            student.assignGrade(course, grade);
        }
    }

    private Student findStudentById(String studentId) {
        return students.stream()
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);
    }

    private Course findCourseById(String courseId) {
        return courses.stream()
                .filter(course -> course.getCourseId().equals(courseId))
                .findFirst()
                .orElse(null);
    }

    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        Scanner scanner = new Scanner(System.in);

        // Sample data
        Teacher teacher1 = new Teacher("T001", "Mrs. Neha Gupta");
        sms.addTeacher(teacher1);

        Course course1 = new Course("C001", "DSA", teacher1);
        sms.addCourse(course1);

        Student student1 = new Student("S001", "Harsh", 20);
        sms.addStudent(student1);

        // Example operations
        sms.enrollStudentInCourse("S001", "C001");
        sms.assignGrade("S001", "C001", "A");

        // Display student details
        Student foundStudent = sms.findStudentById("S001");
        if (foundStudent != null) {
            System.out.println("Student: " + foundStudent.getName());
            System.out.println("Courses: " + foundStudent.getCourses().get(0).getCourseName());
            System.out.println("Grades: " + foundStudent.getGrades().get(course1));
        }
        
        scanner.close();
    }
}
