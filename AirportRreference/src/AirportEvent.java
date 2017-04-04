//YOUR NAME HERE

public class AirportEvent extends Event {
    public static final int PLANE_ARRIVES = 0;
    public static final int PLANE_LANDED = 1;
    public static final int PLANE_DEPARTS = 2;
    public static final int PLANE_FLIES =3;
    Airplane m_Airplane;

    AirportEvent(double delay, EventHandler handler, int eventType, Airplane airplanex) {
        super(delay, handler, eventType);
        m_Airplane = airplanex;
    }
}
