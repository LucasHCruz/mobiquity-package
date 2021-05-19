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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;

class PackageSelectorTest extends BaseTestClass {

    @BeforeAll
    public static void config() {
        BaseTestClass.config();
    }

    /*@Test
    @DisplayName("Select through heavy items and return empty")
    void selectThroughHeavyItemsAndReturnEmpty(){
        //given
        //Package 1
        Package package1 = Fixture.from(Package.class).gimme(PackageTemplate.VALID_EMPTY_WEIGHT_50);
        Package package2 = Fixture.from(Package.class).gimme(PackageTemplate.VALID_EMPTY_WEIGHT_50);
        Item heavyItem1 = Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_99_COST_99);
        softItem1.setIndex(1);
        heavyItem1.setIndex(2);
        package1.setItems(Arrays.asList(softItem1, heavyItem1));
        //Package 2
        Package package2 = Fixture.from(Package.class).gimme(PackageTemplate.VALID_EMPTY_WEIGHT_50);
        Item softItem2 = Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_5_COST_5);
        Item heavyItem2 = Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_99_COST_99);
        softItem2.setIndex(2);
        heavyItem2.setIndex(1);
        package2.setItems(Arrays.asList(softItem2, heavyItem2));
        List<Package> packages = Arrays.asList(package1, package2);

        //when
        List<Package> selectedPackages = PackageSelector.select(packages);

        //then
        assertNotNull(selectedPackages, "Selected packages should not be null");
        Package selectedPackage1 = selectedPackages.get(0);
        Package selectedPackage2 = selectedPackages.get(1);
        assertAll("Assert packages items", ()->{
            assertNotNull(selectedPackage1.getItems());
            assertEquals(1, selectedPackage1.getItems().size());
            assertThat("Package 1 should have only the soft item", selectedPackage1.getItems(), hasItem(softItem1));
        },()->{
            assertNotNull(selectedPackage2.getItems());
            assertEquals(1, selectedPackage2.getItems().size());
            assertThat("Package 2 should have only the soft item", selectedPackage2.getItems(), hasItem(softItem2));
        });
    }*/

    @Test
    @DisplayName("Should eliminate heavy items")
    void shouldSelectSoftItem(){
        //given
        //Package 1
        Package package1 = Fixture.from(Package.class).gimme(PackageTemplate.VALID_EMPTY_WEIGHT_50);
        Item softItem1 = Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_1_COST_1);
        Item heavyItem1 = Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_99_COST_99);
        softItem1.setIndex(1);
        heavyItem1.setIndex(2);
        package1.setItems(Arrays.asList(softItem1, heavyItem1));
        //Package 2
        Package package2 = Fixture.from(Package.class).gimme(PackageTemplate.VALID_EMPTY_WEIGHT_50);
        Item softItem2 = Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_5_COST_5);
        Item heavyItem2 = Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_99_COST_99);
        softItem2.setIndex(2);
        heavyItem2.setIndex(1);
        package2.setItems(Arrays.asList(softItem2, heavyItem2));
        List<Package> packages = Arrays.asList(package1, package2);

        //when
        List<Package> selectedPackages = PackageSelector.select(packages);

        //then
        assertNotNull(selectedPackages, "Selected packages should not be null");
        Package selectedPackage1 = selectedPackages.get(0);
        Package selectedPackage2 = selectedPackages.get(1);
        assertAll("Assert packages items", ()->{
            assertNotNull(selectedPackage1.getItems());
            assertEquals(1, selectedPackage1.getItems().size());
            assertThat("Package 1 should have only the soft item", selectedPackage1.getItems(), hasItem(softItem1));
        },()->{
            assertNotNull(selectedPackage2.getItems());
            assertEquals(1, selectedPackage2.getItems().size());
            assertThat("Package 2 should have only the soft item", selectedPackage2.getItems(), hasItem(softItem2));
        });
    }

    @Test
    @DisplayName("Select the lightest and most expensive combination")
    void selectTheLightestAndMostExpensiveCombination(){
        //given
        //Package 1
        Package pack = Fixture.from(Package.class).gimme(PackageTemplate.VALID_EMPTY_WEIGHT_50);
        Item softItem1 = Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_1_COST_1);
        Item softItem2 = Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_5_COST_5);
        Item heavyItem1 = Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_99_COST_99);
        Item heavyItem2 = Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_99_COST_99);
        softItem1.setIndex(1);
        heavyItem1.setIndex(2);
        softItem2.setIndex(3);
        heavyItem2.setIndex(4);
        pack.setItems(Arrays.asList(softItem1, heavyItem1, softItem2, heavyItem2));
        List<Package> packages = Arrays.asList(pack);

        //when
        List<Package> selectedPackages = PackageSelector.select(packages);

        //then
        assertNotNull(selectedPackages, "Selected packages should not be null");
        Package selectedPackage1 = selectedPackages.get(0);
        assertAll("Assert packages items", ()->{
            assertNotNull(selectedPackage1.getItems());
            assertEquals(2, selectedPackage1.getItems().size());
            assertThat("Package 1 should have only the soft items", selectedPackage1.getItems(), hasItems(softItem1, softItem2));
        });
    }

    @Test
    @DisplayName("Combination should not pass weight limit of a package")
    void combinationShouldNotPassWeightLimitOfAPackage(){
        //given
        //Package
        Package packageWithLowWeightLimit = Fixture.from(Package.class).gimme(PackageTemplate.VALID_EMPTY_WEIGHT_20);
        Item softItem1 = Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_5_COST_5);
        Item softItem2 = Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_5_COST_5);
        Item softItem3 = Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_15_COST_15);
        Item softItem4 = Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_15_COST_15);
        Item heavyItem1 = Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_99_COST_99);
        Item heavyItem2 = Fixture.from(Item.class).gimme(ItemTemplate.WEIGHT_99_COST_99);
        softItem1.setIndex(1);
        heavyItem1.setIndex(2);
        softItem2.setIndex(3);
        heavyItem2.setIndex(4);
        softItem3.setIndex(5);
        softItem4.setIndex(6);
        packageWithLowWeightLimit.setItems(Arrays.asList(softItem1, heavyItem1, softItem2, heavyItem2, softItem3, softItem4));
        List<Package> packages = Arrays.asList(packageWithLowWeightLimit);

        //when
        List<Package> selectedPackages = PackageSelector.select(packages);

        //then
        assertNotNull(selectedPackages, "Selected packages should not be null");
        Package selectedPackage1 = selectedPackages.get(0);
        assertAll("Assert packages items", ()->{
            assertNotNull(selectedPackage1.getItems());
            assertEquals(2, selectedPackage1.getItems().size());
            assertThat("Combination should not pass weight limit", selectedPackage1.getItems(), hasItems(softItem1, softItem3));
        });
    }
}