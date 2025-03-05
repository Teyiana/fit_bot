package org.tytysh.fit_bot.dto;

import lombok.Data;

import java.util.List;

@Data
public class DishDTO {

    private long id;

    private String name;

    private List<PortionDTO> portions;

}
