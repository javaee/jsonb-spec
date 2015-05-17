package examples.mapping;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

/**
 * Created by mvojtek on 19/04/15.
 */
public class I_JSONCompatibility {

    public static void main(String[] args) {
        JsonbConfig jsonIConfig = new JsonbConfig().
                withStrictIJSONSerializationCompliance(true).
                withIJSONValidation(true);

        Jsonb jsonb = JsonbBuilder.create(jsonIConfig);
    }
}
