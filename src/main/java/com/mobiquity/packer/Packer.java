package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.model.Package;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Packer {
    private Packer() {
    }

    public static String pack(String filePath) throws APIException {
        try {
            log.info("Packing");
            List<Package> packagesFounded = PackageReader.find(filePath);
            PackageAssertion.validate(packagesFounded);
            List<Package> packagesSelected = PackageSelector.select(packagesFounded);
            String packageOutput = PackageWriter.write(packagesSelected);
            log.info("Packages generated");
            return packageOutput;
        } catch (APIException apiException){
            throw apiException;
        }   catch (Exception e){
            throw new APIException("Something went wrong", e);
        }
    }

    public static void main(String[] args) throws APIException {
        System.out.println(Packer.pack("path/to/file"));
    }
}
