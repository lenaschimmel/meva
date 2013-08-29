package de.gmino.issuemap.client.view.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class ListView<DataItem> extends VerticalPanel {
	
	public interface ListViewItem<DataItem> extends IsWidget
	{
		public void setDataItem(DataItem item);
	}
	
	private ArrayList<ListViewItem<DataItem>> items;
	
	public abstract ListViewItem<DataItem> createListItem(DataItem item);
	
	public ListView() {
		items = new ArrayList<ListViewItem<DataItem>>();
	}
	
	public void updateData(Collection<DataItem> data)
	{
		ArrayList<ListViewItem<DataItem>> newItems = new ArrayList<ListViewItem<DataItem>>();
		Iterator<ListViewItem<DataItem>> it = items.iterator();
		for(DataItem i : data)
		{
			if(it.hasNext())
			{
				ListViewItem<DataItem> item = it.next();
				item.setDataItem(i);
				newItems.add(item);	
			}
			else
			{
				final ListViewItem<DataItem> item = createListItem(i);
				newItems.add(item);		    	
				add(item);
			}
		}
		
		while(it.hasNext())
		{
			remove(it.next());
			it.remove();
		}
		
		items = newItems;
	}
}
