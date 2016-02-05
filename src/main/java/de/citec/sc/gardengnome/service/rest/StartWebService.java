package de.citec.sc.gardengnome.service.rest;

import org.json.simple.parser.ParseException;
import org.restlet.Component;
import org.restlet.data.Protocol;

public class StartWebService {

  public static void main(String[]args) throws IllegalArgumentException, ParseException, Exception {
      
        Component component = new Component();

        component.getServers().add(Protocol.HTTP,80);
	component.getDefaultHost().attach("/kognihome/userprofiles",new RestletApp());  
        component.start();
  }

}
