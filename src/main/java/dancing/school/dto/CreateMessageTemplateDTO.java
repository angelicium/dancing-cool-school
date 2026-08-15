package dancing.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMessageTemplateDTO {

    private Long teacherId;

    private Long studentId;

    private String title;

    private String text;
}
