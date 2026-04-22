package org.example.eshop.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDto {
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String name;


    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;
}
