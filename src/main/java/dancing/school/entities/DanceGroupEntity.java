package dancing.school.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dance_groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanceGroupEntity {

    @Id
    @GeneratedValue
    @Column(name = "dance_group_id")
    private Long id;

    private String danceGroupName;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherEntity teacher;
}
