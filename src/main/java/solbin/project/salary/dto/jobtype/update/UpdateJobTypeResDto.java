package solbin.project.salary.dto.jobtype.update;

import lombok.Getter;
import lombok.Setter;
import solbin.project.salary.domain.JobType;

@Getter @Setter
public class UpdateJobTypeResDto {

    private String name;
    private Long payRate;

    public UpdateJobTypeResDto(JobType jobType) {
        this.name = jobType.getName();
        this.payRate = jobType.getPayRate();
    }
}
