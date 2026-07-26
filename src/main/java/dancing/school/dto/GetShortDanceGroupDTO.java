package dancing.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetShortDanceGroupDTO {

    private Long id;

    private String danceGroupName;
}
