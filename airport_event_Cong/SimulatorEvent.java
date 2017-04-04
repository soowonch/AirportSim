package airport_event_simulation;

public class SimulatorEvent extends Event {
	public static final int STOP_EVENT = 0;
	
	//handler of SimulatorEvent is SimulatorEngine
	SimulatorEvent(double delay, EventHandler handler, int eventType) { 
		super(delay,handler,eventType);
	}

}
