package com.mobiquity.packer;

import br.com.six2six.fixturefactory.Fixture;
import com.mobiquity.BaseTestClass;
import com.mobiquity.exception.APIException;
import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.Package;
import com.mobiquity.packer.templates.ItemTemplate;
import com.mobiquity.packer.templates.PackageTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PackageAssertionTest extends BaseTestClass {

    @BeforeAll
    public static void config() {
        BaseTestClass.config();
    }

    @Test
    @DisplayName("Should validate packages")
    void shouldValidatePackages(){
        //given
        List<Package> packages = Fixture.from(Package.class).gimme(2, PackageTemplate.VALID);

        //when
        assertDoesNotThrow(() -> PackageAssertion.validate(packages));

        //then
        //expect to not throw
    }

    @ParameterizedTest
    @ValueSource(doubles = {100.01, 999.00})
    @DisplayName("Should validate packages with heavy items. Throw APIException")
    void shouldValidatePackagesWithHeavyItemsAndThrowException(double weight){
        //given
        List<Package> packages = Fixture.from(Package.class).gimme(2, PackageTemplate.VALID);
        packages.get(0).getItems().get(0).setWeight(weight);

        //when
        APIException apiException = assertThrows(APIException.class, () -> PackageAssertion.validate(packages));

        //then
        assertEquals("The weight of an item must be a maximum of 100", apiException.getMessage(), "Exception message");
    }

    @ParameterizedTest
    @ValueSource(doubles = {100.01, 999.00})
    @DisplayName("Should validate packages with expensive items. Throw APIException")
    void shouldValidatePackagesWithExpensiveItemsThrowApiexception(double cost){
        //given
        List<Package> packages = Fixture.from(Package.class).gimme(2, PackageTemplate.VALID);
        packages.get(0).getItems().get(0).setCost(cost);

        //when
        APIException apiException = assertThrows(APIException.class, () -> PackageAssertion.validate(packages));

        //then
        assertEquals("The cost of an item must be a maximum of 100", apiException.getMessage(), "Exception message");
    }

    @ParameterizedTest
    @ValueSource(ints = {16, 100})
    @DisplayName("Should validate packages with more than 15 items. Throw APIException")
    void shouldValidatePackagesWithMoreThan15ItemsThrowApiexception(int itemsSize){
        //given
        Package pack = Fixture.from(Package.class).gimme(PackageTemplate.VALID);
        pack.setItems(Fixture.from(Item.class).gimme(itemsSize, ItemTemplate.VALID));

        //when
        APIException apiException = assertThrows(APIException.class, () -> PackageAssertion.validate(Arrays.asList(pack)));

        //then
        assertEquals("Packages must have a maximum of 15 items", apiException.getMessage(), "Exception message");
    }
}
