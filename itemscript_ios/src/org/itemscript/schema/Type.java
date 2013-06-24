
package org.itemscript.schema;

import org.itemscript.core.values.JsonValue;

/**
 * Represents a Type.
 * 
 *  @author Jacob Davies<br/><a href="mailto:jacob@itemscript.org">jacob@itemscript.org</a>
 *
 */

public interface Type {
	
	/**
	 * Retrieves the description tied to this Type.
	 * 
	 * @return The associated description.
	 */
    public String description();

    /**
     * Tests whether this Type is an ArrayType.
     * 
     * @return True if this is an ArrayType, false otherwise.
     */
    public boolean isArray();
    
    /**
     * Tests whether this Type is a BinaryType.
     * 
     * @return True if this is a BinaryType, false otherwise.
     */
    public boolean isBinary();

    /**
     * Tests whether this Type is a BooleanType.
     * 
     * @return True if this is a BooleanType, false otherwise.
     */
    public boolean isBoolean();

    /**
     * Tests whether this Type is an IntegerType.
     * 
     * @return True if this is an IntegerType, false otherwise.
     */
    public boolean isInteger();

    /**
     * Tests whether this Type is a NullType.
     * 
     * @return True if this is a NullType, false otherwise.
     */
    public boolean isNull();

    /**
     * Tests whether this Type is a NumberType.
     * 
     * @return True if this is a NumberType, false otherwise.
     */
    public boolean isNumber();

    /**
     * Tests whether this Type is an ObjectType.
     * 
     * @return True if this is an ObjectType, false otherwise.
     */
    public boolean isObject();

    /**
     * Tests whether this Type is a StringType.
     * 
     * @return True if this is a StringType, false otherwise.
     */
    public boolean isString();
    
    /**
     * Tests whether this Type is a DecimalType.
     * 
     * @return True if this is a DecimalType, false otherwise.
     */
    public boolean isDecimal();
    
    /**
     * Tests whether this Type is a LongType.
     * 
     * @return True if this is a LongType, false otherwise.
     */
    public boolean isLong();
    
    /**
     * Tests whether this Type is a AnyType.
     * 
     * @return True if this is a AnyType, false otherwise.
     */
    public boolean isAny();

    /**
     * Retrieves the schema tied to this Type.
     *     
     * @return The associated Schema.
     */
    public Schema schema();
    
    /**
     * Validates that the specified JsonValue is valid according to its type.
     * 
     * @throws ItemscriptError if invalid.
     * @param path
     * @param value
     */
    public void validate(String path, JsonValue value);
}