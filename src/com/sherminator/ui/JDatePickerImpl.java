package com.sherminator.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sherminator.ui.DateModel;
import com.sherminator.ui.JDatePanel;
import com.sherminator.ui.JDatePicker;

public class JDatePickerImpl extends JPanel implements JDatePicker {

	private static final long serialVersionUID = 2814777654384974503L;
	
	private Popup popup;
	private JFormattedTextField formattedTextField;
	private JButton button;
	
	private JDatePanelImpl datePanel;
	private InternalEventHandler internalEventHandler;

	public JDatePickerImpl(JDatePanelImpl dateInstantPanel) {
		this(dateInstantPanel, null);
	}
	
	/**
	 * You are able to set the format of the date being displayed on the label.
	 */
	public JDatePickerImpl(JDatePanelImpl datePanel, JFormattedTextField.AbstractFormatter formatter) {
		this.datePanel = datePanel;

		//Initialise Variables
		popup = null;
		datePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		internalEventHandler = new InternalEventHandler();

		//Create Layout
		FlowLayout layout = new FlowLayout();
        setLayout(layout);

        //Create and Add Components
		//Add and Configure TextField
        formattedTextField = new JFormattedTextField((formatter!=null) ? formatter : createDefaultFormatter());
        
		DateModel<?> model = datePanel.getModel();
		setTextFieldValue(formattedTextField, model.getYear(), model.getMonth(), model.getDay(), model.isSelected());
		formattedTextField.setEditable(false);		
		add(formattedTextField);

		//Add and Configure Button
		button = new JButton("Select a day");
		add(button);
		
		layout.setHgap(10);              
	    layout.setVgap(30);
		
		//Do layout formatting
		int h = 20;
		int w = 120;
		button.setPreferredSize(new Dimension(w, h));
		formattedTextField.setPreferredSize(new Dimension(w-h-1, h));

		//Add event listeners
		addHierarchyBoundsListener(internalEventHandler);
//TODO		addAncestorListener(listener)
		button.addActionListener(internalEventHandler);
		formattedTextField.addPropertyChangeListener("value", internalEventHandler);
		datePanel.addActionListener(internalEventHandler);
		datePanel.getModel().addChangeListener(internalEventHandler);
	}	
	
	protected JFormattedTextField.AbstractFormatter createDefaultFormatter() {
		return new DateComponentFormatter();
	}

	public void addActionListener(ActionListener actionListener) {
		datePanel.addActionListener(actionListener);
	}

	public void removeActionListener(ActionListener actionListener) {
		datePanel.removeActionListener(actionListener);
	}

	public void setI18nStrings(Properties i18nStrings) {
		datePanel.setI18nStrings(i18nStrings);
	}
	
	public Properties getI18nStrings() {
		return datePanel.getI18nStrings();
	}

	public DateModel<?> getModel() {
		return datePanel.getModel();
	}
	
	/* (non-Javadoc)
	 * @see net.sourceforge.jdatepicker.JDatePicker#setTextEditable(boolean)
	 */
	public void setTextEditable(boolean editable) {
		formattedTextField.setEditable(editable);
	}
	
	/* (non-Javadoc)
	 * @see net.sourceforge.jdatepicker.JDatePicker#isTextEditable()
	 */
	public boolean isTextEditable() {
		return formattedTextField.isEditable();
	}
	
	/* (non-Javadoc)
	 * @see net.sourceforge.jdatepicker.JDatePicker#setButtonFocusable(boolean)
	 */
	public void setButtonFocusable(boolean focusable) {
		button.setFocusable(focusable);
	}
	
	/* (non-Javadoc)
	 * @see net.sourceforge.jdatepicker.JDatePicker#getButtonFocusable()
	 */
	public boolean getButtonFocusable() {
		return button.isFocusable();
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.jdatepicker.JDatePicker#getJDateInstantPanel()
	 */
	public JDatePanel getJDateInstantPanel() {
		return datePanel;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.jdatepicker.JDatePicker#getJFormattedTextField()
	 */
	public JFormattedTextField getJFormattedTextField() {
		return formattedTextField;
	}

	/**
	 * Called internally to popup the dates.
	 */
	private void showPopup() {
		if (popup == null){
			PopupFactory fac = new PopupFactory();
			Point xy = getLocationOnScreen();
			datePanel.setVisible(true); 
			popup = fac.getPopup(this, datePanel, (int) xy.getX(), (int) (xy.getY()+this.getHeight()));
			popup.show();
		}
	}
	
	/**
	 * Called internally to hide the popup dates. 
	 */
	private void hidePopup() {
		if (popup != null) {
			popup.hide();
			popup = null;
		}
	}

	/**
	 * This internal class hides the public event methods from the outside 
	 */
	private class InternalEventHandler implements ActionListener, HierarchyBoundsListener, ChangeListener, PropertyChangeListener {

		public void ancestorMoved(HierarchyEvent arg0) {
			hidePopup();
		}

		public void ancestorResized(HierarchyEvent arg0) {
			hidePopup();
		}

		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == button){
				if (popup == null) {
					showPopup();
				}
				else {
					hidePopup();
				}
			} 
			else if (arg0.getSource() == datePanel){
				hidePopup();
			}
		}

		public void stateChanged(ChangeEvent arg0) {
			if (arg0.getSource() == datePanel.getModel()) {
				DateModel<?> model = datePanel.getModel();
				setTextFieldValue(formattedTextField, model.getYear(), model.getMonth(), model.getDay(), model.isSelected());
			}
		}

		public void propertyChange(PropertyChangeEvent evt) {
			if (formattedTextField.isEditable() && formattedTextField.getValue() != null) {
				Calendar value = (Calendar)formattedTextField.getValue();
				datePanel.getModel().setDate(value.get(Calendar.YEAR), value.get(Calendar.MONTH), value.get(Calendar.DATE));
				datePanel.getModel().setSelected(true);
			}
		}
		
	}

	public boolean isDoubleClickAction() {
		return datePanel.isDoubleClickAction();
	}

	public boolean isShowYearButtons() {
		return datePanel.isShowYearButtons();
	}

	public void setDoubleClickAction(boolean doubleClickAction) {
		datePanel.setDoubleClickAction(doubleClickAction);
	}

	public void setShowYearButtons(boolean showYearButtons) {
		datePanel.setShowYearButtons(showYearButtons);
	}
	
	private void setTextFieldValue(JFormattedTextField textField, int year, int month, int day, boolean isSelected) {
		if (!isSelected) {
			textField.setValue(null);
		}
		else {
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day, 0, 0, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			textField.setValue(calendar);
		}
	}
	/*public static void main(String args[]){ 
		SqlDateModel model = new SqlDateModel();
		JDatePanelImpl dPanel =new JDatePanelImpl(model);
		JFormattedTextField.AbstractFormatter formatter = new DateComponentFormatter(); 
		JDatePickerImpl dPicker = new JDatePickerImpl(dPanel, formatter);
		JFrame f = new JFrame();
		f.getContentPane().add(dPicker);
		f.pack();
		f.setVisible(true);
	}*/
	
}
