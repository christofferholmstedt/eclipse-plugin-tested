package se.chho.tested.core;

public interface AnalyserManagerObservable {
	
	public FoundMethod[] getFoundMethods();
	public void attach(AnalyserObserver Observer);
	public void detach(AnalyserObserver Observer);
	public void runAllAnalysers();
}
