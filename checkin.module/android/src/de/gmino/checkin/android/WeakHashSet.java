package de.gmino.checkin.android;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * A WeakHashSet simply is a set that does not keep its elements alive - if no
 * other reference to some element is present outside of this set, the element
 * will simply disappear.
 * 
 * This class is based upon the WeakHashMap. One way to use a WeakHashMap as a
 * WeakHashSet would be
 * 
 * <code>Set<T> s = Collections.newSetFromMap(new WeakHashMap<T,
 * Boolean>())</code>;
 * 
 * But this is not supported on Android. So this class tries to do the same.
 * 
 * Originally written for the platform, see
 * /home/lena/Arbeitsfläche/Kram/workspaceLinuxKaputt
 * /java-platform/src/de/gmino/platform/misc/WeakHashSet.java
 * 
 * @author lena
 * 
 * @param <E>
 */
public class WeakHashSet<E> implements Set<E> {
	WeakHashMap<E, Boolean> inner;

	public WeakHashSet() {
		this.inner = new WeakHashMap<E, Boolean>();
	}

	public boolean add(E e) {
		assert (e != null);
		inner.put(e, true);
		// this return statement is just to comply with the interface.
		return false;
	}

	public boolean addAll(Collection<? extends E> c) {
		boolean changed = false;
		for (E e : c)
			changed |= add(e);
		return true;
	}

	public void clear() {
		inner.clear();
	}

	public boolean contains(Object o) {
		return inner.containsKey(o);
	}

	public boolean containsAll(Collection<?> c) {
		for (Object e : c)
			if (!inner.containsKey(e))
				return false;
		return true;
	}

	public boolean isEmpty() {
		return inner.isEmpty();
	}

	public Iterator<E> iterator() {
		return inner.keySet().iterator();
	}

	public boolean remove(Object o) {
		return inner.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		boolean changed = false;
		for (Object e : c)
			if (!inner.remove(e))
				changed |= true;
		return changed;
	}

	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException(
				"Retain is not yet supported by this class - feel free to implement it!");
	}

	public int size() {
		return inner.size();
	}

	public Object[] toArray() {
		return inner.keySet().toArray();
	}

	public <T> T[] toArray(T[] a) {
		return inner.keySet().toArray(a);
	}
}