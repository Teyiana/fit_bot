package org.tytysh.fit_bot.dto;

import lombok.Data;

import java.util.List;

@Data
public class DishDTO {

    private long id;

    private long userId;

    private String name;

    private List<PortionDTO> portions;


}
