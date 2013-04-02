package se.chho.tested;

import org.osgi.framework.BundleContext;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.jdt.junit.JUnitCore;

/**
 * The activator class controls the plug-in life cycle
 */
public class TestedPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "se.chho.tested"; //$NON-NLS-1$

	// The shared instance
	private static TestedPlugin plugin;
	
	/**
	 * The constructor
	 */
	public TestedPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		// Adds a TestRunListener to my plugin
		// JUnitCore.addTestRunListener(new TestedTestRunListener()); 
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static TestedPlugin getDefault() {
		return plugin;
	}

}
