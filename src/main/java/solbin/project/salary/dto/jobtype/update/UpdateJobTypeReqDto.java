package solbin.project.salary.dto.jobtype.update;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateJobTypeReqDto {

    private Long jobTypeId;
    private String name;
    private Long payRate;

}
