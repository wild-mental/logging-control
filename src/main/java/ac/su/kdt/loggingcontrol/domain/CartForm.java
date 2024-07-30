package ac.su.kdt.loggingcontrol.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartForm {
    private Long id;
    private Long productId;
    private Long userId;
    private Integer quantity;
}
