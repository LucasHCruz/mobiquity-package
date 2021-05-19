package com.mobiquity.packer.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Package {
    private Double weightLimit;
    private List<Item> items;
}
