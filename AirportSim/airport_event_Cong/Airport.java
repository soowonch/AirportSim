package airport_event_simulation;

/**
 * 
 * @author Cong Du
 *
 */

import java.util.LinkedList;
import java.util.Random;
import java.util.Hashtable;


public class Airport implements EventHandler {
	private boolean m_runwayfree;
	
	private double m_runwayTimeToTakeOff;
	private double m_runwayTimeToLand;
	private double m_requiredTimeOnGround;
	//how long it will take for a airplane to take off, and take up the run way
	//runwayTime means how long it will take for a airplane to land, and take up the runway
	//how long it take for an airplane to prepare to take off.
	
	//stats info.
	private long arrivingCount;
	private long leavingCount;
	
	private long circlingTime ;
	private String m_airportName;
	//geology information about one Airport;
	private double m_latitude;
	private double m_longitude;
	
	//this is the waiting queue of an airport, notice that landing and departing airplanes are wait in the same queue, based on first-come-first-serve principal
	private LinkedList<Airplane> waitingQ;
	private Hashtable<String,Double> distanceToOtherAirport; 

	private static Random randomPassengerNumber = new Random();
	
	public long getArriving() {
		return arrivingCount;
	}
	
	public long getLeaving() {
		return leavingCount;
	}
	
	public long getCircling() {
		return circlingTime;
	}
	
	public Airport(String name, double runwayTimeToLand,double requiredTimeOnGround,double runwayTimeToTakeOff, double lat, double longi) {
		m_airportName = name;
		m_runwayTimeToLand = runwayTimeToLand;
		m_requiredTimeOnGround = requiredTimeOnGround;
		m_runwayTimeToTakeOff = runwayTimeToTakeOff;
		m_latitude = lat;
		m_longitude = longi;
		m_runwayfree = true;
		
		arrivingCount = 0;
		leavingCount = 0;
		
		circlingTime = 0;
		
		waitingQ = new LinkedList<Airplane>();
		distanceToOtherAirport = new Hashtable<String,Double>();
	}
	
	public String getName() {
		return m_airportName;
	}
	
	//airportEvent.getHandler.handle() will call different airport
	public void handle(Event event) {
		AirportEvent airEvent = (AirportEvent)event;
		Airplane relatedAirplane= airEvent.getSubject();
		
		switch(airEvent.getType()) {
		case AirportEvent.PLANE_DEPARTS_READY:
			System.out.println(Simulator.getCurrentTime()+": plane "+relatedAirplane.getName()+" ready to take off at airport "+getName());
			relatedAirplane.setPassengersNumber((int)(randomPassengerNumber.nextGaussian()*50+230.0));
			//System.out.println("PassengersNumber: "+relatedAirplane.getPassengersNumber());
			leavingCount += relatedAirplane.getPassengersNumber();
			if(m_runwayfree)
			{
				//System.out.println("    plane "+relatedAirplane.getName()+" now on the runway of "+getName()+" to depart");
				m_runwayfree = false;
				AirportEvent takeOffFinished = new AirportEvent(m_runwayTimeToTakeOff, this, AirportEvent.PLANE_DEPARTS_FINISHED, relatedAirplane);
				Simulator.schedule(takeOffFinished);
			}
			else {
				//System.out.println("    plane "+relatedAirplane.getName()+" waiting in line to depart");
				waitingQ.add(relatedAirplane);
			}
			break;
			
		case AirportEvent.PLANE_DEPARTS_FINISHED:
			System.out.println(Simulator.getCurrentTime()+": plane "+relatedAirplane.getName()+" has left airport "+getName()+" heading to "+relatedAirplane.getDest().getName());
			double distance = distanceTo(relatedAirplane.getDest());
			AirportEvent arriving = new AirportEvent(distance/relatedAirplane.getSpeed()*60.0,relatedAirplane.getDest(), AirportEvent.PLANE_ARRIVES, relatedAirplane);
			Simulator.schedule(arriving);
			if(waitingQ.isEmpty())
			{
				//System.out.println("     airport "+getName()+"'s runway is free");
				m_runwayfree = true;
			}
				
			else {
				Airplane nextAirplane = waitingQ.poll();
				handleRunwayIssue(nextAirplane);
			}
			break;
			
		case AirportEvent.PLANE_ARRIVES:
			System.out.println(Simulator.getCurrentTime()+": plane "+relatedAirplane.getName()+" arriving at "+getName()+" ,ready to land");
			if(m_runwayfree)
			{
				//System.out.println("    plane "+relatedAirplane.getName()+" now on the runway of "+getName()+" to land");
				m_runwayfree = false;
				AirportEvent landedEvent = new AirportEvent(m_runwayTimeToLand, this, AirportEvent.PLANE_LANDED, relatedAirplane);
				Simulator.schedule(landedEvent);
			}
			else {
				//System.out.println("     plane "+relatedAirplane.getName()+" waiting in line to land");
				relatedAirplane.setTimeStamp(Simulator.GetDoubleCurrentTime());
				waitingQ.add(relatedAirplane);
			}
			break;
			
		case AirportEvent.PLANE_LANDED:
			System.out.println(Simulator.getCurrentTime()+": plane "+relatedAirplane.getName()+" landed at "+getName()+", what a trip!");
			arrivingCount += relatedAirplane.getPassengersNumber();
			relatedAirplane.return_trip();//exchange dest and origin, get prepared for "return";
			AirportEvent departsReady = new AirportEvent(m_requiredTimeOnGround, this, AirportEvent.PLANE_DEPARTS_READY, relatedAirplane);
			Simulator.schedule(departsReady);
			if(waitingQ.isEmpty())
			{
				//System.out.println("airprt "+getName()+"'s runway is free");
				m_runwayfree = true;
			}
			else {
				Airplane nextAirplane = waitingQ.poll();
				handleRunwayIssue(nextAirplane);
			}
		}
	}
	
