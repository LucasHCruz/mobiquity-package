package com.mobiquity.packer.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PackagePossibility {
    private double totalWeight;
    private double totalCost;
    private List<Item> items;

    public static PackagePossibility init() {
        return PackagePossibility.builder()
                .items(new ArrayList<>())
                .build();
    }
}