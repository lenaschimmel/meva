package de.gmino.meva.shared;

import java.util.Collection;

public interface Query extends Value {
	public Collection<Long> evaluate();
	
	public String getUrlPostfix();
}