	//part of handle code, write a separated function to reduce code duplication 
	private void handleRunwayIssue(Airplane nextplane) {
		if(nextplane.getDest().getName().equals(getName())) {//then this airplane is going to land.
			System.out.println("     plane "+nextplane.getName()+" now is landing on airport "+getName());
			circlingTime +=Simulator.GetDoubleCurrentTime()-nextplane.getTimeStamp();
			AirportEvent landedEvent = new AirportEvent(m_runwayTimeToLand, this, AirportEvent.PLANE_LANDED,nextplane);
			Simulator.schedule(landedEvent);
		}
		else {
			System.out.println("     plane "+nextplane.getName()+" now is departing from airport "+getName());
			AirportEvent takeOffFinished = new AirportEvent(m_runwayTimeToTakeOff, this, AirportEvent.PLANE_DEPARTS_FINISHED, nextplane);
			Simulator.schedule(takeOffFinished);
		}
	}
	
	//this is a function belong to different airport, they will check their private field hashTable first, if the distance value is not saved,
	//call static method to compute it and add to hash table;
	public double distanceTo(Airport dest)
	{
		double dist;
		if(distanceToOtherAirport.containsKey(dest.getName()))
			dist = distanceToOtherAirport.get(dest.getName());
		else {
			dist = GetDistance(this,dest);
			distanceToOtherAirport.put(dest.getName(), dist);
		}
		//System.out.println("the distance is "+dist);
		return dist;
	}
	
	//reference http://blog.csdn.net/xiyang_1990/article/details/16803735
	//reference Android SDK api, android.location.Location.getDistance();
	//	
	//unit: km;
	private static final double EARTH_RADIUS = 6387.137;
	private static double rad(double d){
		return d * Math.PI /180.0;
	}
	
	public static double GetDistance(Airport origin, Airport dest) {
		double radLatitude1 = rad(origin.m_latitude);
		double radLatitude2 = rad(dest.m_latitude);
		double a = radLatitude1 - radLatitude2;
		double b = rad(origin.m_longitude)-rad(dest.m_longitude);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+
				Math.cos(radLatitude1)*Math.cos(radLatitude2)*Math.pow(Math.sin(b/2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) /10000;
		return s;
	}
	
	
}
