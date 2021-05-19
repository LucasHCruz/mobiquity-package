package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.Package;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PackageReader {
    public static final String WEIGHT_LIMIT_REGEX = "(\\d+(\\.?\\d+)?)";
    public static final String WEIGHT_LIMIT_SEPARATOR_REGEX = "(\\s:\\s)";
    public static final String SINGLE_ITEM_REGEX = "(\\(\\d+\\,\\d+(\\.\\d+)?\\,€\\d+(\\.?\\d+)?\\)[\\s\\n]?)";
    public static final String MULTIPLE_ITEMS_REGEX = SINGLE_ITEM_REGEX + "+";
    public static final String PACKAGE_REGEX = WEIGHT_LIMIT_REGEX + WEIGHT_LIMIT_SEPARATOR_REGEX + MULTIPLE_ITEMS_REGEX;

    public static List<Package> find(String path) throws APIException {
        String rawPackagesContent = getLinesContentForPath(path);
        return getPackagesForContent(rawPackagesContent);
    }

    private static String getLinesContentForPath(String path) throws APIException {
        String serializedPackage;
        File file = new File(path);
        try {
            serializedPackage = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new APIException("Error when reading the file: " + path, e);
        }
        return serializedPackage;
    }

    private static List<Package> getPackagesForContent(String rawPackagesContent) {
        List<String> packagesLinesFromRaw = getPackagesLinesFromRaw(rawPackagesContent);
        return deserializePackagesLines(packagesLinesFromRaw);
    }

    private static List<String> getPackagesLinesFromRaw(String packagesRawContent) {
        Pattern packagesPattern = Pattern.compile(PACKAGE_REGEX);
        Matcher packagesMatcher = packagesPattern.matcher(packagesRawContent);
        return packagesMatcher.results().map(MatchResult::group).collect(Collectors.toList());
    }

    private static List<Package> deserializePackagesLines(List<String> packagesLinesFromRaw) {
        return packagesLinesFromRaw.stream().map(serializedPackage -> {
            Double weightLimit = extractWeightLimit(serializedPackage);
            List<Item> items = extractItems(serializedPackage);
            return Package.builder().weightLimit(weightLimit).items(items).build();
        }).collect(Collectors.toList());
    }

    private static Double extractWeightLimit(String serializedPackage) {
        Pattern weightLimitPattern = Pattern.compile(WEIGHT_LIMIT_REGEX);
        Matcher weightLimitMatcher = weightLimitPattern.matcher(serializedPackage);
        return weightLimitMatcher.results().map(rawWeightLimitMatch -> Double.parseDouble(rawWeightLimitMatch.group())).findFirst().orElseThrow();
    }

    private static List<Item> extractItems(String serializedItemsLine) {
        Pattern singleItemPattern = Pattern.compile(SINGLE_ITEM_REGEX);
        Matcher itemMatcher = singleItemPattern.matcher(serializedItemsLine);
        List<String> rawItems = itemMatcher.results().map(MatchResult::group).collect(Collectors.toList());
        return extractItemsInfo(rawItems);
    }

    private static List<Item> extractItemsInfo(List<String> rawItems) {
        return rawItems.stream().map(PackageReader::extractItem).collect(Collectors.toList());
    }

    private static Item extractItem(String rawItem) {
        Pattern itemPattern = Pattern.compile(SINGLE_ITEM_REGEX);
        Matcher itemMatcher = itemPattern.matcher(rawItem);
        String rawItemInfo = itemMatcher.results().map(MatchResult::group).findFirst().orElseThrow();
        String[] rawItemInfoIndexed = rawItemInfo.replace("(", "").replace(")", "").split(",");
        Integer itemIndex = Integer.valueOf(rawItemInfoIndexed[0]);
        Double itemWeight = Double.valueOf(rawItemInfoIndexed[1]);
        Double itemCost = Double.valueOf(rawItemInfoIndexed[2].replace("€", ""));
        return Item.builder().index(itemIndex).weight(itemWeight).cost(itemCost).build();
    }

}
