package examples.mapping;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

public class I_JSONCompatibility {

    public static void main(String[] args) {
        JsonbConfig jsonIConfig = new JsonbConfig().
                withStrictIJSON(true);

        Jsonb jsonb = JsonbBuilder.create(jsonIConfig);
    }
}
