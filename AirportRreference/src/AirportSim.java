//YOUR NAME HERE

import java.util.TreeSet;


public class AirportSim {
    public static void main(String[] args) {
        int number_of_airports =5;
        Airport LAX = new Airport("LAX", 0.0267, 1, 0.0267);
        Airport ATL = new Airport("ATL", 0.0250, 1, 0.0250);
        Airport SEA = new Airport("SEA", 0.0300, 1, 0.0300);
        Airport LHR = new Airport("LHR", 0.0222, 1, 0.0222);
        Airport HKG = new Airport("HKG", 0.0294, 1, 0.0294);
        LAX.m_allAirports[0] = LAX;
        LAX.m_allAirports[1] = ATL;
        LAX.m_allAirports[2] = SEA;
        LAX.m_allAirports[3] = LHR;
        LAX.m_allAirports[4] = HKG;

        int number_of_airplanes = 500;
        for (int i = 0; i < number_of_airplanes; i++) {
            int PassengerCapacity_Chosen = (int) Math.random() * 100;
            int maxPassengerCapacity_tmp;
            if (PassengerCapacity_Chosen > 70) {
                maxPassengerCapacity_tmp = 170;
            } else {
                maxPassengerCapacity_tmp = 120;
            }
            double delay_time = Math.random();
            int Airport_Chosen = (int) Math.random() * 5;
            int[] AirEventStatus = new int[]{
                    AirportEvent.PLANE_ARRIVES,
                    AirportEvent.PLANE_DEPARTS
            };
            int AirEventStatus_Chosen = (int) Math.random() * 2;
            Airplane airplane_tmp = new Airplane(Integer.toString(i), maxPassengerCapacity_tmp);
            AirportEvent airportEvent_tmp = new AirportEvent(delay_time, LAX.m_allAirports[Airport_Chosen], AirEventStatus[AirEventStatus_Chosen], airplane_tmp);
            Simulator.schedule(airportEvent_tmp);
        }

        Simulator.stopAt(5000);
        Simulator.run();
        for (int j = 0; j < number_of_airports; j++) {
            System.out.println("The number of arriving planes of "+ LAX.m_allAirports[j].getName()+ " is " + LAX.m_allAirports[j].getarrivingPlanes());
            System.out.println("The number of departing planes of "+ LAX.m_allAirports[j].getName()+ " is " + LAX.m_allAirports[j].getdepartingPlanes());
            System.out.println("The number of arriving passengers of "+ LAX.m_allAirports[j].getName()+ " is " + LAX.m_allAirports[j].getarrivingPassengers());
            System.out.println("The number of departing passengers of "+ LAX.m_allAirports[j].getName()+ " is " + LAX.m_allAirports[j].getdepartingPassengers());
            System.out.println("The total circling time of "+ LAX.m_allAirports[j].getName()+ " is " + LAX.m_allAirports[j].getCirclingTime());
            System.out.println("The average circling time of "+ LAX.m_allAirports[j].getName()+ " is " + LAX.m_allAirports[j].getaveCirclingTime());
        }
    }

}
