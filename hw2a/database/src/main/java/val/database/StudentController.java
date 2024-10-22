package val.database;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
  private final StudentRepository students;

  @GetMapping//("/all")
  public ResponseEntity<List<Student>> getStudent() {
    return ResponseEntity.ok(students.getStudents());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Student> getStudentByID(@PathVariable long id) {
    Student student = students.getStudentByID(id);
    return  students != null
            ? ResponseEntity.ok(student)
            : ResponseEntity.notFound().build();
  }

  @GetMapping(value = "/search", params = "name")
  public ResponseEntity<List<Student>> getStudentByName(@RequestParam("name") String name) {
    List<Student> studentByName = students.getStudentByName(name);
    return studentByName.isEmpty()
            ? ResponseEntity.notFound().build()
            : ResponseEntity.ok(studentByName);
  }

  @GetMapping(value = "/search", params = "group")
  public ResponseEntity<List<Student>> getStudentByNameGroup(@RequestParam("group") String nameGroup) {
    List<Student> studentByGroup = students.getStudentByGroup(nameGroup);
    return studentByGroup.isEmpty()
            ? ResponseEntity.notFound().build()
            : ResponseEntity.ok(studentByGroup);
  }

  @GetMapping("/group/{nameGroup}")
  public ResponseEntity<List<Student>> getStudentByGroupName(@PathVariable("nameGroup") String name) {
    List<Student> studentByGroup = students.getStudentByGroup(name);
    return studentByGroup.isEmpty()
            ? ResponseEntity.notFound().build()
            : ResponseEntity.ok(studentByGroup);
  }

  @PostMapping
  public ResponseEntity<Student> createStudent( @RequestBody Student student) {
    Student newStudent = students.addStudent(student);
    return  newStudent != null
            ? ResponseEntity.ok(student)
            : ResponseEntity.badRequest().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Student> updateStudent (@PathVariable long id, @RequestBody Student student) {
    Student updateStudent = students.updateStudent(id, student);
    return updateStudent != null
            ? ResponseEntity.ok(updateStudent)
            : ResponseEntity.badRequest().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
    students.deleteStudent(id);
    return ResponseEntity.noContent().build();
  }


}