package airport_event_simulation;

/**
 * 
 * @author Cong Du
 *
 */
//cha

public class Airplane {
    private String m_name;
    private int m_numberPassengers;
    private int m_speed = 750;  //unit:km/h
    private int m_max_passengers = 300;
    private Airport m_dest;
    private Airport m_origin;
    private double timeStamp = 0;
    
    public void setTimeStamp(double num) {
    	timeStamp = num;
    }
    
    public double getTimeStamp() {
    	return timeStamp;
    }
    public void return_trip() {
    	Airport temp = m_dest;
    	m_dest = m_origin;
    	m_origin = temp;
    }
    
    public Airport getDest() {
    	return m_dest;
    }
    
    public Airport getOrigin() {
    	return m_origin;
    }
    
    public Airplane(String name) {
        m_name = name;
    }

    public Airplane(String name, Airport origin,Airport dest) {
    	m_name = name;
    	m_dest = dest;
    	m_origin = origin;
    }
    
    public Airplane(String name, Airport origin,Airport dest, int speed, int max_passengers){
    	m_name = name;
    	m_dest = dest;
    	m_origin = origin;
    	m_speed = speed;
    	m_max_passengers = max_passengers;
    }
    
    public String getName() {
        return m_name;
    }
    
    public void setSpeed(int sp) {
    	m_speed = sp;
    }
    
    public int getSpeed() {
    	return m_speed;
    }
    
    public void setPassengersNumber(int num) {
    	if(num > this.m_max_passengers) 
    		this.m_numberPassengers = this.m_max_passengers;
    	else
    		this.m_numberPassengers=num;
    }
    
    public int getPassengersNumber() {
    	return this.m_numberPassengers;
    }
}

