package airport_event_simulation;

/**
 * 
 * @author Cong Du
 *
 */
//handler of AirportEvent is Airport, see Airport.handle(Event);
public class AirportEvent extends Event{
	public static final int PLANE_ARRIVES = 0;
	public static final int PLANE_LANDED = 1;
	public static final int PLANE_DEPARTS_READY = 2;
	public static final int PLANE_DEPARTS_FINISHED = 3;
	
	private Airplane EventSubject;

	AirportEvent(double delay, EventHandler handler, int eventType, Airplane airplane) {
		super(delay,handler,eventType);
		EventSubject = airplane;
	}
	
	public Airplane getSubject() {
		return EventSubject;
	}

	
}
