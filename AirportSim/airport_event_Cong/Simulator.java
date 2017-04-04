package airport_event_simulation;
/**
 * 
 * @author Cong Du
 *
 */
public class Simulator {
	private static SimulatorEngine instance = null;
	
	private static SimulatorEngine getSim() {
		if(instance == null) {
			instance = new SimulatorEngine();
		}
		return instance;
	}
	
	//the parameter of stopAt method means how long from now! 
	public static void stopAt(double time) {
		Event stopEvent = new SimulatorEvent(time,getSim(),SimulatorEvent.STOP_EVENT);
		schedule(stopEvent);
	}
	
	public static void run() {
		getSim().run();
	}
	
	public static String getCurrentTime() {
		double tm = getSim().getCurrentTime();
		int h = (int) (tm /60);
		int m = (int) (tm % 60);
		return Integer.toString(h)+" h "+Integer.toString(m) + " m";
	}
	
	public static double GetDoubleCurrentTime() {
		return getSim().getCurrentTime();
	}
	public static void schedule(Event event) {
		event.setTime(event.getTime()+getSim().getCurrentTime());
		getSim().schedule(event);
	}
}
