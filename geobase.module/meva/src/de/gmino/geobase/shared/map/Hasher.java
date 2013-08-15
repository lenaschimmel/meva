package de.gmino.geobase.shared.map;

public class Hasher {
	
	int hash = 214523;
	
	public void hashInt(int i)
	{
		hash = ((hash + i) * 67867967) % 961748927;
	}
	
	public void hashLong(long l)
	{
		hashInt((int)l);
		hashInt((int)(l>>32));
	}
	
	public void hashChar(char c)
	{
		hashInt(c);
	}
	
	public void hashShort(short s)
	{
		hashInt(s);
	}

	public void hashObject(Object o)
	{
		if(o == null)
			hashInt(77);
		else
			hashInt(o.hashCode());
	}

	public void hashFloat(float f)
	{
		hashInt((int)f);
		if(f != 0)
			hashInt((int)(1/f));
		if(f > 0 && f != 1)
			hashInt((int)(1/(1-f)));
		if(f < 0 && f != -1)
			hashInt((int)(1/(-1-f)));
	}

	public void hashDouble(double d)
	{
		hashInt((int)d);
		if(d != 0)
			hashInt((int)(1/d));
		if(d > 0 && d != 1)
			hashInt((int)(1/(1-d)));
		if(d < 0 && d != -1)
			hashInt((int)(1/(-1-d)));
	}
	
	public void hashBoolean(boolean b)
	{
		hashInt(b ? 6709 : 7823);
	}
	
	public int getValue()
	{
		return hash;
	}
	
	public void reset()
	{
		hash = 214523;
	}
}
