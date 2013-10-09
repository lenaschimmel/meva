package de.gmino.issuemap.client.view.backend;

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
import de.gmino.issuemap.client.domain.DecentralizedGeneration;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.view.list.Generation_List_Item;
import de.gmino.issuemap.client.view.list.ListView;
import de.gmino.meva.shared.Util;
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
			public de.gmino.issuemap.client.view.list.ListView.ListViewItem<DecentralizedGeneration> createListItem(DecentralizedGeneration gen) {
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
				String[] newParts = new String[6];

				Iterator<DecentralizedGeneration> gens = results.iterator();
				
				for(String line : lines)
				{
					if(line.startsWith("Ort") || line.startsWith("\"Ort"))
						continue;
					if(line.length() < 10)
						continue;
					String[] parts = line.split(",");
					if(parts.length == 1)
						parts = line.split(";");
					int len = parts.length;
					int n = 0;

					for(int i = 0; i  < len; i++)
					{
						String part = parts[i];
						if(part.startsWith("\""))
							while(!part.endsWith("\""))
							{
								i++;
								part += "," + parts[i];
							};
						newParts[n++] = part.replace("\"", "");
					}

					String[] streetAndNumberParts = newParts[2].split(" ");
					int numberIndex = -1;
					for(int i = 0; i < streetAndNumberParts.length; i++)
					{
						if(streetAndNumberParts[i].length() > 0 && Character.isDigit(streetAndNumberParts[i].charAt(0)))
						{
							numberIndex = i;
						}
					}
					String street, houseNumber;
					if(numberIndex > -1)
					{
						street = joinStrings(streetAndNumberParts, 0, numberIndex - 1);
						houseNumber =  joinStrings(streetAndNumberParts, numberIndex, streetAndNumberParts.length - 1);
					}
					else
					{
						street = newParts[2];
						houseNumber =  "";
					}
					
					Address addr = new Address("",street, houseNumber, newParts[1], newParts[0], "");
					
					DecentralizedGeneration gen = gens.next();
					gen.setAddress(addr);
					gen.setPower(Float.parseFloat(newParts[3].replace(',', '.')));
					if(newParts.length >= 6 && newParts[5].equals("Windenergie"))
						gen.setUnitType("wk");
					else
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

			private String joinStrings(String[] parts, int start, int end) {
				String ret = "";
				for(int i = start; i <= end; i++)
				{
					if(i > 0)
						ret += " ";
					ret += parts[i];
				}
				return ret;
			}
		});	
	}
	
	public void setMap(Map map)
	{
		
		this.map = map;
		Collection<de.gmino.issuemap.client.domain.DecentralizedGeneration> generations = Util.<DecentralizedGeneration,de.gmino.issuemap.shared.domain.DecentralizedGeneration>convertCollection(map.getGenerations());
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
