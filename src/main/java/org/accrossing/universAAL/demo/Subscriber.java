/**
 * 
 */
package org.accrossing.universAAL.demo;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;

/**
 * @author amedrano
 *
 */
public class Subscriber extends ContextSubscriber {

	public Subscriber(ModuleContext connectingModule, ContextEventPattern[] initialSubscriptions) {
		super(connectingModule, initialSubscriptions);
	}

	/* (non-Javadoc)
	 * @see org.universAAL.middleware.context.ContextSubscriber#communicationChannelBroken()
	 */
	@Override
	public void communicationChannelBroken() {
		// This is called when the ContextBus is dying, for whatever reason...

	}

	/* (non-Javadoc)
	 * @see org.universAAL.middleware.context.ContextSubscriber#handleContextEvent(org.universAAL.middleware.context.ContextEvent)
	 */
	@Override
	public void handleContextEvent(ContextEvent event) {
		// This is called everytime a context event is received AND matched.
		
		//Do something with the event, for example print the value:
		System.out.println(event.getRDFObject().toString());

	}

}
