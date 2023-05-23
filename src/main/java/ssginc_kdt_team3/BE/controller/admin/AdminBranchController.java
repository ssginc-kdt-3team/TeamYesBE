package ssginc_kdt_team3.BE.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssginc_kdt_team3.BE.DTOs.branch.BranchAddDTO;
import ssginc_kdt_team3.BE.DTOs.branch.BranchDetailDTO;
import ssginc_kdt_team3.BE.DTOs.branch.BranchUpdateDTO;
import ssginc_kdt_team3.BE.service.admin.AdminBranchService;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/branch")
public class AdminBranchController {

    private final AdminBranchService adminBranchService;

    @PostMapping("/add")
    public ResponseEntity createNewBranch(@Validated @RequestPart(value = "branchData") BranchAddDTO dto, BindingResult bindingResult,
                                          @RequestPart(value = "branchImg") MultipartFile multipartFile) {

        if (bindingResult.hasErrors()) {
            log.info("error");
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        Optional<Long> aLong = adminBranchService.addNewBranch(dto, multipartFile);

        if (aLong.isPresent()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<BranchDetailDTO> showBranchDetail(@PathVariable(name = "id") Long branchId) {

        Optional<BranchDetailDTO> oneBranch = adminBranchService.findOneBranch(branchId);

        if (oneBranch.isPresent()) {
            return ResponseEntity.ok(oneBranch.get());
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity deleteBranch(@PathVariable(name = "id") Long branchId) {

        boolean b = adminBranchService.deleteBranch(branchId);

        if (b) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/update/{id}")
    public ResponseEntity updateBranch(@PathVariable(name = "id") Long branchId, @RequestBody BranchUpdateDTO updateDTO) {
        boolean b = adminBranchService.updateBranch(branchId, updateDTO);

        if (b) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


//    @GetMapping("/{id}")
//    public ResponseEntity findOneBranch(@PathVariable(name = "id") Long branchId) {
//
//        Optional<Long> aLong = adminBranchService.addNewBranch(dto);
//
//        if (aLong.isPresent()) {
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
//    }
}