package rsb;

/**
 *
 * @author cunger
 */
public class BasicCommunication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RSBException, InterruptedException {
               
        String scope = "/test";

        Factory factory = Factory.getInstance();
        
        Informer<Object> informer = factory.createInformer(scope);
        informer.activate();
        
        Listener listener = factory.createListener(scope);       
        listener.activate();
        listener.addHandler(new TestEventListener(),true);

        informer.send("Hello world!");
        
        informer.deactivate();
        listener.deactivate();
        
        System.out.println("Done.");
    }
    
}
