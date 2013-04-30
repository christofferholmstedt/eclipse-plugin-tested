package se.chho.tested.helpers;

import java.util.ArrayList;

public class ParameterParserHelper {
	
	public static ArrayList<Object> parseParameters(String string)
	{
		ArrayList<Object> tempArrayList = new ArrayList<Object>();	
		String tempString = (String) string.subSequence(string.indexOf("(")+1, string.indexOf(")"));
	
		// Step 1: First check, no input at all.
		if (tempString.isEmpty() && tempString != null)
		{
			return tempArrayList;
		}

		if (tempString.matches("(.*)(\")(.*)"))
		{
			tempString = tempString.substring(1, tempString.length()-1);
			tempArrayList.add(tempString);
		} else {
			
			// Split all commas
			String parameters[]	= tempString.split(",");
			
			// Loop through all values and convert to integers
			for (int i = 0; i < parameters.length; i++ )
			{
				try {
					tempArrayList.add(Integer.parseInt(parameters[i]));
				} catch (NumberFormatException nfe)	
				{
					// Pass
				}
			}
		}
		
		return tempArrayList; 
	}
}
