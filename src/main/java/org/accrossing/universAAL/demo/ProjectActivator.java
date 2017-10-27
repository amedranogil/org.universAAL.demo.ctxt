package org.accrossing.universAAL.demo;

import org.universAAL.middleware.container.ModuleActivator;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.rdf.Resource;

public class ProjectActivator implements ModuleActivator {

	static ModuleContext context;

	static ContextPublisher myPublisher;

	static ContextSubscriber mySubscriber;
	
	private Thread worker;

	public void start(ModuleContext ctxt) throws Exception {
		context = ctxt;
		LogUtils.logDebug(context, getClass(), "start", "Starting.");
		/*
		 * uAAL stuff
		 */

		/*
		 * Context Publisher
		 */
		// Create the ContextProvider that configures which types of events the
		// publisher can work with
		ContextProvider myProv = new ContextProvider();
		ContextEventPattern cep = new ContextEventPattern();
		cep.addRestriction(MergedRestriction.getFixedValueRestriction(ContextEvent.PROP_RDF_SUBJECT,
				new Resource("Juan")));
		myProv.setProvidedEvents(new ContextEventPattern[] {
			// Add the context event patterns the publisher will publish
				// any for the time being:
				new ContextEventPattern(),
				cep
		});
		// Set the type of provider:
		// gauge -> only produces events
		// controller -> provides events but it can be controlled through a service request
		// reasoner -> the events are based on logic elevation from other events
		myProv.setType(ContextProviderType.gauge);
		myPublisher = new DefaultContextPublisher(context, myProv);
		
		// initialise the worker to publish things
		worker = new Thread(new Worker());
		worker.start();
		
		
		/*
		 * Context Subscriber
		 */
		cep = new ContextEventPattern();
		cep.addRestriction(MergedRestriction.getFixedValueRestriction(ContextEvent.PROP_RDF_SUBJECT,
				new Resource("Juan")));
		ContextEventPattern[] subscriptions = new ContextEventPattern[] {
			//add the conext event pattern for the subscriber to react when matched
				// any for now...
				new ContextEventPattern(),
				cep
		};
		
		mySubscriber = new Subscriber(ctxt, subscriptions);
		
		LogUtils.logDebug(context, getClass(), "start", "Started.");
	}

	public void stop(ModuleContext ctxt) throws Exception {
		LogUtils.logDebug(context, getClass(), "stop", "Stopping.");
		/*
		 * close uAAL stuff
		 */
		worker.stop();
		worker = null;
		if (myPublisher != null) {
			myPublisher.close();
			myPublisher = null;
		}
		if (mySubscriber != null) {
			mySubscriber.close();
			mySubscriber = null;
		}
		LogUtils.logDebug(context, getClass(), "stop", "Stopped.");

	}

}
