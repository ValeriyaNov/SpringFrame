package val.database;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import val.database.Student;

@Component
public class StudentRepository {
  private final List<Student> students;

  public StudentRepository() {
    this.students = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      this.students.add(new Student((long) i, "user" + i, i % 2 == 0 ? "group1" : "group2"));
    }
  }

  public List<Student> getStudents() {
    return List.copyOf(students);
  }

  public Student getStudentByID(long id) {
    return students.stream()
            .filter(student -> Objects.equals(student.getId(), id))
            .findFirst()
            .orElse(null);
  }

  public List<Student> getStudentByName(String name) {
    return students.stream()
            .filter(student -> student.getName().contains(name))
            .toList();
  }

  public List<Student> getStudentByGroup(String nameGroup) {
    return students.stream()
            .filter(student -> student.getNameGroup().contains(nameGroup))
            .toList();
  }

  public Student updateStudent(Long id, Student updateStudent) {
    return students.stream()
            .filter(student -> student.getId().equals(id))
            .peek(student -> {
              student.setName(updateStudent.getName());
              student.setNameGroup(updateStudent.getNameGroup());
            })
            .findFirst()
            .orElse(null);
  }

  public void deleteStudent(Long id) {
    students.removeIf(student -> student.getId().equals(id));
  }

  public Student addStudent(Student student) {
    if (students.stream().anyMatch(existstudent -> existstudent.getId().equals(student.getId()))) {
      return null;
    } else {
      students.add(student);
      return student;
    }
  }
}
