package rsb;

/**
 *
 * @author cunger
 */
public class TestEventListener extends AbstractEventHandler {
 
    @Override
    public void handleEvent(final Event event) {

        System.out.println("Test listener heard: " + event.getData());
    }

}
