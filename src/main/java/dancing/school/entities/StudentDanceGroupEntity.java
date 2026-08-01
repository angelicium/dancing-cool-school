package dancing.school.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "students_dance_groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDanceGroupEntity {

    @Id
    @GeneratedValue
    @Column(name = "student_dance_group_id")
    private Long id;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "dance_group_id")
    private DanceGroupEntity danceGroupEntity;
}
