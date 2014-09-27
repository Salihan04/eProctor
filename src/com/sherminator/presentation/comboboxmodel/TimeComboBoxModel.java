/*
 * @author	Soe Lynn
 * @version	1.0
 * 
 */

package com.sherminator.presentation.comboboxmodel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

public class TimeComboBoxModel implements ComboBoxModel {

	private String selectedValue;
	private List<String> values;
	private DateFormat dateFormat;
	
	
	/**
	 * Time is in hours. It must be in 24 hours format (0-23)
	 * @param start
	 * @param end
	 */
	public TimeComboBoxModel(int startTime, int endTime) {
		Calendar calendar = Calendar.getInstance();
		dateFormat = new SimpleDateFormat("hh:mm a");
		values = new ArrayList<String>();
		
		calendar.set(Calendar.MINUTE, 0);
		for(int i=startTime;i<=endTime;i++) {
			calendar.set(Calendar.HOUR_OF_DAY, i);
			String am_pm_string = dateFormat.format(calendar.getTime());
			values.add(am_pm_string);
		}
		
		// initialize selected index
		if(getSize() > 0) {
			selectedValue = values.get(0);
		}
	}
	
	@Override
	public int getSize() {
		return values.size();
	}

	@Override
	public String getElementAt(int index) {
		return values.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
				
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
				
	}

	@Override
	public void setSelectedItem(Object selectedItem) {
		selectedValue = (String) selectedItem;
	}

	@Override
	public String getSelectedItem() {
		return selectedValue;
	}

}
