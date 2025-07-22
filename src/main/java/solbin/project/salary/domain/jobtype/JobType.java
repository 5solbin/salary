package solbin.project.salary.domain.jobtype;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import solbin.project.salary.domain.user.User;
import solbin.project.salary.domain.worklog.Worklog;
import solbin.project.salary.dto.jobtype.update.UpdateJobTypeReqDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class JobType {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Worklog> worklogs = new ArrayList<>();

    private String name;

    private Long payRate;

    @Builder
    public JobType(Long id, String name, Long payRate, User user) {
        this.id = id;
        this.name = name;
        this.payRate = payRate;
        this.user = user;
    }

    public void assignUser(User user) {
        this.user = user;
    }

    public void addWorklog(Worklog worklog) {
        worklogs.add(worklog);
        worklog.assignJobType(this);
    }

    public void update(UpdateJobTypeReqDto reqDto) {
        this.name = reqDto.getName();
        this.payRate = reqDto.getPayRate();
    }
}
