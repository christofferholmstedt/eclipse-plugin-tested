package se.chho.tested.helpers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * Helper class to make it easier to add "Passed Test" markers.
 * A marker within Eclipse is e.g. a line number marker or text marker.
 * All problem markers gets automatically added to the problems tab within Eclipse.
 * 
 * @author Christoffer Holmstedt
 *
 */
public class MarkerHelper {
	
	// The passed test marker ID
	public static final String PASSED_TEST_MARKER_ID = "se.chho.marker.passedTest"; //$NON-NLS-1$

	static public void addMarker(IFile file, int linenumber, String message)
	{
		try {
			  IMarker marker = file.createMarker(MarkerHelper.PASSED_TEST_MARKER_ID);
			  marker.setAttribute(IMarker.LINE_NUMBER, linenumber);
			  marker.setAttribute(IMarker.MESSAGE, message);
			  marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
		  } catch (Exception e)
		  {
			  e.printStackTrace();
		  }
	}
	
	/***
	 * Source: http://help.eclipse.org/juno/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Fguide%2FresAdv_markers.htm
	 * @param file
	 * @param linenumber
	 * @param markerType
	 * @return
	 */
	static public IMarker[] findMarkersByLineNumber(IFile file, int linenumber, String markerType)
	{
		IMarker[] markers = null;
		try {
			   int depth = IResource.DEPTH_INFINITE;
			   try {
				   markers = file.findMarkers(markerType, false, depth);
			   } catch (CoreException e) {
			      // something went wrong
			   }
		  } catch (Exception e)
		  {
			  e.printStackTrace();
		  }
		return markers;
	}
}
