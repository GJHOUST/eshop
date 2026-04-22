package org.example.eshop.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.eshop.model.entities.CartItem;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewCartDto {

    private UUID id;
    private UUID userId;
    private List<CartItemDto> items = new ArrayList<>();
}
