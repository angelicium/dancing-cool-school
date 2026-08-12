package dancing.school.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "message_templates")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageTemplateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 100)
    private String text;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherEntity teacher;
}
