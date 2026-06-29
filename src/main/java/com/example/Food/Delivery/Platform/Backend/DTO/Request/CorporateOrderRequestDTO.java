package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Entities.CorporateOrder;
import com.example.Food.Delivery.Platform.Backend.Entities.CorporateOrderItem;
import com.example.Food.Delivery.Platform.Backend.Entities.OrderItem;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateOrderRequestDTO {
    @NotBlank
    private String companyName;
    private List<OrderItemRequestDTO> items;

    public static CorporateOrder toEntity(CorporateOrderRequestDTO dto) {

        CorporateOrder corporateOrder = new CorporateOrder();
        corporateOrder.setCompanyName(dto.getCompanyName());

        List<CorporateOrderItem> items = new ArrayList<>();

        if (dto.getItems() != null) {

            for (OrderItemRequestDTO itemDto : dto.getItems()) {

                CorporateOrderItem item = new CorporateOrderItem();

                item.setQuantity(itemDto.getQuantity());
                item.setCorporateOrder(corporateOrder);
                items.add(item);
            }
        }

        corporateOrder.setCorporateOrderItems(items);

        return corporateOrder;

    }
}
