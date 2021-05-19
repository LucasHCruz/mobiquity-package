package com.mobiquity.packer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Integer index;
    private Double weight;
    private Double cost;

    public boolean weightIsLessThan(Double weight) {
        return this.weight.compareTo(weight) < 1;
    }

    public boolean costIsLessThan(Double cost) {
        return cost.compareTo(cost) < 1;
    }
}
