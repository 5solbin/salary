package solbin.project.salary.dto.jobtype.add;

import lombok.Getter;
import lombok.Setter;
import solbin.project.salary.domain.jobtype.JobType;

@Getter @Setter
public class AddJobTypeResDto {

    private Long id;
    private String name;
    private Long payRate;

    public AddJobTypeResDto(JobType jobType) {
        this.id = jobType.getId();
        this.name = jobType.getName();
        this.payRate = jobType.getPayRate();
    }
}
