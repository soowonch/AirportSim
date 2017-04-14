// Author: Xiaoyang MENG, Wuchang LI, Cong DU, Soowoo CHANG

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import java.util.Map;

public class Airport implements EventHandler {
    // Landing queue which holds all the airplanes that arrived at the airport waiting to land
    private Queue<Airplane> m_landingQueue;
    // coordinator of the airport
    private Coordinator m_coordinator;
    // Hash table which keep track of the arriving time of each airplane
    private Map<Airplane, Double> m_arrivalTimes;
    // Time to fly to the destination airplane
    private double m_flightTime;
    // how long each airplane needs to land
    private double m_runwayTimeToLand;
    // how long each airplane need to stay at the airport after land before departure
    private double m_requiredTimeOnGround;
    // Total circling time for this airport
    private double m_totalCirclingTime = 0;
    private String m_airportName;
    private double m_rangeLeft;

    public Airport(String name, double runwayTimeToLand, double requiredTimeOnGround, Coordinator coordinator) {
        m_airportName = name;
        m_coordinator = coordinator;
        m_runwayTimeToLand = runwayTimeToLand;
        m_requiredTimeOnGround = requiredTimeOnGround;
        m_landingQueue = new ArrayDeque<>();
        m_arrivalTimes = new HashMap<>();
    }

    public Coordinator getCoordinator() {
        return m_coordinator;
    }

    public double getTotalCirclingTime() {
        return Math.abs(m_totalCirclingTime);
    }

    public String getName() {
        return m_airportName;
    }

    public void handle(Event event) {
        // Get event and convert it into AirportEvent type
        AirportEvent airEvent = (AirportEvent)event;
        // Get the sender of the event (sender is an airplane)
        Airplane airplane = airEvent.getAirplane();

        // Three type of event available here : PLANE_ARRIVES, PLANE_DEPARTS, PLANE_LANDED
        // Each event will be executed with a delay :
        // Delay of PLANE_ARRIVES is m_runwayTimeToLand which means after arrival, airplane should take time to land
        // Delay of PLANE_DEPARTS is m_flightTime which means after departure this flight will fly to the destination airport in m_flightTime
        // Delay of PLANE_LANDED is m_requiredTimeOnGround which means after landed, the airplane will stay in the airport for m_requiredTimeOnGround
        // After adding every event to the event list in the simulator, the simulator will sort the list of events (TreeSet<Event> is used for keeping the events in order)
        switch(airEvent.getType()) {

            case AirportEvent.PLANE_DEPARTS:
            	
                // Random destination airport
                Airport destination = AirportSim.getRandomAirport(this);
                // Calculate distance to the destination airport
                double distance = this.m_coordinator.getDistance(destination.getCoordinator());
                // Calculate flying time to the destination airport based on the distance and flight speed
                m_flightTime = distance / airplane.getSpeed();
                // Random number of passengers
                airplane.randomNumberOfPassengers();
                // fuel left for this journal
                m_rangeLeft = 100 * (airplane.getmaxAirplaneRange() - distance)/airplane.getmaxAirplaneRange();
                System.out.println(getCurrentTime() + " : " + airplane.getName() + " departed from " + m_airportName + " to " + destination.m_airportName + ", Estimation flying time " + String.format("%.2f", m_flightTime)  + " hours, Speed " + airplane.getSpeed() + "KM/h, Distance " + String.format("%.0f", distance) + "KM, Number of Passengers " + airplane.getNumberPassengers() + ", Occupation rate " + String.format("%.2f", airplane.getOccupationRate()) + ", Fuel Left: " + String.format("%.3f",m_rangeLeft) + "% when arrive. ");
                // Flight will arrive at the destination airport in m_flightTime time, we add Airplane arrive event to the event list with a delay of the flying time
                AirportEvent takeoffEvent = new AirportEvent(m_flightTime, destination, AirportEvent.PLANE_ARRIVES, airplane);
                Simulator.schedule(takeoffEvent);
                break;

            case AirportEvent.PLANE_ARRIVES:
                // Put airplane into a landing queue
                m_landingQueue.add(airplane);
                // Use a hashmap to save arrival time of each airplane
                m_arrivalTimes.put(airplane, getCurrentTime());



                System.out.println(getCurrentTime() + " : " + airplane.getName() + " arrived at " + m_airportName);

                // if there is not other airplane in the landing queue, we add the PLANE_LANDED event into the event list
                if(m_landingQueue.size() == 1) {
                    AirportEvent landedEvent = new AirportEvent(m_runwayTimeToLand, this, AirportEvent.PLANE_LANDED, airplane);
                    Simulator.schedule(landedEvent);
                }

                if(m_landingQueue.size() > 1) {
                    AirportEvent landedEvent = new AirportEvent(m_runwayTimeToLand, this, AirportEvent.PLANE_LANDED, airplane);
                    Simulator.schedule(landedEvent);

                }
                break;

            case AirportEvent.PLANE_LANDED:
                System.out.println(getCurrentTime() + " : " + airplane.getName() + " landed at airport " + m_airportName);
                // Flight will depart in m_requiredTimeOnGround time, so PLANE_DEPARTS event is added into the event list
                AirportEvent departureEvent = new AirportEvent(m_requiredTimeOnGround, this, AirportEvent.PLANE_DEPARTS, airplane);
                Simulator.schedule(departureEvent);
                // Calculate circling time = landed time - arrival time - time spent on the runway
                m_totalCirclingTime += (getCurrentTime() - m_arrivalTimes.get(airplane) - m_runwayTimeToLand);
                // remove airplane from the hashmap
                m_arrivalTimes.remove(airplane);
                // Remove airplane from the landing queue
                m_landingQueue.poll();

                // If there is others airplanes waiting in the landing queue, we will allow the next airplane to land (Add PLANE_LANDED event to the event list for the next airplane)
                if(m_landingQueue.size() > 0)
                {
                    Airplane nextPlane = m_landingQueue.peek();
                    AirportEvent landingEvent = new AirportEvent(m_runwayTimeToLand, this, AirportEvent.PLANE_LANDED, nextPlane);
                    Simulator.schedule(landingEvent);
                }

                break;
        }
    }

    private double getCurrentTime()
    {
        return Math.round(Simulator.getCurrentTime() * 10000.000) / 10000.000;
    }
}
