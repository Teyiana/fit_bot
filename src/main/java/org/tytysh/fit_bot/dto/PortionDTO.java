package org.tytysh.fit_bot.dto;


import lombok.Data;

@Data
public class PortionDTO {
    private long id;
    private long dishId;
    private ProductDTO product;
    private long quantity;
}
