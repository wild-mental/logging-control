package ac.su.kdt.loggingcontrol.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderForm {
    private Long id;
    private Long productId;
    private Long cartId;
    private Long userId;
    private Integer quantity;
}
