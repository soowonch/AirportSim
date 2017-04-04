//YOUR NAME HERE
import java.util.TreeSet;
public class Airport implements EventHandler {

    //TODO add landing and takeoff queues, random variables

    private int m_inTheAir;
    private int m_onTheGround;
    private int m_aboutToFly;
    private int m_arrivingPlanes;
    private int m_departingPlanes;
    private int m_arrivingPassengers;
    private int m_departingPassengers;


    private boolean m_freeToLand;


    private double m_flightTime;
    private double m_runwayTimeToLand;
    private double m_requiredTimeOnGround;
    private double m_runwayTimeToDepart;
    private double m_circlingtime;

    private String m_airportName;

    private TreeSet<AirportEvent> m_eventlist;
    //private TreeSet<AirportEvent> m_departureeventlist;

    public static Airport[] m_allAirports = new Airport[5];

    public double [][] m_distance = new double[][]{
            //LAX   ATL         SEA         LHR         HKG
            {0,     1942.17,    954.95,     5442.74,    7247.45},
            {1942.17,   0,      2177.79,    4200.74,    8386.7},
            {954.95, 2177.79,   0,          4785.62,    6488.05},
            {5442.74,4200.74,   4785.62,    0,          5984.22},
            {7247.45,8386.7,    6488.05,    5984.22,    0}

    };

    public Airport(String name, double runwayTimeToLand, double requiredTimeOnGround, double runwayTimeToDepart) {
        m_airportName = name;
        m_aboutToFly =0;
        m_inTheAir =  0;
        m_onTheGround = 0;
        m_arrivingPassengers =0;
        m_departingPassengers =0;
        m_freeToLand = true;
        m_runwayTimeToLand = runwayTimeToLand;
        m_requiredTimeOnGround = requiredTimeOnGround;
        m_runwayTimeToDepart = runwayTimeToDepart;
        m_circlingtime = 0;
        m_eventlist = new TreeSet<AirportEvent>();
        m_arrivingPlanes =0;
        m_departingPlanes =0;

    }

    public String getName() {
        return m_airportName;
    }
    public int getarrivingPassengers(){return m_arrivingPassengers;}
    public int getdepartingPassengers(){return m_departingPassengers;}
    public double getCirclingTime(){return m_circlingtime;}
    public int getdepartingPlanes(){return m_departingPlanes;}
    public int getarrivingPlanes(){return m_arrivingPlanes;}
    public double getaveCirclingTime(){return m_circlingtime/m_arrivingPlanes;}

