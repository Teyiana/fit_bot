package org.tytysh.fit_bot.dto;

import lombok.Data;

@Data
public class ProductDTO {

    private long id;

    private String name;

    private long typeId;

    private int gram;

    private double protein;

    private double fat;

    private double carbohydrates;

    private double calories;

}
