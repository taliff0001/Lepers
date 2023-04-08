package lepers;

import java.io.PrintStream;

public class SuggestionBox {
	
	private static double avgWaitFull;
	
	private static double avgWaitSelf;
	
	private static double percentOfTimeUnoccupiedFull;
	
	private static double percentOfTimeUnoccupiedSelf;
	
	private static double finishTime;
	
	
	public String toString() {
		
		return "Avg Wait FULL = " + avgWaitFull + " SELF = " + avgWaitSelf
				+ "UnoccupiedPercentFull = " + percentOfTimeUnoccupiedFull + "UnoccupiedPercentSelf = "
				+ percentOfTimeUnoccupiedSelf;	
	}
	
	public static void calcPercentUnoccupied(int[] downtimeF, int[] downtimeS) {
		int sum = 0;
		double avg = 0;
		for(int i:downtimeF)
			sum += i;
		avg = sum/(double)downtimeF.length;
		percentOfTimeUnoccupiedFull = avg/finishTime;
		
		for(int i:downtimeS)
			sum += i;
		avg = sum/(double)downtimeS.length;
		percentOfTimeUnoccupiedSelf = avg/finishTime;
		
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
