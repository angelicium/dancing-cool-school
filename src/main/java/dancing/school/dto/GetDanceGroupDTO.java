package dancing.school.dto;

import dancing.school.entities.TeacherEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDanceGroupDTO {

    private Long id;

    private String danceGroupName;

    private String teacherFirstName;

    private String teacherLastName;

    private String teacherPatronymic;
}
