// Author: Xiaoyang MENG

import java.util.*;


public class AirportSim {
    // Modify these Constants to realize different simulation
    private static final int numberOfAirplane = 10;
    private static final int simulationTime = 100;
    private static final int maxNumberOfPassengers = 500;
    private static final int minNumberOfPassengers = 30;
    private static final int maxAirplaneSpeed = 1000;
    private static final int minAirplaneSpeed = 500;

    // List of airport with initialized runway time, on ground time and geographical coordinator of the airport
    // Source of coordinator : https://www.distancesto.com/coordinates/cn/beijing-latitude-longitude/history/991.html
    private static List<Airport> m_listOfAirport = new ArrayList<Airport>(Arrays.asList(
            new Airport("SEA", 4, 4, new Coordinator(-122.3088165, 47.4502499)),
            new Airport("LAX", 8, 8, new Coordinator(-118.40853, 33.9415889)),
            new Airport("ATL", 10, 10, new Coordinator(-84.42770009, 33.6407282)),
            new Airport("CDG", 9, 9, new Coordinator(2.541188, 49.013993)),
            new Airport("PEK", 12, 12, new Coordinator(116.407395, 39.904211))));

    // random variable
    private static Random rand = new Random();

    public static void main(String[] args) {

        // Generate list of airplane with random max number of passengers and speed
        for (int i = 0; i < numberOfAirplane; i++) {

            Airplane airplane = new Airplane("Airplane" + i, getRandomMaxNumberOfPassengers(), getRandomSpeed());
            // Airplane depart and fly to a random destination airport
            AirportEvent event = new AirportEvent(0, getRandomAirport(null), AirportEvent.PLANE_DEPARTS, airplane);
            // simulator launch event
            Simulator.schedule(event);
        }

        // Simulator stop at given time
        Simulator.stopAt(simulationTime);
        // Simulator start to run
        Simulator.run();
        // generate circling report for each airport and output it
        airportCirclingReport();
    }

    // get a random airport which is different from the departure airport
    public static Airport getRandomAirport(Airport departure) {
        Airport result;

        do {
            result = m_listOfAirport.get(rand.nextInt(m_listOfAirport.size()));
        } while (result.equals(departure));

        return result;
    }

    // generate circling report for each airport and output it
    private static void airportCirclingReport() {
        System.out.println("Airport circling report: ");
        for (int i = 0; i < m_listOfAirport.size(); i++) {
            System.out.println(m_listOfAirport.get(i).getName() + " : " + String.format("%.2f", m_listOfAirport.get(i).getTotalCirclingTime()));
        }
    }

    // generate random max number of passengers for an airplane, range from minNumberOfPassengers to maxNumberOfPassengers
    private static int getRandomMaxNumberOfPassengers() {
        return minNumberOfPassengers + rand.nextInt(maxNumberOfPassengers - minNumberOfPassengers + 1);
    }

    // generate random speed for an airplane
    private static int getRandomSpeed() {
        return minAirplaneSpeed + rand.nextInt(maxAirplaneSpeed - minAirplaneSpeed + 1);
    }
}
