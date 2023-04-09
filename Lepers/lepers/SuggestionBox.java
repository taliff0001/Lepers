package lepers;

import java.text.DecimalFormat;

public class SuggestionBox {
	
	private static double avgWaitFull;
	
	private static double avgWaitSelf;
	
	private static double percentOfTimeUnoccupiedFull;
	
	private static double percentOfTimeUnoccupiedSelf;
	
	private static double finishTime;
	
	DecimalFormat df = new DecimalFormat("#.#");
	
	public String toString() {
		
		String suggestion = "";
		
		if(avgWaitFull<5 && avgWaitSelf<5)
			if((percentOfTimeUnoccupiedSelf+percentOfTimeUnoccupiedFull)/2 < 35) 
				suggestion = "NONE";
			else {				
				if(percentOfTimeUnoccupiedFull>50)
					suggestion += "Decrease Full Service Lanes  ";
				if(percentOfTimeUnoccupiedSelf>50)
					suggestion += "Decrease Self Service Lanes  ";
			}
		else {
			
			if(avgWaitFull>=5 && avgWaitSelf > 3)
				suggestion += "Increase Self Service Lanes  ";
			else if(avgWaitFull>=5)
				suggestion += "Increase Full Service Lanes  ";
			if(avgWaitSelf>=5 && avgWaitFull > 3)
				suggestion += "Increase Full Service Lanes  ";	
			else if(avgWaitSelf>=5)
				suggestion += "Increase Self Service Lanes  ";				
		}
		if(suggestion.equalsIgnoreCase(""))
			if(percentOfTimeUnoccupiedSelf<50 && percentOfTimeUnoccupiedSelf>40)
				suggestion += "You can try decreasing self service but no guarantees  ";
			else if(percentOfTimeUnoccupiedFull<50 && percentOfTimeUnoccupiedSelf>40)
				suggestion += "You can try decreasing self service but no guarantees  ";
			else
				suggestion = "NONE";
		

			
			
		return "\r  -------------------------------------------------------------------------------------------------------\r  |\r  |  Avg Wait FULL = "
				+ df.format(avgWaitFull) + " mins  |  Avg Wait SELF = " + df.format(avgWaitSelf)
				+ " mins\r  |  Time Unoccupied FULL = " + df.format(percentOfTimeUnoccupiedFull) + "%  |  Time Unoccupied SELF = "
				+ df.format(percentOfTimeUnoccupiedSelf) + "%" + "\r  |  Suggestions for optimizing lanes: " + suggestion
				+ "\r  |\r  -------------------------------------------------------------------------------------------------------";	
	}
	
	public static void calcPercentUnoccupied(int[] downtimeF, int[] downtimeS) {
		int sum = 0;
		double avg = 0;
		for(int i:downtimeF)
			sum += i;
		avg = sum/(double)downtimeF.length;
		percentOfTimeUnoccupiedFull = avg/finishTime * 100;
			sum = 0;
		for(int i:downtimeS)
			sum += i;
		avg = sum/(double)downtimeS.length;
		percentOfTimeUnoccupiedSelf = avg/finishTime * 100;
		
	}
	
	public static String getSuggestions() {
		String s = "";
		
		
		
		return s;
	}
	
	
	public static double getAvgWaitFull() {
		return avgWaitFull;
	}

	public static void setAvgWaitFull(double avgWaitFull) {
		SuggestionBox.avgWaitFull = avgWaitFull;
	}

	public static double getAvgWaitSelf() {
		return avgWaitSelf;
	}

	public static void setAvgWaitSelf(double avgWaitSelf) {
		SuggestionBox.avgWaitSelf = avgWaitSelf;
	}

	public static double getPercentOfTimeUnoccupiedFull() {
		return percentOfTimeUnoccupiedFull;
	}

	public static void setPercentOfTimeUnoccupiedFull(double percentOfTimeUnoccupiedFull) {
		SuggestionBox.percentOfTimeUnoccupiedFull = percentOfTimeUnoccupiedFull;
	}

	public static double getPercentOfTimeUnoccupiedSelf() {
		return percentOfTimeUnoccupiedSelf;
	}

	public static void setPercentOfTimeUnoccupiedSelf(double percentOfTimeUnoccupiedSelf) {
		SuggestionBox.percentOfTimeUnoccupiedSelf = percentOfTimeUnoccupiedSelf;
	}

	public static double getFinishTime() {
		return finishTime;
	}

	public static void setFinishTime(int finishTime) {
		SuggestionBox.finishTime = finishTime;
	}
	
}
