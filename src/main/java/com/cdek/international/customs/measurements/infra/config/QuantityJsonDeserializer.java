package com.cdek.international.customs.measurements.infra.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import tech.units.indriya.quantity.Quantities;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author bantu
 */
@SuppressWarnings("rawtypes")
public class QuantityJsonDeserializer extends StdDeserializer<Quantity> {
    public QuantityJsonDeserializer() {
        super(Quantity.class);
    }

    @Override
    public Quantity deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
        TreeNode root = jp.readValueAsTree();
        if (root.get("value") == null) {
            throw new JsonParseException(jp, "Value not found for quantity type.");
        }
        if (root.get("unit") == null) {
            throw new JsonParseException(jp, "Unit not found for quantity type.");
        }
        if (root.get("scale") == null) {
            throw new JsonParseException(jp, "Scale not found for quantity type.");
        }

        ObjectCodec codec = jp.getCodec();
        BigDecimal value = codec.treeToValue(root.get("value"), BigDecimal.class);
        Unit<?> unit = codec.treeToValue(root.get("unit"), Unit.class);
        Quantity.Scale scale = Quantity.Scale.valueOf(codec.treeToValue(root.get("scale"), String.class));

        return Quantities.getQuantity(value, unit, scale);
    }
}
