package de.hszg.atocc.autoedit.nea2dea.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.hszg.atocc.core.util.ServiceUtils;
import de.hszg.atocc.core.util.WebService;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Hello Nea2Dea with Data: " + WebService.get("www.google.de"));
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye Nea2Dea!!");
	}

}
