package solbin.project.salary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solbin.project.salary.domain.jobtype.JobType;
import solbin.project.salary.domain.user.User;
import solbin.project.salary.dto.jobtype.add.AddJobTypeReqDto;
import solbin.project.salary.dto.jobtype.add.AddJobTypeResDto;
import solbin.project.salary.dto.jobtype.update.UpdateJobTypeReqDto;
import solbin.project.salary.dto.jobtype.update.UpdateJobTypeResDto;
import solbin.project.salary.handler.ex.CustomApiException;
import solbin.project.salary.repository.JobTypeRepository;
import solbin.project.salary.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JobTypeService {

    private final JobTypeRepository jobTypeRepository;
    private final UserRepository userRepository;


    @Transactional
    public AddJobTypeResDto addJobType(Long userId, AddJobTypeReqDto reqDto) {

        // 유저가 존재하는지 확인하기
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("존재하지 않는 유저 입니다.")
        );

        // 유저가 담고 있는 일자리 리스트 출력하기
        List<JobType> jobTypes = jobTypeRepository.findByUser(user);

        for  (JobType jobType : jobTypes) {
            if (jobType.getName().equals(reqDto.getName())) {
                throw new CustomApiException("해당 이름이 이미 존재합니다.");
            }
        }

        // 엔티티로 변환하기
        JobType jobType = reqDto.toEntity(user);

        // 연관관계 설정 후 DB에 저장
        user.addJobType(jobType);
        jobTypeRepository.save(jobType);

        // 응답 DTO 반환
        return new  AddJobTypeResDto(jobType);
    }

    @Transactional
    public UpdateJobTypeResDto updateJobType(UpdateJobTypeReqDto reqDto) {

        JobType jobType = jobTypeRepository.findById(reqDto.getJobTypeId()).orElseThrow(
                () -> new CustomApiException("존재하지 않는 정보 입니다.")
        );

        jobType.update(reqDto);
        return new UpdateJobTypeResDto(jobType);
    }


}
