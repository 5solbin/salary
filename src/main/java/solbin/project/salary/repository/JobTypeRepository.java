package solbin.project.salary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solbin.project.salary.domain.JobType;
import solbin.project.salary.domain.user.User;

import java.util.List;

public interface JobTypeRepository extends JpaRepository<JobType, Long> {

    List<JobType> findByUser(User user);
}
