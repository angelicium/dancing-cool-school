package dancing.school.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @ToString.Exclude
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherEntity teacher;
}
