package ssginc_kdt_team3.BE.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.admin.AdminLoginDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.Token;
import ssginc_kdt_team3.BE.jwt.JwtAuthenticationFilter;
import ssginc_kdt_team3.BE.service.admin.AdminService;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminLoginController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<Map> loginCheck(@RequestBody AdminLoginDTO loginDTO, HttpServletResponse response)  {

        Token token = adminService.adminLogin(loginDTO);

        if (token != null) {
            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + token);

            Map<String, String> map = new HashMap();
                    map.put("accessToken", token.getAccessToken());
                    map.put("refreshToken", token.getRefreshToken());

            return new ResponseEntity<>(map, httpHeaders, HttpStatus.OK);
        }

        return null;

    }

    @PostMapping("/test")
    public String test(){

        return "<h1>test 통과</h1>";
    }
}

