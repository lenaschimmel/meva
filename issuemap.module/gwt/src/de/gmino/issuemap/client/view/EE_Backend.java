package de.gmino.issuemap.client.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.domain.Address;
import de.gmino.geobase.client.request.Geocoder;
import de.gmino.geobase.client.request.Geocoder.SearchLocationListener;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.DecentralizedGeneration;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class EE_Backend extends Composite  {

	public class GeocodeCommand implements RepeatingCommand {

		private int i = 0;

		public GeocodeCommand() {
		}
		
		private Geocoder gc = new Geocoder();
			
		@Override
		public boolean execute() {
			
			DecentralizedGeneration gen;
			do
			{
				if(i >= data.size())
					return false;
				
				gen = data.get(i);
				i++;
			} while(!gen.getLocation().isEmpty());
			
			final DecentralizedGeneration searchGen = gen;
			
			gc.searchLocationByAddress(gen.getAddress(), new SearchLocationListener() {

				@Override
				public void onLocationNotFound() {
					
				}
				
				@Override
				public void onLocationFound(LatLon location) {
					searchGen.setLocation(location);
					Requests.saveEntity(searchGen, null);
					list.updateData(data);
				}

				@Override
				public void onError(String message) {
					Window.alert("Es ist ein Fehler bei der Straßensuche passiert: "
							+ message);
				}
			});
			return true;
		}
	}
	
	private static Show_Maps_BackendUiBinder uiBinder = GWT
			.create(Show_Maps_BackendUiBinder.class);

	
	interface Show_Maps_BackendUiBinder extends
			UiBinder<Widget, EE_Backend> {
	}

	private Map map;
	ArrayList<DecentralizedGeneration> data;

	public EE_Backend() {
		initWidget(uiBinder.createAndBindUi(this));
		this.data = new ArrayList<DecentralizedGeneration>();
		list = new ListView<DecentralizedGeneration>(){
			@Override
			de.gmino.issuemap.client.view.ListView.ListViewItem<DecentralizedGeneration> createListItem(
					DecentralizedGeneration gen) {
				return new Generation_List_Item(gen);
			}
		};
		listPanel.add(list);
	}

	@UiField
	TextArea taCsvInput;
	@UiField
	Button btCsvImport;
	@UiField
	Button btGeocodeAll;
	@UiField
	SimplePanel listPanel;
	private ListView<DecentralizedGeneration> list;

	@UiHandler("btGeocodeAll")
	void onBtGeocodeAllClick(ClickEvent e) {
		Scheduler scheduler = Scheduler.get();
		
		scheduler.scheduleFixedPeriod(new GeocodeCommand(), 5000);
	}
	
	@UiHandler("btCsvImport")
	void onClickExport(ClickEvent e) {
		String csv = taCsvInput.getText();
		final String[] lines = csv.split("\\n");

		Requests.getNewEntities(DecentralizedGeneration.type, lines.length, new RequestListener<DecentralizedGeneration>() {
			@Override
			public void onFinished(Collection<DecentralizedGeneration> results) {
				String[] newParts = new String[5];

				Iterator<DecentralizedGeneration> gens = results.iterator();
				
				for(String line : lines)
				{
					if(line.startsWith("Ort"))
						continue;
					if(line.length() < 10)
						continue;
					String[] parts = line.split(",");
					int len = parts.length;
					int n = 0;

					for(int i = 0; i  < len; i++)
					{
						String part = parts[i];
						if(part.startsWith("\""))
							do {
								i++;
								part += "," + parts[i];
							} while(!part.endsWith("\""));
						newParts[n++] = part.replace("\"", "");
					}

					String street = newParts[2].substring(0, newParts[2].lastIndexOf(' '));
					String houseNumber = newParts[2].substring(newParts[2].lastIndexOf(' ')+1);
					
					Address addr = new Address("",street, houseNumber, newParts[1], newParts[0], "");
					
					DecentralizedGeneration gen = gens.next();
					gen.setAddress(addr);
					gen.setPower(Float.parseFloat(newParts[3].replace(',', '.')));
					gen.setUnitType("pv");
					gen.setVoltage(newParts[4]);
					gen.setMap_instance(map);
					map.getGenerations().add(gen);
					data.add(gen);
				}
				Requests.saveEntities(results, null);
				Requests.saveEntity(map, null);
				list.updateData(data);
			}
		});	
	}
	
	public void setMap(Map map)
	{
		
		this.map = map;
		Collection<de.gmino.issuemap.client.domain.DecentralizedGeneration> generations = IssuemapGwt.<DecentralizedGeneration,de.gmino.issuemap.shared.domain.DecentralizedGeneration>convertCollection(map.getGenerations());
		Requests.loadEntities(generations, new RequestListener<DecentralizedGeneration>() {
			@Override
			public void onFinished(Collection<DecentralizedGeneration> results) {
				data.clear();
				data.addAll(results);
				list.updateData(data);
			}
		});
	}
}
