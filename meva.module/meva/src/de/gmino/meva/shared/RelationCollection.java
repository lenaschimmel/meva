package de.gmino.meva.shared;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.TreeSet;

public class RelationCollection<Item extends Entity> extends AbstractCollection<Item> {

	Entity container;
	String relname;
	TreeSet<Item> items;

	public RelationCollection(Entity container, String relname) {
		super();
		this.container = container;
		this.relname = relname;
		this.items = new TreeSet<Item>();
	}

	@Override
	public Iterator<Item> iterator() {
		return items.iterator();
	}

	@Override
	public int size() {
		return items.size();
	}

	@Override
	public boolean contains(Object o) {
		return items.contains(o);
	}


	@Override
	public boolean add(Item e) {
		final boolean result = items.add((Item) e);
		if(result)
			((Entity) e).addToRelation(relname, container);
		return result;
	};

	@Override
	public boolean remove(Object o) {
		final boolean result = items.remove(o);
		if (result && o instanceof Entity) {
			Entity e = (Entity) o;
			e.removeFromRelation(relname, container);
		}
		return result;
	}

	class RemoveSensitiveIterator implements Iterator<Item> {
		Iterator<Item> base;
		Item lastReturned;

		public RemoveSensitiveIterator(Iterator<Item> base) {
			super();
			this.base = base;
		}

		public boolean hasNext() {
			return base.hasNext();
		}

		public Item next() {
			lastReturned = base.next();
			return lastReturned;
		}

		public void remove() {
			base.remove();
			lastReturned.removeFromRelation(relname, container);
		}
	}
}
