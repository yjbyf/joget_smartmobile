package com.joget.smartmobile.client.panel;

import java.util.Date;

import com.google.gwt.event.shared.HandlerRegistration;
import com.joget.smartmobile.client.listgrid.record.CountryRecord;
import com.smartgwt.mobile.client.core.Rectangle;
import com.smartgwt.mobile.client.data.Record;
import com.smartgwt.mobile.client.data.RecordList;
import com.smartgwt.mobile.client.types.TableMode;
import com.smartgwt.mobile.client.widgets.Popover;
import com.smartgwt.mobile.client.widgets.ScrollablePanel;
import com.smartgwt.mobile.client.widgets.grid.ListGridField;
import com.smartgwt.mobile.client.widgets.tableview.TableView;
import com.smartgwt.mobile.client.widgets.tableview.events.RecordNavigationClickEvent;
import com.smartgwt.mobile.client.widgets.tableview.events.RecordNavigationClickHandler;

public class TestGridPanel extends ScrollablePanel {
	private Popover popover;
    private ScrollablePanel bioPanel;
    private HandlerRegistration recordNavigationClickRegistration = null;
    
	public TestGridPanel(String title) {
		super(title);
		this.setWidth("100%");
		this.setHeight("100%");
		final TableView countryGrid=  new TableView();
		//ListGrid countryGrid = new ListGrid("countryName");
		countryGrid.setWidth("100%");
		countryGrid.setHeight("100%");
		ListGridField countryCodeField = new ListGridField("countryCode");

		ListGridField nameField = new ListGridField("countryName");
		ListGridField capitalField = new ListGridField("capital");
		ListGridField continentField = new ListGridField("continent");
		countryGrid.setFields(countryCodeField, nameField, capitalField, continentField);
		countryGrid.setTitleField(nameField.getName());
		countryGrid.setTableMode(TableMode.GROUPED);
		countryGrid.setShowNavigation(true);
		RecordList recordList = new RecordList();
		recordList.add(new CountryRecord(
				"North America",
				"United States",
				"US",
				9631420,
				298444215,
				12360,
				new Date(1776 - 1900, 6, 4),
				"federal republic",
				2,
				"Washington, DC",
				true,
				"http://en.wikipedia.org/wiki/United_states",
				"Britain's American colonies broke with the mother country in 1776 and were recognized as the new nation of the United States of America following the Treaty of Paris in 1783. During the 19th and 20th centuries, 37 new states were added to the original 13 as the nation expanded across the North American continent and acquired a number of overseas possessions. The two most traumatic experiences in the nation's history were the Civil War (1861-65) and the Great Depression of the 1930s. Buoyed by victories in World Wars I and II and the end of the Cold War in 1991, the US remains the world's most powerful nation state. The economy is marked by steady growth, low unemployment and inflation, and rapid advances in technology."));
		recordList.add(new CountryRecord(
				"Asia",
				"China",
				"CH",
				9596960,
				1313973713,
				8859,
				null,
				"Communist state",
				0,
				"Beijing",
				false,
				"http://en.wikipedia.org/wiki/China",
				"For centuries China stood as a leading civilization, outpacing the rest of the world in the arts and sciences, but in the 19th and early 20th centuries, the country was beset by civil unrest, major famines, military defeats, and foreign occupation. After World War II, the Communists under MAO Zedong established an autocratic socialist system that, while ensuring China's sovereignty, imposed strict controls over everyday life and cost the lives of tens of millions of people. After 1978, his successor DENG Xiaoping and other leaders focused on market-oriented economic development and by 2000 output had quadrupled. For much of the population, living standards have improved dramatically and the room for personal choice has expanded, yet political controls remain tight."));
		
		countryGrid.setData(recordList);
		//countryGrid.addMember(new ToolStrip());
		
		//this.addMember(new ToolStrip());
		this.addMember(countryGrid);
		
		bioPanel = new ScrollablePanel("Bio");
		popover = new Popover("Bio", bioPanel);
		recordNavigationClickRegistration = countryGrid.addRecordNavigationClickHandler(new RecordNavigationClickHandler() {
	            @Override
	            public void onRecordNavigationClick(RecordNavigationClickEvent event) {
	                final Record record = event.getRecord();
	                final Rectangle clientRect = countryGrid.getRowClientBounds(record);
	                if (clientRect != null) {
	                    popover.setTitle(record.getAttribute("countryName"));
	                    bioPanel.setContents("<div style='padding:5px'><b>continent:</b>" + record.getAttribute("continent") + "</div>"+
	                    		"<div style='padding:5px'><b>capital:</b>" + record.getAttribute("capital") + "</div>" +
	                    				"<table border='1' width='100%'><tr><td>country</td><tr><td>china</td></tr></table>");
	                    popover.showForArea(clientRect);
	                }

	                // Alternatively, using TableView.getRowTop():
	                //final Integer rowTop = presidentsTable.getRowTop(record);
	                //if (rowTop != null) {
	                //    final int clientY = rowTop.intValue() + presidentsTable.getElement().getOffsetTop() - getScrollTop() + getClientBounds().getTop();
	                //    popover.setTitle(record.getAttribute(FIRST_NAME_PROPERTY) + " " + record.getAttribute(LAST_NAME_PROPERTY));
	                //    bioPanel.setContents("<div style='padding:5px'>" + record.getAttribute(BIO_PROPERTY) + "</div>");
	                //    popover.showForArea(0, clientY, Window.getClientWidth(), presidentsTable.getCellHeight());
	                //}
	            }
	        });
		

	}
	
	@Override
    public void destroy() {
        if (recordNavigationClickRegistration != null) {
            recordNavigationClickRegistration.removeHandler();
            recordNavigationClickRegistration = null;
        }
        popover.destroy();
        super.destroy();
    }

}
