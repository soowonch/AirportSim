//YOUR NAME HERE

//TODO add number of passengers, speed

public class Airplane {
    private String m_name;
    private int m_numberPassengers;
    private int m_maxPassengerCapacity;
    private double m_speed;
    private double m_timestamp;

    public Airplane(String name, int maxPassengerCapacity) {
        m_name = name;
        m_maxPassengerCapacity = maxPassengerCapacity;
        // the unit of speed is mph.
        // Typical cruising airspeed for a long-distance commercial passenger aircraft is 546 -575 mph.
        m_speed = Math.random()*29+546;
        m_numberPassengers = (int) (Math.random()*maxPassengerCapacity*0.08 +maxPassengerCapacity*0.92);
        m_timestamp =0;
        //System.out.println(m_name+ " has passengers " + m_numberPassengers);
    }

    public void setTimestamp(double timestamp){
        m_timestamp = timestamp;
    }

    public void setNumberPassengers(){
        m_numberPassengers = (int) (Math.random()*m_maxPassengerCapacity*0.08 +m_maxPassengerCapacity*0.92);
       //System.out.println(m_name+ " has passengers " + m_numberPassengers);
    }

    public void setSpeed(){
        m_speed = Math.random()*29+546;
    }

    public String getName() {
        return m_name;
    }
    public int getNumberPassengers (){return m_numberPassengers;}
    public double getSpeed(){return m_speed;}
    public double getTimestamp(){return m_timestamp;}
}
