package de.citec.sc.gardengnome.service.rest;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 *
 * @author cunger
 */
public class RestletApp extends Application {
    
	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 */
	@Override
	public Restlet createInboundRoot() {
            
            Router router = new Router(getContext());

            router.attach("/status",StatusRequest.class);
            router.attach("/query",QueryRequest.class);
            router.attach("/write",WriteRequest.class);
            router.attach("/retrieve",RetrieveRequest.class);
                
            return router;
        }
}