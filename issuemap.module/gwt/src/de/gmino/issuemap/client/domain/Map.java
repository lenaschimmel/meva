// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.client.domain;

// gmino stuff
import java.util.Collection;
import java.util.TreeMap;
import java.util.TreeSet;

import de.gmino.geobase.client.domain.Address;
import de.gmino.geobase.client.domain.ImageUrl;
import de.gmino.geobase.client.domain.LatLon;
import de.gmino.issuemap.client.ImageUrlLoader;
import de.gmino.issuemap.client.domain.gen.MapGen;
import de.gmino.issuemap.shared.domain.MapHasMarkertype;
import de.gmino.meva.shared.RelationCollection;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;
// default imports
// imports for JSON
// imports for field types
public class Map extends MapGen {
	final TreeMap<Long, Markertype> markertypesById = new TreeMap<Long, Markertype>();
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Map(long id)
	{
		super(id);
	}
	
	public Map(
			long id,
			boolean ready,
			String title,
			String description,
			String subdomain,
			String primary_color,
			String secondary_color,
			String background_color,
			String resolved_color,
			String city,
			LatLon initLocation,
			int initZoomlevel,
			String layer,
			String headerText,
			ImageUrl logo,
			String infoText,
			String mapTyp,
			String website,
			String impressum_url,
			String email,
			Address postal_address)
	{
		super(
			id,
			ready,
			title,
			description,
			subdomain,
			primary_color,
			secondary_color,
			background_color,
			resolved_color,
			city,
			(de.gmino.geobase.client.domain.LatLon)initLocation,
			initZoomlevel,
			layer,
			headerText,
			(de.gmino.geobase.client.domain.ImageUrl)logo,
			infoText,
			mapTyp,
			website,
			impressum_url,
			email,
			(de.gmino.geobase.client.domain.Address)postal_address
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
	@SuppressWarnings("unchecked")
	public void loadMarkertypes(final RequestListener<Markertype> listener)
	{
		RelationCollection<? extends MapHasMarkertype> hasMarkertype = getHasMarkertypes();
		Requests.loadEntities(hasMarkertype, new RequestListener<MapHasMarkertype>() {
			@Override
			public void onFinished(Collection<MapHasMarkertype> results) {
				for(MapHasMarkertype mhm : results)
				{
					markertypesById.put(mhm.getMarkertypeId(), (Markertype) mhm.getMarkertype());
				}
				Requests.loadEntities(markertypesById.values(), new RequestListener<Markertype>() {
					@Override
					public void onFinished(final Collection<Markertype> results) {
						Collection<String> imagesToLoad = new TreeSet<String>();
					
						for(Markertype mt : results)
						{
							String imageName =mt.getImageName();
							String url = "/mapicon/" + imageName + ".png";
							imagesToLoad.add(url);
						}

						ImageUrlLoader.getInstance().loadImages(imagesToLoad, new ImageUrlLoader.ImageLoadListener() {
							@Override
							public void onLoaded() {
								listener.onFinished(results);
								for(Markertype r : results)
									listener.onNewResult(r);
							}
						});
					}
				});
			}
		});
	}
	
	public Markertype getMarkertypeById(long id)
	{
		return markertypesById.get(id);
	}
	
	public Collection<Markertype> getMarkertypes()
	{
		return markertypesById.values();
	}
	
	public TreeMap<Long,Markertype> getMarkertypeMap()
	{
		return markertypesById;
	}
	
	
}
