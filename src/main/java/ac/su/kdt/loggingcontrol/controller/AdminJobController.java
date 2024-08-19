package ac.su.kdt.loggingcontrol.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminJobController {
    // 관리자용 로그 기록 컨트롤러
    @PostMapping("/admin/products")
    public String addProduct(
        // RequestBody String productInfoDTO
        // DTO -> Jackson 라이브러리를 사용하여 JSON을 객체로 변환
    ) {
        // 관리자가 상품을 추가하는 로그를 기록
        return "상품 추가 로그 기록됨";
    }
}
