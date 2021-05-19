package com.mobiquity.packer;

import br.com.six2six.fixturefactory.Fixture;
import com.mobiquity.BaseTestClass;
import com.mobiquity.exception.APIException;
import com.mobiquity.packer.model.Package;
import com.mobiquity.packer.templates.PackageTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PackerTest extends BaseTestClass {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    @BeforeAll
    public static void config() {
        BaseTestClass.config();
    }

    @BeforeEach
    public void setUp(){
        //Mockito.clearAllCaches();
    }

    @Test
    @DisplayName("Should return packages")
    void shouldReturnPackages() throws APIException {
        //given
        String path = "path/to/file";
        List<Package> packagesFounded = Fixture.from(Package.class).gimme(2, PackageTemplate.VALID);
        List<Package> packagesSuggested = Fixture.from(Package.class).gimme(1, PackageTemplate.VALID);
        String resultExpected = "result";

        //when
        String packageResponse;
        try (
            MockedStatic<PackageReader> packageReader = Mockito.mockStatic(PackageReader.class);
            MockedStatic<PackageSelector> packageSelector = Mockito.mockStatic(PackageSelector.class);
            MockedStatic<PackageWriter> packageWriter = Mockito.mockStatic(PackageWriter.class)
        ) {
            packageReader.when(() -> PackageReader.find(path)).thenReturn(packagesFounded);
            packageSelector.when(() -> PackageSelector.select(packagesFounded)).thenReturn(packagesSuggested);
            packageWriter.when(() -> PackageWriter.write(packagesSuggested)).thenReturn(resultExpected);
            packageResponse = Packer.pack(path);
        }

        //then
        assertEquals(resultExpected, packageResponse);
    }
}