    public void handle(Event event) {
        AirportEvent airEvent = (AirportEvent) event;
        //System.out.println("Used once in Airport");
        switch (airEvent.getType()) {
            case AirportEvent.PLANE_ARRIVES:
                //m_inTheAir++;
                m_arrivingPassengers+=airEvent.m_Airplane.getNumberPassengers();
                System.out.println(Simulator.getCurrentTime() + ": Plane " + airEvent.m_Airplane.getName() + " arrived at "+this.getName()+" airport");
                if (m_freeToLand) {
                    AirportEvent landedEvent = new AirportEvent(m_runwayTimeToLand, airEvent.getHandler(), AirportEvent.PLANE_LANDED, airEvent.m_Airplane);
                    m_freeToLand = false;
                    Simulator.schedule(landedEvent);
                } else {
                    m_eventlist.add(airEvent);
                    airEvent.m_Airplane.setTimestamp(Simulator.getCurrentTime());
                }
                break;

            case AirportEvent.PLANE_DEPARTS:
               // m_onTheGround--;
               // m_aboutToFly++;
                System.out.println(Simulator.getCurrentTime() + ": Plane " + airEvent.m_Airplane.getName() + " is ready to depart from "+ this.getName() +" airport");
                airEvent.m_Airplane.setNumberPassengers();
                airEvent.m_Airplane.setSpeed();
                if (m_freeToLand) {
                    AirportEvent departureEvent = new AirportEvent(m_runwayTimeToDepart, airEvent.getHandler(), AirportEvent.PLANE_FLIES, airEvent.m_Airplane);
                    m_freeToLand = false;
                    Simulator.schedule(departureEvent);
                } else {
                    m_eventlist.add(airEvent);
                }
                break;

            case AirportEvent.PLANE_LANDED:
               // m_inTheAir--;
                m_arrivingPlanes ++;
                m_arrivingPassengers += airEvent.m_Airplane.getNumberPassengers();
                if (airEvent.m_Airplane.getTimestamp()!=0){
                    m_circlingtime += Simulator.getCurrentTime()-airEvent.m_Airplane.getTimestamp()-this.m_runwayTimeToLand;
                    airEvent.m_Airplane.setTimestamp(0);
                }
                System.out.println(Simulator.getCurrentTime() + ": Plane " + airEvent.m_Airplane.getName() + " lands at " +this.getName()+" airport");
                AirportEvent departureEvent = new AirportEvent(m_requiredTimeOnGround, this, AirportEvent.PLANE_DEPARTS, airEvent.m_Airplane);
                Simulator.schedule(departureEvent);
                m_freeToLand = true;
                handle_eventlist(m_eventlist);
                break;

            case AirportEvent.PLANE_FLIES:
                //m_aboutToFly--;
                m_departingPlanes ++;
                m_departingPassengers += airEvent.m_Airplane.getNumberPassengers();
                Airport nextAirport = Airport_Chosen();
                double distance = find_distance(nextAirport);
               // System.out.println("The distance is " + distance +"; The speed of the airplane is " +airEvent.m_Airplane.getSpeed()+" ;");
                m_flightTime = distance/(airEvent.m_Airplane.getSpeed());
                System.out.println(Simulator.getCurrentTime() + ": Plane " + airEvent.m_Airplane.getName() + " flies from " + this.getName()+" to " + nextAirport.getName());
                AirportEvent arrivalEvent = new AirportEvent(m_flightTime, nextAirport, AirportEvent.PLANE_ARRIVES, airEvent.m_Airplane);
                Simulator.schedule(arrivalEvent);
                m_freeToLand = true;
                handle_eventlist(m_eventlist);
                break;
          }
         }

    public void handle_eventlist( TreeSet<AirportEvent> m_eventlist){
           if (!m_eventlist.isEmpty()) {
               if (m_eventlist.first().getType() == AirportEvent.PLANE_ARRIVES) {
                   AirportEvent ev_tmp = m_eventlist.pollFirst();
                   AirportEvent landingEvent = new AirportEvent(m_runwayTimeToLand, ev_tmp.getHandler(), AirportEvent.PLANE_LANDED, ev_tmp.m_Airplane);
                   Simulator.schedule(landingEvent);
                   m_freeToLand = false;
               }
           }
           if (!m_eventlist.isEmpty()) {
               if (m_eventlist.first().getType() == AirportEvent.PLANE_DEPARTS) {
                   AirportEvent ev_tmp = m_eventlist.pollFirst();
                   AirportEvent departingEvent = new AirportEvent(m_runwayTimeToDepart, ev_tmp.getHandler(), AirportEvent.PLANE_FLIES, ev_tmp.m_Airplane);
                   Simulator.schedule(departingEvent);
                   m_freeToLand = false;
               }
           }
    }

    public Airport Airport_Chosen(){
        int num_airports =5;
        int i;
       do {
            i = (int) (Math.random() * num_airports);
        } while(this.getName()== this.m_allAirports[i].getName());
        //System.out.println(this.m_allAirports[i].getName());
        return this.m_allAirports[i];
    }

    public double find_distance(Airport nextAirport){
        int m,n;
        for (m=0;m<5;m++){
            if ( this.m_allAirports[m].getName()==this.getName()) {
                //System.out.println("find one airport " + m);
                break;
            }
        }
        for (n=0;n<5;n++){
            if ( this.m_allAirports[n].getName()==nextAirport.getName()) {
                //System.out.println("find one airport " + n);
                break;
            }
        }
        return m_distance[m][n];
    }
}


