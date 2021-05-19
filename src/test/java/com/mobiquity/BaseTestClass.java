package com.mobiquity;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.mobiquity.helper.MockitoExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({MockitoExtension.class})
public class BaseTestClass {

    @BeforeAll
    public static void config(){
        FixtureFactoryLoader.loadTemplates("com.mobiquity");
    }
}