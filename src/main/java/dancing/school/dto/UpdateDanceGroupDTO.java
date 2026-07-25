package dancing.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDanceGroupDTO {

    private String danceGroupName;

    private String teacherFirstName;

    private String teacherLastName;

    private String teacherPatronymic;
}
