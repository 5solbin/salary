package solbin.project.salary.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import solbin.project.salary.domain.jobtype.JobType;
import solbin.project.salary.domain.user.User;
import solbin.project.salary.domain.worklog.Worklog;
import solbin.project.salary.dto.worklog.add.AddWorklogReqDto;
import solbin.project.salary.dto.worklog.add.AddWorklogResDto;
import solbin.project.salary.dto.worklog.monthly.GetMonthlyReqDto;
import solbin.project.salary.dummy.DummyObject;
import solbin.project.salary.handler.ex.CustomApiException;
import solbin.project.salary.repository.JobTypeRepository;
import solbin.project.salary.repository.UserRepository;
import solbin.project.salary.repository.WorklogRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// Repository가 jpaRepository를 상속하고 있기때문에 굳이 Repository에 의존하는 서비스 코드는 테스트 하지 않는다.
@ExtendWith(MockitoExtension.class)
class WorklogServiceTest extends DummyObject {

    @InjectMocks
    private WorklogService worklogService;
    @Mock
    private WorklogRepository worklogRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JobTypeRepository jobTypeRepository;

    @Test
    public void 근무기록_추가() throws Exception{
        //given
        AddWorklogReqDto addReqDto = new AddWorklogReqDto();
        addReqDto.setStartTime(LocalDateTime.of(2025, 7, 10, 9, 0, 0));
        addReqDto.setEndTime(LocalDateTime.of(2025, 7, 10, 18, 0, 0));
        addReqDto.setJobTypeId(1L);

        JobType jobType = newJobType("치킨", 10000L);

        User user = newUser("test@naver.com", "테스트");

        //stub1
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(jobTypeRepository.findById(any())).thenReturn(Optional.of(jobType));

        // stub2
        Worklog worklog = newWorklog(user,addReqDto.getStartTime(),addReqDto.getEndTime(),jobType);
        when(worklogRepository.save(any())).thenReturn(worklog);

        //when
        AddWorklogResDto addResDto = worklogService.addWorklog(1L, addReqDto);
        System.out.println("테스트 : " + addResDto.getStartTime());
        System.out.println("테스트 : " + addResDto.getEndTime());

        //then
        assertThat(addResDto.getStartTime()).isEqualTo(worklog.getStartTime());
        assertThat(addResDto.getEndTime()).isEqualTo(worklog.getEndTime());
    }

    @Test
    public void 출퇴근날짜가_다를_떄() throws Exception{
        //given
        AddWorklogReqDto addReqDto = new AddWorklogReqDto();
        addReqDto.setStartTime(LocalDateTime.of(2025, 7, 10, 9, 0, 0));
        addReqDto.setEndTime(LocalDateTime.of(2025, 7, 12, 18, 0, 0));
        addReqDto.setJobTypeId(1L);

        JobType jobType = newJobType("치킨", 10000L);

        User user = newUser("test@naver.com", "테스트");

        //stub1
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(jobTypeRepository.findById(any())).thenReturn(Optional.of(jobType));

        //when
        CustomApiException e = assertThrows(CustomApiException.class, () -> worklogService.addWorklog(1L, addReqDto));

        //then
        assertThat(e.getMessage()).isEqualTo("같은 날짜에만 근무 시간을 작성할 수 있습니다.");

    }

    @Test
    public void 퇴근시간이_더_빠를때() throws Exception{
        //given
        AddWorklogReqDto addReqDto = new AddWorklogReqDto();
        addReqDto.setStartTime(LocalDateTime.of(2025, 7, 10, 9, 0, 0));
        addReqDto.setEndTime(LocalDateTime.of(2025, 7, 10, 7, 0, 0));
        addReqDto.setJobTypeId(1L);

        JobType jobType = newJobType("치킨", 10000L);

        User user = newUser("test@naver.com", "테스트");

        //stub1
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(jobTypeRepository.findById(any())).thenReturn(Optional.of(jobType));

        //when
        CustomApiException e = assertThrows(CustomApiException.class, () -> worklogService.addWorklog(1L, addReqDto));

        //then
        assertThat(e.getMessage()).isEqualTo("출근 시간은 퇴근 시간보다 빨라야 합니다.");
    }




}