package solbin.project.salary.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import solbin.project.salary.config.auth.LoginUser;
import solbin.project.salary.dto.ResponseDto;
import solbin.project.salary.dto.jobtype.add.AddJobTypeReqDto;
import solbin.project.salary.dto.jobtype.add.AddJobTypeResDto;
import solbin.project.salary.dto.jobtype.update.UpdateJobTypeReqDto;
import solbin.project.salary.dto.jobtype.update.UpdateJobTypeResDto;
import solbin.project.salary.service.JobTypeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s/jobType")
public class JobTypeController {

    private final JobTypeService jobTypeService;

    @PostMapping("/add")
    @Operation(summary = "일자리 저장")
    public ResponseEntity<?> addJobType(@RequestBody AddJobTypeReqDto reqDto, @AuthenticationPrincipal LoginUser user) {
        AddJobTypeResDto addJobTypeResDto = jobTypeService.addJobType(user.getUser().getId(), reqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "일자리 등록 성공", addJobTypeResDto), HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "일자리 수정")
    public ResponseEntity<?> updateJobType(@RequestBody UpdateJobTypeReqDto reqDto, @AuthenticationPrincipal LoginUser user) {
        UpdateJobTypeResDto updateJobTypeResDto = jobTypeService.updateJobType(reqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "일자리 수정 성공", updateJobTypeResDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "일자리 삭제")
    public ResponseEntity<?> deleteJobType(@RequestBody Long jobTypeId, @AuthenticationPrincipal LoginUser user) {
        jobTypeService.deleteJobType(jobTypeId);
        return new ResponseEntity<>("일자리 삭제 성공", HttpStatus.OK);
    }

}
