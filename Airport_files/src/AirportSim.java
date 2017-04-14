// Author: Xiaoyang MENG, Wuchang LI, Cong DU, Soowon CHANG

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class AirportSim {
    // Modify these Constants to realize different simulation
    private static final int numberOfAirplane = 50;
    private static final int simulationTime = 100;
    private static final int maxNumberOfPassengers = 500;
    private static final int minNumberOfPassengers = 36;
    private static final int maxAirplaneSpeed = 1000;
    private static final int minAirplaneSpeed = 500;
    // maximum airplane range. in this simulation we use Boeing 787-8 as the model and it has the largest range in 787 family: 14140 km.
    private static final int maxAirplaneRange = 14140;
    
    private static final String flightRecordFile = "./Airport_files/resource/flights.txt";
    public static PrintWriter recordWriter= null;


    // List of airport with initialized runway time, on ground time and geographical coordinator of the airport
    // Source of coordinator : https://www.distancesto.com/coordinates/cn/beijing-latitude-longitude/history/991.html


    private static List<Airport> m_listOfAirport = new ArrayList<Airport>();
    // random variable
    private static Random rand = new Random();

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
    	recordWriter = new PrintWriter(flightRecordFile,"UTF-8");
    	Scanner airportInfo = new Scanner(new File("./Airport_files/resource/airports.csv"));
    	while(airportInfo.hasNextLine())
    	{
    		String line = airportInfo.nextLine();
    		String[] attrs = line.split(",");
    		m_listOfAirport.add(new Airport(attrs[1],4,4,
    				new Coordinator(Double.parseDouble(attrs[2]),Double.parseDouble(attrs[3]))));
    	}
    	airportInfo.close();

        // Generate list of airplane with random max number of passengers and speed
        for (int i = 0; i < numberOfAirplane; i++) {

            Airplane airplane = new Airplane("Airplane" + i, getRandomMaxNumberOfPassengers(), getRandomSpeed(), getRandomMaxAirplaneRange());
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
        recordWriter.close();
        DrawMap.draw(m_listOfAirport, flightRecordFile, simulationTime);
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
        System.out.println("Airport circling report : ");
        for (int i = 0; i < m_listOfAirport.size(); i++) {
            System.out.println(m_listOfAirport.get(i).getName() + " : " + String.format("%.2f", m_listOfAirport.get(i).getTotalCirclingTime()));
        }
    }
    // generate random max number of passengers for an airplane, range from minNumberOfPassengers to maxNumberOfPassengers
    private static int getRandomMaxNumberOfPassengers() {
        int[] k = {2,0,8,5,7,10,12,10,3,6,3,5};
        int j = 0;
        int simTime  = simulationTime/24/30%12; //month
        j = k[simTime];
        return minNumberOfPassengers + rand.nextInt(maxNumberOfPassengers - minNumberOfPassengers +1+j);
    } // Soo >  I changed the monthly changes of the number of passengers

    // generate random speed for an airplane
    private static int getRandomSpeed() {
        return minAirplaneSpeed + rand.nextInt(maxAirplaneSpeed - minAirplaneSpeed + 1);
    }

    //generate random max airplane range
    private static int getRandomMaxAirplaneRange(){
        return maxAirplaneRange;
    }
}
