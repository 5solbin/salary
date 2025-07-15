package solbin.project.salary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solbin.project.salary.domain.user.User;
import solbin.project.salary.domain.worklog.Worklog;
import solbin.project.salary.dto.worklog.add.AddReqDto;
import solbin.project.salary.dto.worklog.add.AddResDto;
import solbin.project.salary.dto.worklog.monthly.DailyInfo;
import solbin.project.salary.dto.worklog.monthly.GetMonthlyReqDto;
import solbin.project.salary.dto.worklog.monthly.GetMonthlyResDto;
import solbin.project.salary.dto.worklog.update.UpdateReqDto;
import solbin.project.salary.dto.worklog.update.UpdateResDto;
import solbin.project.salary.handler.ex.CustomApiException;
import solbin.project.salary.repository.UserRepository;
import solbin.project.salary.repository.WorklogRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class                                                                                                     WorklogService {

    private final WorklogRepository worklogRepository;
    private final UserRepository userRepository;

    @Transactional
    public AddResDto addWorklog(Long userId, AddReqDto dto) {
        // 유저 존재 확인
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("존재하지 않는 유저입니다.")
        );

        // 유효성 검사 - 시작 시간과 종료 시간의 날짜는 같아야 한다.
        if (dto.getStartTime().getDayOfMonth() != dto.getEndTime().getDayOfMonth()
        || dto.getStartTime().getMonth() != dto.getEndTime().getMonth()
        || dto.getStartTime().getYear() != dto.getEndTime().getYear()){
            throw new CustomApiException("같은 날짜에만 근무 시간을 작성할 수 있습니다.");
        }

        // 유효성 검사 - 시작 시간과 종료 시간 비교
        if (dto.getStartTime().isAfter(dto.getEndTime())) {
            throw new CustomApiException("시작 시간은 종료 시간보다 빨라야 합니다.");
        }

        // 유효성 검사 - 같은 날짜 내에 시간이 겹치면 안된다.
        List<Worklog> worklogs = worklogRepository.findByUserId(user.getId());
        List<Worklog> sameDate = new ArrayList<>();
        for (Worklog worklog : worklogs) {
            if (checkDate(worklog, dto)) {
                sameDate.add(worklog);
            }
        }

        for  (Worklog worklog : sameDate) {

            if(dto.getStartTime().isBefore(worklog.getEndTime()) && worklog.getStartTime().isBefore(dto.getEndTime()))
            {
                throw new CustomApiException("겹치는 근무 시간이 존재합니다.");
            }
        }

        // 근무 기록 생성 및 dto 반환
        Worklog workLog = dto.ToEntity(user);

        user.addWorkLog(workLog);
        worklogRepository.save(workLog);

        return new AddResDto(workLog);
    }

    @Transactional
    public void deleteWorklog(Long worklogId) {
        // 근무 기록 존재 확인
        Worklog workLog = worklogRepository.findById(worklogId).orElseThrow(
                () -> new CustomApiException("존재하지 않는 근무기록 입니다.")
        );

        worklogRepository.delete(workLog);
    }

    @Transactional
    public UpdateResDto updateWorklog(Long worklogId,UpdateReqDto dto) {
        Worklog worklog = worklogRepository.findById(worklogId).orElseThrow(
                () -> new CustomApiException("존재하지 않는 근무기록 입니다."));

        worklog.update(dto);
        return new UpdateResDto(worklog);
    }


    public GetMonthlyResDto getMonthlyInfo(Long userId, GetMonthlyReqDto reqDto) {

        // User 아래 있는 근무 내역 전부 담기
        List<Worklog> worklogs = worklogRepository.findByUserId(userId);
        // 일별로 dto를 담기 위한 해시 맵
        Map<String, List<DailyInfo>> dailyMap = new HashMap<>();
        // 총 일한 시간을 알기 위한 변수
        long totalWorkTime = 0L;

        // worklogs 리스트 내부에 있는 객체를 확인해서 입력 받은 달에 해당하는 객체인지 확인후 형식에 맞게 저장
        for (Worklog worklog : worklogs) {
            if (checkDate(worklog, reqDto)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
                String date = worklog.getStartTime().format(formatter);

                LocalDateTime startTime = worklog.getStartTime();
                LocalDateTime endTime = worklog.getEndTime();
                long workTime = Duration.between(startTime, endTime).toHours();
                totalWorkTime += workTime;

                dailyMap.computeIfAbsent(date, k -> new ArrayList<>())
                        .add(new DailyInfo(date, startTime, endTime, workTime));
            }
        }

        String date = reqDto.getYear() + "/" + reqDto.getMonth();

        return new GetMonthlyResDto(date, dailyMap, totalWorkTime);
    }

    // 객체의 날짜와 요청 받은 날짜가 일치하는지 확인하는 메서드
    public boolean checkDate(Worklog workLog, GetMonthlyReqDto reqDto) {
        String year =  reqDto.getYear();
        String month = reqDto.getMonth();

        return year.equals(String.valueOf(workLog.getStartTime().getYear()))
                && month.equals(String.valueOf(workLog.getStartTime().getMonthValue()));
    }

    public boolean checkDate(Worklog worklog, AddReqDto dto) {
        LocalDate dateDB = worklog.getStartTime().toLocalDate();
        LocalDate dateDTO = dto.getStartTime().toLocalDate();

        return dateDB.equals(dateDTO);
    }




}
