package dancing.school.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity {

    @Id
    @GeneratedValue
    @Column(name = "student_id")
    private Long id;

    @Column(name = "first_name",  nullable = false, length = 50)
    private String firstName;

    @Column(name =  "last_name", nullable = false, length = 50)
    private String lastName;

    @Column (length = 50)
    private String patronymic;

    @Column(nullable = false)
    private int age;

    @Column(unique = true, length = 50, nullable = false)
    private String username;

    @Column(length = 50, nullable = false)
    private String password;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<MessageTemplateEntity> templates = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<RequestEntity> requestEntities = new ArrayList<>();
}
