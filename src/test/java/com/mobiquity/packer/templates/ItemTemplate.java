package com.mobiquity.packer.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.mobiquity.packer.model.Item;

public class ItemTemplate implements TemplateLoader {
    public static final String VALID = "VALID";
    public static final String WEIGHT_10_COST_10 = "WEIGHT_10_COST_1";
    public static final String WEIGHT_15_COST_15 = "WEIGHT_15_COST_15";
    public static final String WEIGHT_1_COST_1 = "WEIGHT_1_COST_1";
    public static final String WEIGHT_1_COST_10 = "WEIGHT_1_COST_10";
    public static final String WEIGHT_5_COST_5 = "WEIGHT_5_COST_5";
    public static final String WEIGHT_5_COST_99 = "WEIGHT_5_COST_99";
    public static final String WEIGHT_99_COST_99 = "WEIGHT_99_COST_99";
    public static final String WEIGHT_999_COST_1 = "WEIGHT_999_COST_1";

    @Override
    public void load() {
        Fixture.of(Item.class).addTemplate(VALID, new Rule(){{
            add("index", random(Integer.class, range(1,10)));
            add("weight", random(Double.class, range(0.01,100.0)));
            add("cost", random(Double.class, range(0.01,100.0)));
        }});
        Fixture.of(Item.class).addTemplate(WEIGHT_1_COST_1).inherits(VALID, new Rule(){{
            add("index", random(Integer.class, range(1,10)));
            add("weight", random(Double.class, 1.0));
            add("cost", random(Double.class, 1.0));
        }});
        Fixture.of(Item.class).addTemplate(WEIGHT_5_COST_5).inherits(VALID, new Rule(){{
            add("index", random(Integer.class, range(1,10)));
            add("weight", random(Double.class, 5.0));
            add("cost", random(Double.class, 5.0));
        }});
        Fixture.of(Item.class).addTemplate(WEIGHT_5_COST_99).inherits(VALID, new Rule(){{
            add("index", random(Integer.class, range(1,10)));
            add("weight", random(Double.class, 5.0));
            add("cost", random(Double.class, 99.0));
        }});
        Fixture.of(Item.class).addTemplate(WEIGHT_1_COST_10).inherits(VALID, new Rule() {{
            add("index", random(Integer.class, range(1, 10)));
            add("weight", random(Double.class, 1.0));
            add("cost", random(Double.class, 10.0));
        }});
        Fixture.of(Item.class).addTemplate(WEIGHT_10_COST_10).inherits(VALID, new Rule() {{
            add("index", random(Integer.class, range(1, 10)));
            add("weight", random(Double.class, 10.0));
            add("cost", random(Double.class, 1.0));
        }});
        Fixture.of(Item.class).addTemplate(WEIGHT_15_COST_15).inherits(VALID, new Rule() {{
            add("index", random(Integer.class, range(1, 10)));
            add("weight", random(Double.class, 15.0));
            add("cost", random(Double.class, 15.0));
        }});
        Fixture.of(Item.class).addTemplate(WEIGHT_99_COST_99).inherits(VALID, new Rule(){{
            add("index", random(Integer.class, range(1,10)));
            add("weight", random(Double.class, 99.0));
            add("cost", random(Double.class, 99.0));
        }});
        Fixture.of(Item.class).addTemplate(WEIGHT_999_COST_1).inherits(VALID, new Rule(){{
            add("index", random(Integer.class, range(1,10)));
            add("weight", random(Double.class, 999.0));
            add("cost", random(Double.class, 1.0));
        }});
    }
}
