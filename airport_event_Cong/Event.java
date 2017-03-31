package airport_event_simulation;

public class Event implements Comparable<Event>{
	static private int m_nextID = 0 ;
	
	private int m_eventID;
	private int m_eventType; //denote what type this event is;
	private double m_time; //when constructed, it's the time the event take,
	//after being schedule, it's the time event finished in simulation
	
	private EventHandler m_handler;
	
	Event() {
		m_eventID = m_nextID++;
	}
	
	Event(double delay, EventHandler handler, int eventType) {
		this();//assign new eventID;
		m_time = delay;
		m_handler = handler;
		m_eventType = eventType;
	}
	
	public int getId() {
		return m_eventID;
	}
	
	public double getTime() {
		return m_time;
	}
	
	public void setTime(double time) {
		m_time = time;
	}
	
	public EventHandler getHandler() {
		return m_handler;
	}
	
	public int getType() {return m_eventType;}
	
	public void setHandler(EventHandler handler) {
		m_handler = handler;
	}

	@Override
	public int compareTo(final Event ev) {
		int timeCmp = Double.compare(m_time, ev.getTime());
		if(timeCmp != 0) {
			return timeCmp;
		}
		else
			return Integer.compare(m_eventID, ev.getId());
	}
}
