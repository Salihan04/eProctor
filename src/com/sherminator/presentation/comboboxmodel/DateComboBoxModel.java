package com.sherminator.presentation.comboboxmodel;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 * @author Soe Lynn
 *
 */

public class DateComboBoxModel implements ComboBoxModel {

	private String selectedValue;
	private List<String> values;
	private DateFormat dateFormat;

	public DateComboBoxModel(Date startDate, Date endDate) {
		this(startDate, endDate, new SimpleDateFormat("E, dd MMM yyyy"));		
	}

	public DateComboBoxModel(Date startDate, Date endDate, DateFormat dateFormat) {
		values = new ArrayList<String>();
		this.dateFormat = dateFormat;
		
		Calendar cal = Calendar.getInstance();
		Date date;

		if(startDate.compareTo(cal.getTime()) != 1) {
			cal.add(Calendar.DATE, 1);
			date = new Date(cal.getTime().getTime());
		}
		else {
			date = startDate;
		}

		while(date.compareTo(endDate) != 1) {
			java.util.Date utilDate = new java.util.Date(date.getTime());
			String dateString = dateFormat.format(utilDate);
			values.add(dateString);		

			cal.setTime(date);
			cal.add(Calendar.DATE, 1);
			date = new Date(cal.getTime().getTime());
		}
		
		// initialize selected value
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
	public void setSelectedItem(Object selectedItem) {
		this.selectedValue = (String) selectedItem;
	}

	@Override
	public String getSelectedItem() {
		return selectedValue;
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		
		
	}

}
