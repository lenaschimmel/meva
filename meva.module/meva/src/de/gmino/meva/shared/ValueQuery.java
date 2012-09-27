package de.gmino.meva.shared;

import java.io.IOException;
import java.util.Collection;

import org.itemscript.core.values.JsonObject;

public interface ValueQuery<ValueClass extends Value> extends Value {
	public Collection<ValueClass> evaluate();
	
	public String getUrlPostfix();
	
	public Value valueFromJson(JsonObject json) throws IOException;
}
