// Author: Xiaoyang MENG, Wuchang LI, Cong DU, Soowoo CHANG

//TODO add number of passengers, spe
//Author: Xiaoyang MENG
import java.util.Random;

public class Airplane {
    private String m_name;
    // Current number of passengers on the flight
    private int m_numberPassengers;
    // Max number of passengers on the flight
    private int m_maxNumberOfPassengers;
    // Speed of the flight
    private int m_speed;
    // maximum range of a flight, here we assume all our flights in this simulation is Boeing 787-8. it has the largest range in 787 family.
    private int m_maxAirplaneRange;
    // distance that left for a plane to travel, which in here means the fuel left
    //private int m_distanceLeft;

    private Random rand = new Random();

    public Airplane(String name, int maxNumberOfPassengers, int speed, int maxAirplaneRange) {
        m_name = name;
        m_numberPassengers = 0;
        m_maxNumberOfPassengers = maxNumberOfPassengers;
        m_speed = speed;
        m_maxAirplaneRange = maxAirplaneRange;
    }

    public String getName() {
        return m_name;
    }

    public double getOccupationRate()
    {
        return (double)m_numberPassengers / m_maxNumberOfPassengers;
    }

    public int getNumberPassengers() {
        return m_numberPassengers;
    }

    public void setnumberPassengers(int numberPassengers) {
        m_numberPassengers = numberPassengers;
    }

    public int getmaxNumberOfPassengers() {
        return m_maxNumberOfPassengers;
    }

    public int getmaxAirplaneRange() {
        return m_maxAirplaneRange;
    }

    public int getSpeed() {
        return m_speed;
    }



    // Random Gaussian distribution of number of passengers based on Max number of passengers of this flight
    public void randomNumberOfPassengers() {
        double randomGaussian;

        do {
            randomGaussian =  rand.nextGaussian();
        } while (randomGaussian > 1 || randomGaussian < -1);

        this.m_numberPassengers = (int) Math.floor(randomGaussian * m_maxNumberOfPassengers / 2 + m_maxNumberOfPassengers / 2) + 1;
    }
}