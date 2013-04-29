package se.chho.tested.core;

import java.util.ArrayList;


public interface AnalyserManagerObservableInterface {
	
	public ArrayList<FoundMethod> getFoundMethods();
	public void attach(AnalyserObserverInterface Observer);
	public void detach(AnalyserObserverInterface Observer);
	public void runAllAnalysers();
}
