package dancing.school.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teachers")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeacherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long id;

    @Column(name = "first_name",  nullable = false, length = 50)
    private String firstName;

    @Column(name =  "last_name", nullable = false, length = 50)
    private String lastName;

    @Column (length = 50)
    private String patronymic;

    @Column(nullable = false)
    private float experience;

    @Column(name = "about_me", nullable = false, length = 150)
    private String aboutMe;

    @Column(nullable = false)
    private int age;

    @Column(unique = true, length = 50, nullable = false)
    private String username;

    @Column(length = 50, nullable = false)
    private String password;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<DanceGroupEntity> groups = new ArrayList<>();
}
