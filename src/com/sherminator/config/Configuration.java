/*
 * This is the center of all the UI configuration.
 * Need to enforced on every UI.
 * Currently used in TakeExamUI and ProctorHome_UI.
 * Future implemention can be done for storing all the configuration to text file. 
 * 
 * @author	Soe Lynn
 * @version	1.0
 * 
 */

package com.sherminator.config;

import java.awt.Color;
import java.awt.Font;

public class Configuration {

	public static final int START_TIME = 8;
	public static final int END_TIME = 18;
	public static final Font IMPORTANT_TEXT_FONT = new Font("Tahoma", Font.BOLD, 30);
	public static Font QUESTION_TEXT_FONT = new Font("Tahoma", Font.BOLD, 24);
	public static Font ANSWER_TEXT_FONT = new Font("Tahoma", Font.PLAIN, 20);
	public static Font MATRIC_CARD_TEXT_FONT = new Font("Tahoma", Font.PLAIN, 12);
	
	public static Color HEADER_BG_COLOR = new Color(14, 115, 156);
	public static Color HEADER_TEXT_COLOR = Color.WHITE;
	public static Color CONTENT_BG_COLOR = Color.WHITE;
	public static Color CONTENT_TEXT_COLOR = new Color(14, 115, 156);
	public static Color BUTTON_NORMAL_BG_COLOR = Color.WHITE;
	public static Color BUTTON_NORMAL_TEXT_COLOR = new Color(14, 115, 156);
	public static Color BUTTON_BORDER_COLOR = new Color(14, 115, 156);
	public static Color BUTTON_HOVER_BG_COLOR = new Color(14, 115, 156);
	public static Color BUTTON_HOVER_TEXT_COLOR = Color.WHITE;
	
}
