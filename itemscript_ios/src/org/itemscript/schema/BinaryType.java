
package org.itemscript.schema;


import org.itemscript.core.Params;
import org.itemscript.core.exceptions.ItemscriptError;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;
/**
 * Type class for Binary Type. All BinaryTypes base64 encoded and are represented by strings.
 * 
 * @author Eileen Bai
 */
final class BinaryType extends TypeBase {
	private static final String MAX_BYTES_KEY = ".maxBytes";
	private final boolean hasDef;
	private final int maxBytes;
	
	/**
	 * Create a new BinaryType. Sets all associated ".keys" that are specified.
	 * 
	 * @param schema
	 * @param extendsType
	 * @param def
	 */
    BinaryType(Schema schema, Type extendsType, JsonObject def) {
        super(schema, extendsType, def);
    	if (def != null) {
			hasDef = true;
			if (def.containsKey(MAX_BYTES_KEY)) {
				maxBytes = def.getRequiredInt(MAX_BYTES_KEY);
			} else {
				maxBytes = -1;
			}
    	} else {
    		hasDef = false;
    		maxBytes = -1;
    	}
    }

    @Override
    public boolean isBinary() {
        return true;
    }
    
	private Params pathValueParams(String path, byte[] binary) {
		return schema().pathParams(path).p("value", binary);
	}

    @Override
    public void validate(String path, JsonValue value) {
        super.validate(path, value);
        byte[] binaryValue;
		if (!value.isString()) {
			throw ItemscriptError.internalError(this,
					"validate.value.was.not.string", schema().pathParams(path)
							.p("value", value.toCompactJsonString()));
		}
        try {
			binaryValue = value.binaryValue();
        } catch (ItemscriptError e) {
        	throw ItemscriptError.internalError(this,
        			"validate.value.could.not.be.parse.as.base.64", schema().pathParams(path)
        				.p("value", value.toCompactJsonString()));
        } catch (IllegalArgumentException e) {
        	throw ItemscriptError.internalError(this,
        			"validate.value.illegal.character.in.base.64.encoded.data", schema().pathParams(path)
        				.p("value", value.toCompactJsonString()));
        }
        if (hasDef) {
			validateBinary(path, binaryValue);
        }
    }
    
    private void validateBinary(String path, byte[] binaryValue) {
		if (maxBytes > 0) {
			if (binaryValue.length > maxBytes) { throw ItemscriptError.internalError(this,
					"validateBinary.value.has.too.many.bytes", pathValueParams(path, binaryValue)
							.p("specified", maxBytes)
							.p("input", binaryValue.length)); }
		}
    }
}