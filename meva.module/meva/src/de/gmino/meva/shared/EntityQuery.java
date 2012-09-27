package de.gmino.meva.shared;

import java.util.Collection;

public interface EntityQuery extends Value {
	public Collection<Long> evaluate();
	
	public String getUrlPostfix();
}
