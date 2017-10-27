/**
 * 
 */
package org.accrossing.universAAL.demo;

import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.rdf.Resource;

/**
 * @author amedrano
 *
 */
public class Worker implements Runnable {

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// Normal working of the publisher, looking for things to be publisher
		try {
			// looking for something to publish
			// loooooking
			// ...
			// this takes some time
			// zzzZZZZZ
			Thread.sleep(1000);
		} catch (InterruptedException e1) {	}
		
		
		// when you found something and want to publish something you need to create the event
		Resource subject = new Resource("Juan");
		String predicate = "http://www.someURI.org/someOnt.owl#someProperty";
		Object object = "value of the property";
		ContextEvent e = new ContextEvent(subject, predicate, object);
		// And then publish it
		ProjectActivator.myPublisher.publish(e);

	}

}
