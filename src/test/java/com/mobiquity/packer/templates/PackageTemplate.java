package com.mobiquity.packer.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.Package;

public class PackageTemplate implements TemplateLoader {
    public static final String VALID = "VALID";
    public static final String VALID_EMPTY = "VALID_EMPTY";
    public static final String VALID_EMPTY_WEIGHT_INFINITY = "VALID_EMPTY_WEIGHT_INFINITY";
    public static final String VALID_EMPTY_WEIGHT_20 = "VALID_EMPTY_WEIGHT_20";
    public static final String VALID_EMPTY_WEIGHT_50 = "VALID_EMPTY_WEIGHT_50";
    public static final String INVALID_HEAVY_ITEMS = "INVALID_HEAVY_ITEMS";

    @Override
    public void load() {
        Fixture.of(Package.class).addTemplate(VALID_EMPTY, new Rule(){{
            add("weightLimit", 1.0);
        }});
        Fixture.of(Package.class).addTemplate(VALID_EMPTY_WEIGHT_INFINITY, new Rule(){{
            add("weightLimit", Integer.valueOf(Integer.MAX_VALUE).doubleValue());
        }});
        Fixture.of(Package.class).addTemplate(VALID_EMPTY_WEIGHT_20, new Rule(){{
            add("weightLimit", 20.0);
        }});
        Fixture.of(Package.class).addTemplate(VALID_EMPTY_WEIGHT_50, new Rule(){{
            add("weightLimit", 50.0);
        }});
        Fixture.of(Package.class).addTemplate(VALID, new Rule(){{
            add("weightLimit", 1.0);
            add("items", has(2).of(Item.class, ItemTemplate.VALID));
        }});
        Fixture.of(Package.class).addTemplate(VALID, new Rule(){{
            add("weightLimit", random(Double.class, range(1, 10.0)));
            add("items", has(2).of(Item.class, ItemTemplate.VALID));
        }});
        Fixture.of(Package.class).addTemplate(INVALID_HEAVY_ITEMS, new Rule(){{
            add("weightLimit", random(Double.class, range(1, 10.0)));
            add("items", has(2).of(Item.class, ItemTemplate.WEIGHT_1_COST_1, ItemTemplate.WEIGHT_999_COST_1));
        }});
    }
}
