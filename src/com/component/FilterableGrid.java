package com.component;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

public class FilterableGrid extends Grid implements ValueChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BeanItemContainer<?> container;
	IndexedContainer containers;
	public FilterableGrid(final BeanItemContainer<?> container) {
		// TODO Auto-generated constructor stub
		super();
		this.container = container;
		this.setSizeFull();
		this.setSelectionMode(SelectionMode.MULTI);
		this.setContainerDataSource(container);
		HeaderRow filterRow = this.appendHeaderRow();
		for (final Object pid : this.getContainerDataSource()
				.getContainerPropertyIds()) {
			HeaderCell cell = filterRow.getCell(pid);

			// Have an input field to use for filter
			TextField filterField = new TextField();
			filterField.setColumns(8);
			filterField.addValueChangeListener(new ValueChangeListener() {

				private static final long serialVersionUID = 1L;

				@Override
				public void valueChange(ValueChangeEvent event) {
					// TODO Auto-generated method stub
					container.removeContainerFilters(pid);
					String change = event.getProperty().getValue().toString();
					// (Re)create the filter if necessary
					if (!change.isEmpty())
						container.addContainerFilter(new SimpleStringFilter(
								pid, change, true, false));
				}
			});
			cell.setComponent(filterField);
		}

	}

	
	
	public FilterableGrid() {
		// TODO Auto-generated constructor stub
		super();
	containers = (IndexedContainer)this.getContainerDataSource();
		this.setSizeFull();
		this.setSelectionMode(SelectionMode.MULTI);
		
		HeaderRow filterRow = this.appendHeaderRow();
		for (final Object pid : this.getContainerDataSource()
				.getContainerPropertyIds()) {
			HeaderCell cell = filterRow.getCell(pid);

			// Have an input field to use for filter
			TextField filterField = new TextField();
			filterField.setColumns(8);
			filterField.addValueChangeListener(new ValueChangeListener() {

				private static final long serialVersionUID = 1L;

				@Override
				public void valueChange(ValueChangeEvent event) {
					// TODO Auto-generated method stub
					containers.removeContainerFilters(pid);
					String change = event.getProperty().getValue().toString();
					// (Re)create the filter if necessary
					if (!change.isEmpty())
						containers.addContainerFilter(new SimpleStringFilter(
								pid, change, true, false));
				}
			});
			cell.setComponent(filterField);
	
		
		}

	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
