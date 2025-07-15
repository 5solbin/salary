package solbin.project.salary.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import solbin.project.salary.config.auth.LoginUser;
import solbin.project.salary.dto.ResponseDto;
import solbin.project.salary.dto.worklog.add.AddReqDto;
import solbin.project.salary.dto.worklog.add.AddResDto;
import solbin.project.salary.dto.worklog.monthly.GetMonthlyReqDto;
import solbin.project.salary.dto.worklog.monthly.GetMonthlyResDto;
import solbin.project.salary.dto.worklog.update.UpdateReqDto;
import solbin.project.salary.dto.worklog.update.UpdateResDto;
import solbin.project.salary.service.WorklogService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WorklogController {

    private final WorklogService worklogService;

    @PostMapping("/s/worklog")
    @Operation(summary = "근무 내역 저장")
    public ResponseEntity<?> saveWorklog(@RequestBody @Valid AddReqDto addReqDto
            , @AuthenticationPrincipal LoginUser user) {
        AddResDto addResDto = worklogService.addWorklog(user.getUser().getId(), addReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "근무 기록 등록 성공", addResDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/s/worklog/{worklogId}")
    @Operation(summary = "근무 내역 삭제")
    public ResponseEntity<?> deleteWorklog(@PathVariable Long worklogId) {
        worklogService.deleteWorklog(worklogId);
        return new ResponseEntity<>("근무기록 삭제 성공", HttpStatus.OK);
    }

    @PutMapping("/s/worklog/{worklogId}")
    @Operation(summary = "근무 내역 수정")
    public ResponseEntity<?> updateWorklog(@PathVariable Long worklogId, @RequestBody @Valid UpdateReqDto updateReqDto) {
        UpdateResDto updateResDto = worklogService.updateWorklog(worklogId, updateReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "근무 기록 변경 성공", updateResDto), HttpStatus.OK);
    }

    @GetMapping("/s/worklog/getMonthlyInfo")
    @Operation(summary = "월별 근무 내역 반환")
    public ResponseEntity<?> getMonthlyInfo(@AuthenticationPrincipal LoginUser user, @ModelAttribute GetMonthlyReqDto getMonthlyReqDto) {
        GetMonthlyResDto getMonthlyResDto = worklogService.getMonthlyInfo(user.getUser().getId(), getMonthlyReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "근무 기록 조회 성공", getMonthlyResDto), HttpStatus.OK);
    }

}
