package de.gmino.issuemap.client.view.popup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.geobase.shared.domain.Timestamp;
import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Poi;
import de.gmino.issuemap.client.poi.IssueIconRenderer;
import de.gmino.issuemap.client.view.list.IssueList_Item;
import de.gmino.issuemap.client.view.list.ListView;
import de.gmino.issuemap.client.view.list.ListView.ListViewItem;
import de.gmino.issuemap.client.view.list.ResolvedList_Item;
import de.gmino.issuemap.client.view.popup.Info_PopUp.Style;
import de.gmino.issuemap.shared.domain.Markertype;
import de.gmino.meva.client.domain.KeyValueSet;

public class Info_PopUp extends Composite implements HasText {

	private final class IssueDateComparator implements Comparator<Poi> {
		@Override
		public int compare(Poi i1, Poi i2) {

			int timestampComparision = -IssuemapGwt.compareLong(i1.getCreationTimestamp().getMillisSinceEpoch(), i2.getCreationTimestamp().getMillisSinceEpoch());
			if (timestampComparision != 0)
				return timestampComparision;
			else
				return IssuemapGwt.compareLong(i1.getId(), i2.getId());
			
		}
	}
	
	private static Info_PopUpUiBinder uiBinder = GWT
			.create(Info_PopUpUiBinder.class);

	private DecoratedPopupPanel decorated_panel;
	private Map mapObject;
	private ListView<Poi> list;
	private int resolvedCount;

	
	interface Info_PopUpUiBinder extends UiBinder<Widget, Info_PopUp> {
	}
	
	interface Style extends CssResource {
		String underline();
		String active();
	}

	public Info_PopUp(Map mapObject, DecoratedPopupPanel decorated_panel) {
		initWidget(uiBinder.createAndBindUi(this));
		title.setText(mapObject.getTitle());
		infotext.setHTML(new SafeHtmlBuilder().appendEscapedLines(mapObject.getInfoText()).toSafeHtml());
		infotext.setHeight(Window.getClientHeight()*0.5 + "px");
		help.setHeight(Window.getClientHeight()*0.5 + "px");
		if (mapObject.isCreate()){
			createHeading.setVisible(true);
			createContent.setVisible(true);
		}
		if (mapObject.isEdit()){
			editHeading.setVisible(true);
			editContent.setVisible(true);
		}
		if (mapObject.isDelete()){
			deleteHeading.setVisible(true);
			deleteContent.setVisible(true);
		}
		if (mapObject.isHas_fotos()){
			fotoHeading.setVisible(true);
			fotoContent.setVisible(true);
		}
		if (mapObject.isHas_comments()){
			commentsHeading.setVisible(true);
			commentsContent.setVisible(true);
		}
		if (mapObject.isMark()){
			markHeading.setVisible(true);
			markContent.setVisible(true);
		}
		if (mapObject.isHas_ratings()){
			rateHeading.setVisible(true);
			rateContent.setVisible(true);
		}
		if (mapObject.isHas_list()){
			listHeading.setVisible(true);
			listContent.setVisible(true);
		}
		
		resolvedPanel.setHeight(Window.getClientHeight()*0.5 + "px");
		if (mapObject.isMark()) tabButtonResolved.setVisible(true);
		parent.getElement().getStyle().setBackgroundColor(mapObject.getPopupBackgroundColor());
		infotext.getElement().getStyle().setColor(mapObject.getPopupTextColor());
		help.getElement().getStyle().setColor(mapObject.getPopupTextColor());
		impressum.getElement().getStyle().setColor(mapObject.getPopupTextColor());
		deckPanel.showWidget(0);
		this.decorated_panel = decorated_panel;
		this.mapObject=mapObject;
		activateTab(0);

		
		
		
	}
	
	@UiField
	Style style;
	
	@UiField
	Image close;
	@UiField
	Label title;
	@UiField
	HTML infotext;
	@UiField
	SimplePanel help;
	@UiField
	SimplePanel resolvedPanel;
	@UiField
	Label impressum;
	@UiField
	Panel parent;
	@UiField
	DeckPanel deckPanel;
	@UiField
	Button tabButtonInfo;
	@UiField
	Button tabButtonHelp;
	@UiField
	Button tabButtonResolved;
	@UiField
	Image marker_icon1;
//	@UiField
//	Image marker_icon2;
	@UiField
	Panel createContent;
	@UiField
	Panel editContent;
	@UiField
	Panel deleteContent;
	@UiField
	Panel fotoContent;
	@UiField
	Panel commentsContent;
	@UiField
	Panel markContent;
	@UiField
	Panel rateContent;
	@UiField
	Panel listContent;
	@UiField
	Label createHeading;
	@UiField
	Label editHeading;
	@UiField
	Label deleteHeading;
	@UiField
	Label fotoHeading;
	@UiField
	Label commentsHeading;
	@UiField
	Label markHeading;
	@UiField
	Label rateHeading;
	@UiField
	Label listHeading;

