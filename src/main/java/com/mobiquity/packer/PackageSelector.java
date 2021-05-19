package com.mobiquity.packer;

import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.Package;
import com.mobiquity.packer.model.PackagePossibility;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PackageSelector {
    private static final Double COST_LIMIT = 100.00;

    public static List<Package> select(List<Package> packages) {
        removeHeavyItems(packages);
        removeExpensiveItems(packages);
        selectBestItems(packages);
        return packages;
    }

    private static void removeHeavyItems(List<Package> packages) {
        packages.parallelStream().forEach(pack -> {
            Double weightLimit = pack.getWeightLimit();
            List<Item> softItems = pack.getItems()
                    .parallelStream()
                    .filter(item -> item.weightIsLessThan(weightLimit))
                    .collect(Collectors.toList());
            pack.setItems(softItems);
        });
    }

    private static void removeExpensiveItems(List<Package> packages) {
        packages.parallelStream().forEach(pack -> {
            List<Item> softItems = pack.getItems()
                    .parallelStream()
                    .filter(item -> item.weightIsLessThan(COST_LIMIT))
                    .collect(Collectors.toList());
            pack.setItems(softItems);
        });
    }

    private static void selectBestItems(List<Package> packages) {
        packages.parallelStream().forEach(pack -> {
            List<PackagePossibility> possibilities = generatePossibilities(pack.getItems());
            List<PackagePossibility> allowedPossibilitiesWeight = filterPackMaxWeight(pack, possibilities);
            List<Item> bestItems = selectLightestAndExpensivePossibility(pack, allowedPossibilitiesWeight);
            pack.setItems(bestItems);
        });
    }

    private static List<PackagePossibility> filterPackMaxWeight(Package pack, List<PackagePossibility> possibilities) {
        return possibilities.parallelStream()
                .filter(packagePossibility -> packagePossibility.getTotalWeight() <= pack.getWeightLimit())
                .collect(Collectors.toList());
    }

    private static List<PackagePossibility> generatePossibilities(List<Item> items) {
        List<PackagePossibility> packagePossibilities = new ArrayList<>();
        int N = items.size();
        for (int target = 1; target <= N; target++) {
            int[] combination = new int[target];
            int combinationsIndex = 0; // index for combination array
            int elementsIndex = 0; // index for elements array

            while (combinationsIndex >= 0) {
                if (elementsIndex <= (N + (combinationsIndex - target))) {
                    combination[combinationsIndex] = elementsIndex;
                    if (combinationsIndex == target - 1) {
                        packagePossibilities.add(getPossibility(combination, items));
                        elementsIndex++;
                    } else {
                        elementsIndex = combination[combinationsIndex] + 1;
                        combinationsIndex++;
                    }
                } else {
                    combinationsIndex--;
                    if (combinationsIndex >= 0) {
                        elementsIndex = combination[combinationsIndex] + 1;
                    }
                }
            }
        }
        return packagePossibilities;
    }

    private static List<Item> selectLightestAndExpensivePossibility(Package pack, List<PackagePossibility> possibilities) {
        return possibilities.parallelStream()
                .sorted(Comparator.comparing(PackagePossibility::getTotalCost).reversed().thenComparing(PackagePossibility::getTotalWeight))
                .filter(packagePossibility -> packagePossibility.getTotalWeight() <= pack.getWeightLimit())
                .map(PackagePossibility::getItems)
                .findFirst()
                .orElse(new ArrayList<>());
    }

    private static PackagePossibility getPossibility(int[] combination, List<Item> elements) {
        PackagePossibility possibility = PackagePossibility.init();
        for (int i : combination) {
            Item item = elements.get(i);
            possibility.setTotalWeight(possibility.getTotalWeight() + item.getWeight());
            possibility.setTotalCost(possibility.getTotalCost() + item.getCost());
            possibility.getItems().add(item);
        }
        return possibility;
    }
}
