package com.mobiquity.packer;

import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.Package;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class PackageWriter {
    public static String write(List<Package> packagesFounded) {
        log.info("Generating packages output");
        return printItems(packagesFounded);
    }

    private static String printItems(List<Package> packages) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < packages.size(); i++) {
            Package pack = packages.get(i);
            appendLine(pack, stringBuilder);
            if(isNotLastLine(packages, i)) {
                stringBuilder.append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }

    private static void appendLine(Package pack, StringBuilder stringBuilder) {
        assert Objects.nonNull(pack) : "Pack must not be null";
        List<Item> items = pack.getItems();
        if(Objects.nonNull(items) && !items.isEmpty()){
            stringBuilder.append(items.stream().map(item -> item.getIndex().toString()).collect(Collectors.joining(",")));

        } else {
            stringBuilder.append("-");
        }
    }

    private static boolean isNotLastLine(List<Package> packages, int i) {
        return !(i + 1 == packages.size());
    }
}
