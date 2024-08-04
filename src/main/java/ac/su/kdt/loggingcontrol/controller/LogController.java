package ac.su.kdt.loggingcontrol.controller;

import ac.su.kdt.loggingcontrol.domain.CartForm;
import ac.su.kdt.loggingcontrol.domain.OrderForm;
import ac.su.kdt.loggingcontrol.logger.CustomLogger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@RestController
public class LogController {
    Random random = new Random();

    @GetMapping("/products")
    public String showProducts(
        @RequestParam(name="user-id", required = false) String userId,
        HttpServletRequest request
    ) {
        // 5개의 상품 id 리스트가 보여졌다고 가정
        List<Integer> productIds = IntStream
            .range(0, 5)
            .mapToObj(i -> random.nextInt(100) + 1)
            .toList();

        for (int productId : productIds) {
            CustomLogger.logRequest(
                "l",  // list 의 약자
                "/products/",
                "GET",
                userId != null ? userId : "-",  // userId가 없으면 "-"로 대체
                "-",                            // GET 메서드에는 transactionId가 없으므로 "-"로 대체
                String.valueOf(productId),
                "-",
                "-",
                "-",
                request
            );
        }
        return "상품 리스트(5행) 조회 로그 기록됨";
    }

    @GetMapping("/products/{productId}")
    public String getProduct(
        @PathVariable String productId,
        @RequestParam(name="user-id", required = false) String userId,
        HttpServletRequest request
    ) {
        CustomLogger.logRequest(
            "v",  // view 의 약자
            "/products/" + productId,
            "GET",
            userId != null ? userId : "-",  // userId가 없으면 "-"로 대체
            "-",                            // GET 메서드에는 transactionId가 없으므로 "-"로 대체
            productId,
            "-",
            "-",
            "-",
            request  // clientIp, userAgent, referer 정보를 얻기 위해 HttpServletRequest 객체를 파라미터로 추가
        );
        return "상품 조회 로그 1행 기록됨";
    }

    @PostMapping("/cart")
    public String addToCart(
        @RequestParam(name = "tr-id") String transactionId,
        @RequestBody CartForm cartForm,
        HttpServletRequest request
    ) {
        CustomLogger.logRequest(
            "c",  // cart 의 약자
            "/cart",
            "POST",
            cartForm.getUserId().toString(),
            transactionId,
            cartForm.getProductId().toString(),
            cartForm.getId().toString(),
            "-",
            cartForm.getQuantity().toString(),
            request  // clientIp, userAgent, referer 정보를 얻기 위해 HttpServletRequest 객체를 파라미터로 추가
        );
        return "장바구니 추가 로그 1행 기록됨";
    }

    @PostMapping("/order")
    public String placeOrder(
        @RequestParam(name = "tr-id") String transactionId,
        @RequestBody OrderForm orderForm,
        HttpServletRequest request
    ) {
        CustomLogger.logRequest(
            "o",  // order 의 약자
            "/order",
            "POST",
            orderForm.getUserId().toString(),
            transactionId,
            orderForm.getProductId().toString(),
            orderForm.getCartId() != null ? orderForm.getCartId().toString() : "-",
            orderForm.getId().toString(),
            orderForm.getQuantity().toString(),
            request  // clientIp, userAgent, referer 정보를 얻기 위해 HttpServletRequest 객체를 파라미터로 추가
        );
        return "주문 로그 1행 기록됨";
    }
}