	public void activateTab(int i) {
		deckPanel.showWidget(i);
		tabButtonInfo.setStyleName(style.underline(), i == 0);
		if(i==0) tabButtonInfo.getElement().getStyle().setBorderColor(mapObject.getMarkerColor());
		else tabButtonInfo.getElement().getStyle().setBorderColor("transparent");
		tabButtonHelp.setStyleName(style.underline(), i == 1);
		if(i==1) tabButtonHelp.getElement().getStyle().setBorderColor(mapObject.getMarkerColor());
		else tabButtonHelp.getElement().getStyle().setBorderColor("transparent");
		tabButtonResolved.setStyleName(style.underline(), i == 2);
		if(i==2) tabButtonResolved.getElement().getStyle().setBorderColor(mapObject.getMarkerColor());
		else tabButtonResolved.getElement().getStyle().setBorderColor("transparent");

	}

	public int getActiveTab(){
		return deckPanel.getVisibleWidget();
	}
	
	@UiHandler("close")
	void onClick(ClickEvent e) {
		decorated_panel.hide();
	}
	
	@UiHandler("impressum")
	void onImpressumClick(ClickEvent e){
		Window.open(mapObject.getImpressum_url(), "Impressum", "");
		decorated_panel.hide();
		
	}
	
	@UiHandler("tabButtonInfo")
	void onTabButtonInfoClick(ClickEvent e) {
		activateTab(0);
	}

	@UiHandler("tabButtonHelp")
	void onTabButtonHelpClick(ClickEvent e) {
		activateTab(1);
	}
	
	@UiHandler("tabButtonResolved")
	void onTabButtonResolvedClick(ClickEvent e) {
		activateTab(2);
	}

	public void setText(String text) {

	}

	public String getText() {
		return null;
	}

	public void setMarkerIcons(OpenLayersSmartLayer smartLayer) {
		Poi poi = new Poi(-1);
		
		Markertype chosenMarkertype = mapObject.getHasMarkertypes().iterator().next();
		for(Markertype mt : mapObject.getHasMarkertypes())
		{
			if(mt.getMarkerName().equals("Sonstiges"))
				chosenMarkertype = mt;
		}
		final KeyValueSet markerClass = (KeyValueSet) chosenMarkertype.getMarkerClass();
		poi.setKeyvalueset(markerClass);
		poi.setMap_instance(mapObject);
		poi.setCreationTimestamp(Timestamp.now());
		poi.setMarkertype(chosenMarkertype);
		GwtIconRenderer<? super Poi> renderer =  smartLayer.getRendererForPoi(poi);
		String iconUrl = renderer.getIconUrl(poi);
		marker_icon1.setUrl(iconUrl);
		marker_icon1.setVisible(true);
//		marker_icon2.setUrl(iconUrl);
//		marker_icon2.setVisible(true);
	}
	
	public void setResolvedList(final OpenLayersSmartLayer smartLayer, final IssueIconRenderer renderer, Collection<? extends Poi> pois){
			list = new  ListView<Poi>() {
				
			
			@Override
			public ListViewItem<Poi> createListItem(Poi item) {
				return new ResolvedList_Item(item, renderer,smartLayer, decorated_panel);
			}
		};
		resolvedPanel.add(list);
		Comparator<Poi> ratingCompare = new IssueDateComparator();
		TreeSet<Poi> ratingSortedIssues = new TreeSet<Poi>(ratingCompare);
		ratingSortedIssues.addAll(pois);
		
		int count = 0;
		final ArrayList<Poi> filteredRatingIssues = new ArrayList<Poi>();
		for (final Poi i : ratingSortedIssues) {
			if (!i.isDeleted() && i.isMarked())
				filteredRatingIssues.add(i);
		}
		list.updateData(filteredRatingIssues);
		resolvedCount = filteredRatingIssues.size();
		
	}
	
	public int getResolvedCount(){
		return resolvedCount;
	}
}
