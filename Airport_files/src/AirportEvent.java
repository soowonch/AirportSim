// Author: Xiaoyang MENG

public class AirportEvent extends Event {
    public static final int PLANE_ARRIVES = 0;
    public static final int PLANE_LANDED = 1;
    public static final int PLANE_DEPARTS = 2;

    private Airplane m_airplane;

    // Add airplane as the sender of the event
    public AirportEvent(double delay, EventHandler handler, int eventType, Airplane airplane) {
        super(delay, handler, eventType);
        m_airplane = airplane;
    }

    public Airplane getAirplane() {
        return m_airplane;
    }
}
