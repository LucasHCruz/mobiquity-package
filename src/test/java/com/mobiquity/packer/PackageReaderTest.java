package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.Package;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PackageReaderTest {

    @Test
    @DisplayName("Read a sample file")
    void shouldDeserializePackages() throws APIException {
        //when
        String path = "src/test/resources/reduced_example_input";

        //given
        List<Package> packages = PackageReader.find(path);
        //then
        assertAll("Assert content", ()->{
            Package pack1 = packages.get(0);
            assertEquals(80, pack1.getWeightLimit());
            assertEquals(2, pack1.getItems().size());
            Item item1 = pack1.getItems().get(0);
            assertEquals(1, item1.getIndex());
            assertEquals(53.38, item1.getWeight());
            assertEquals(45, item1.getCost());
            Item item2 = pack1.getItems().get(1);
            assertEquals(2, item2.getIndex());
            assertEquals(88.62, item2.getWeight());
            assertEquals(98, item2.getCost());
        }, ()->{
            Package pack2 = packages.get(1);
            assertEquals(5, pack2.getWeightLimit());
            assertEquals(1, pack2.getItems().size());
            Item item1 = pack2.getItems().get(0);
            assertEquals(1, item1.getIndex());
            assertEquals(15.3, item1.getWeight());
            assertEquals(34, item1.getCost());
        },()->{
            Package pack3 = packages.get(2);
            assertEquals(90, pack3.getWeightLimit());
            assertEquals(3, pack3.getItems().size());
            Item item1 = pack3.getItems().get(0);
            assertEquals(1, item1.getIndex());
            assertEquals(85.31, item1.getWeight());
            assertEquals(29, item1.getCost());
            Item item2 = pack3.getItems().get(1);
            assertEquals(2, item2.getIndex());
            assertEquals(14, item2.getWeight());
            assertEquals(74, item2.getCost());
            Item item3 = pack3.getItems().get(2);
            assertEquals(3, item3.getIndex());
            assertEquals(3.98, item3.getWeight());
            assertEquals(16, item3.getCost());
        });
    }
}