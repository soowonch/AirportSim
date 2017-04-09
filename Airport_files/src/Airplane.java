// Author: Xiaoyang MENG

//TODO add number of passengers, speed

import java.util.Random;

public class Airplane {
    private String m_name;
    // Current number of passengers on the flight
    private int m_numberPassengers;
    // Max number of passengers on the flight
    private int m_maxNumberOfPassengers;
    // Speed of the flight
    private int m_speed;
    private Random rand = new Random();

    public Airplane(String name, int maxNumberOfPassengers, int speed) {
        m_name = name;
        m_numberPassengers = 0;
        m_maxNumberOfPassengers = maxNumberOfPassengers;
        m_speed = speed;
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

    public void setNumberPassengers(int numberPassengers) {
        m_numberPassengers = numberPassengers;
    }

    public int getMaxNumberOfPassengers() {
        return m_maxNumberOfPassengers;
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