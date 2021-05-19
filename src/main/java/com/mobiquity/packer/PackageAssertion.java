package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.Package;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

@Slf4j
public class PackageAssertion {
    public static void validate(List<Package> packages) throws APIException {
        log.info("Validating packages");
        validatePackageList(packages);
    }

    private static void validatePackageList(List<Package> packages) throws APIException {
        if(Objects.isNull(packages) || packages.isEmpty()){
            throw new APIException("Packages should not be null");
        } else {
            for(Package pack : packages) {
                validatePackage(pack);
            }
        }
    }

    private static void validatePackage(Package pack) throws APIException {
        if(Objects.isNull(pack)){
            throw new APIException("Package should not be null");
        } else {
            if(pack.getWeightLimit() <= 0){
                throw new APIException("Packages must have weight limit greater than 0");
            } else if (pack.getWeightLimit() > 100){
                throw new APIException("Packages must have weight limit equals or less than 100");
            }
            validateItems(pack.getItems());
        }
    }

    private static void validateItems(List<Item> items) throws APIException {
        if(Objects.isNull(items) || items.isEmpty()){
            throw new APIException("Items should not be null");
        } else {
            if(items.size() > 15){
                throw new APIException("Packages must have a maximum of 15 items");
            }
            for(Item item : items) {
                validateItem(item);
            }
        }
    }

    private static void validateItem(Item item) throws APIException {
        if(Objects.isNull(item)){
            throw new APIException("Item should not be null");
        } else {
            if(item.getCost() <= 0){
                throw new APIException("Item cost should be greater than 0");
            } else if (item.getCost() > 100){
                throw new APIException("The cost of an item must be a maximum of 100");
            }
            if(item.getWeight() <= 0){
                throw new APIException("Item weight should be greater than 0");
            } else if (item.getWeight() > 100){
                throw new APIException("The weight of an item must be a maximum of 100");
            }
        }
    }
}
