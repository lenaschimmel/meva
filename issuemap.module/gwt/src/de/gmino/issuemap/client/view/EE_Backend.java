package de.gmino.issuemap.client.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.domain.Address;
import de.gmino.issuemap.client.domain.DecentralizedGeneration;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class EE_Backend extends Composite  {

	
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
		executeRequest();
	}

	@UiField
	TextArea taCsvInput;
	@UiField
	Button btCsvImport;
	@UiField
	SimplePanel listPanel;
	private ListView<DecentralizedGeneration> list;

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

					String street = newParts[2].substring(0, newParts[2].lastIndexOf(' ')-1);
					String houseNumber = newParts[2].substring(newParts[2].lastIndexOf(' ')+1);
					
					Address addr = new Address("",street, houseNumber, newParts[1], newParts[0], "");
					
					DecentralizedGeneration gen = gens.next();
					gen.setAddress(addr);
					gen.setPower(Float.parseFloat(newParts[3].replace(',', '.')));
					gen.setUnitType("pv");
					gen.setVoltage(newParts[4]);
					data.add(gen);
					//addDecentralizedGenerationElement(gen);
				}
				Requests.saveEntities(results, null);
				list.updateData(data);
			}
		});	
	}
	
	public void setMap(Map map)
	{
		this.map = map;
	}
	
	private void executeRequest(){
		// TODO Load EE form this map, not all EEs. Also save them to the map.
//		Requests.getLoadedEntitiesByType(DecentralizedGeneration.type, new RequestListener<DecentralizedGeneration>() {
//			@Override
//			public void onNewResult(DecentralizedGeneration result) {
//				addDecentralizedGenerationElement(result);
//			}
//		});
	}
}
