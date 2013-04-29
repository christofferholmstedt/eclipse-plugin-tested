package se.chho.tested.analyser;

import java.util.ArrayList;

import se.chho.tested.core.FoundMethod;

public interface AnalyserManagerObservableInterface {
	
	public ArrayList<FoundMethod> getFoundMethods();
	public void attach(AnalyserObserverInterface Observer);
	public void detach(AnalyserObserverInterface Observer);
	public void runAllAnalysers();
}
