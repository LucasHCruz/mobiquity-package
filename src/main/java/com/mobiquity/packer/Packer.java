package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.model.Package;

import java.util.List;

public class Packer {
    private Packer() {
    }

    public static String pack(String filePath) throws APIException {
        try {
            List<Package> packagesFounded = PackageReader.find(filePath);
            PackageAssertion.validate(packagesFounded);
            List<Package> packagesSelected = PackageSelector.select(packagesFounded);
            return PackageWriter.write(packagesSelected);
        } catch (APIException apiException){
            throw apiException;
        }   catch (Exception e){
            throw new APIException("Something went wrong", e);
        }
    }

    public static void main(String[] args) throws APIException {
        System.out.println(Packer.pack("/home/lucascruz/Desktop/test-pack"));
        //Packer.pack("/home/lucascruz/Desktop/test-pack-heavy");
    }
}
