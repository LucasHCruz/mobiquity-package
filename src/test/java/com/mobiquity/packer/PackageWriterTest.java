package com.mobiquity.packer;

import br.com.six2six.fixturefactory.Fixture;
import com.mobiquity.BaseTestClass;
import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.Package;
import com.mobiquity.packer.templates.ItemTemplate;
import com.mobiquity.packer.templates.PackageTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PackageWriterTest extends BaseTestClass {
    @BeforeAll
    public static void config() {
        BaseTestClass.config();
    }

    @Test
    @DisplayName("Should print items")
    void shouldPrintItems(){
        //given
        //Package 1
        Package pack1 = Fixture.from(Package.class).gimme(PackageTemplate.VALID_EMPTY_WEIGHT_50);
        Item item1Pack1 =  Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_1_COST_1);
        item1Pack1.setIndex(1);
        Item item2Pack1 =  Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_1_COST_1);
        item2Pack1.setIndex(2);
        pack1.setItems(Arrays.asList(item1Pack1, item2Pack1));
        //Package 2
        Package pack2 = Fixture.from(Package.class).gimme(PackageTemplate.VALID_EMPTY_WEIGHT_50);
        Item item1Pack2 =  Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_1_COST_1);
        item1Pack2.setIndex(1);
        Item item2Pack2 =  Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_1_COST_1);
        item2Pack2.setIndex(2);
        Item item3Pack2 =  Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_1_COST_1);
        item3Pack2.setIndex(3);
        Item item4Pack2 =  Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_1_COST_1);
        item4Pack2.setIndex(4);
        pack2.setItems(Arrays.asList(item1Pack2, item2Pack2, item3Pack2, item4Pack2));
        //Package 3
        Package pack3 = Fixture.from(Package.class).gimme(PackageTemplate.VALID_EMPTY_WEIGHT_50);
        Item item1Pack3 =  Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_1_COST_1);
        item1Pack3.setIndex(1);
        pack3.setItems(Arrays.asList(item1Pack3));
        //Packages
        List<Package> packages = Arrays.asList(pack1, pack2, pack3);

        //when
        String packageOutput = PackageWriter.write(packages);

        //then
        String expected = "1,2\n" +
                "1,2,3,4\n" +
                "1";
        assertEquals(expected, packageOutput);
    }

    @Test
    @DisplayName("Should print empty items")
    void shouldPrintEmptyItems(){
        //given
        //Package 1
        Package pack1 = Fixture.from(Package.class).gimme(PackageTemplate.VALID_EMPTY_WEIGHT_50);
        Item item1Pack1 =  Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_1_COST_1);
        item1Pack1.setIndex(1);
        Item item2Pack1 =  Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_1_COST_1);
        item2Pack1.setIndex(2);
        pack1.setItems(Arrays.asList(item1Pack1, item2Pack1));
        //Package 2
        Package pack2 = Fixture.from(Package.class).gimme(PackageTemplate.VALID_EMPTY_WEIGHT_50);
        //Package 3
        Package pack3 = Fixture.from(Package.class).gimme(PackageTemplate.VALID_EMPTY_WEIGHT_50);
        Item item2Pack3 =  Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_1_COST_1);
        item2Pack3.setIndex(2);
        Item item3Pack3 =  Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_1_COST_1);
        item3Pack3.setIndex(3);
        Item item4Pack3 =  Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_1_COST_1);
        item4Pack3.setIndex(4);
        pack3.setItems(Arrays.asList(item2Pack3, item3Pack3, item4Pack3));
        //Package 4
        Package pack4 = Fixture.from(Package.class).gimme(PackageTemplate.VALID_EMPTY_WEIGHT_50);
        //Packages
        List<Package> packages = Arrays.asList(pack1, pack2, pack3, pack4);


        //when
        String packageOutput = PackageWriter.write(packages);

        //then
        String expected = "1,2\n" +
                "-\n" +
                "2,3,4\n" +
                "-";
        assertEquals(expected, packageOutput);
    }
}
