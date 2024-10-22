package val.database;

import lombok.Data;

@Data
public class Student {


    private Long id;
    private String name;
    private String nameGroup;


    public Student(Long id, String name, String nameGroup) {
      this.id = id;
      this.name = name;
      this.nameGroup = nameGroup;
    }

}
