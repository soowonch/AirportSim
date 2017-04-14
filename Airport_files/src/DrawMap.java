import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;


//StdDraw use no object model, if would be fine to only use only the method as static method
public class DrawMap {
	
	private static List<Flight> currentFlights = new LinkedList<Flight>();
	private static Flight pendingFlight = null;
	private static double currentTime = 0;
	private static double timeStep = 0.1;
	
	public static void draw(List<Airport> listOfAirport,String flightRecordFile,double endTime) throws FileNotFoundException{
	
		
		StdDraw.setCanvasSize(1046, 1046);
		StdDraw.enableDoubleBuffering();
		
	    Scanner scan = new Scanner(new File(flightRecordFile));
	    while(scan.hasNextLine()){
	        String line = scan.nextLine();
	        String[] elements = line.split(",");
	        pendingFlight = new Flight(Double.parseDouble(elements[0]),
	        		Double.parseDouble(elements[1]),Double.parseDouble(elements[2]),
	        		Double.parseDouble(elements[3]),Double.parseDouble(elements[4]),
	        		Double.parseDouble(elements[5]));
	        if(pendingFlight.depart_time <= currentTime)
	        	currentFlights.add(pendingFlight);
	        else
	        	break;
	        
	    }

		
		for(;currentTime<endTime;currentTime+=timeStep)
		{
			//add new flight to current list;
			if(pendingFlight.depart_time <= currentTime)
			{
				currentFlights.add(pendingFlight);
				while(scan.hasNextLine()){
					String line = scan.nextLine();
					String[] elements = line.split(",");
					pendingFlight = new Flight(Double.parseDouble(elements[0]),
							Double.parseDouble(elements[1]),Double.parseDouble(elements[2]),
							Double.parseDouble(elements[3]),Double.parseDouble(elements[4]),
							Double.parseDouble(elements[5]));
					if(pendingFlight.depart_time <= currentTime)
						currentFlights.add(pendingFlight);
					else
						break;
		    }
		    }
			//delete obsolete flights (which should already at destination):
			Iterator<Flight> it= currentFlights.iterator();
			while(it.hasNext())
			{
				Flight F = it.next();
				if(F.arrive_time < currentTime)
					it.remove();
			}
			
			StdDraw.clear();
			StdDraw.picture(0.5,0.5 , "./Airport_files/resource/projection.png",1,1);
			StdDraw.setPenRadius(0.01);
			StdDraw.setPenColor(StdDraw.BLUE);
			//draw airport, use the list we get.
			for(Airport airport:listOfAirport) {
				drawAirport(airport);
			}
			StdDraw.setPenColor(StdDraw.RED);
			for(Flight flight:currentFlights) {
				drawFlight(flight);
			}
			
			StdDraw.show();
			StdDraw.pause(20);
		}
		scan.close();

	}
	
	public static void main(String[] args) {
		//test class for DrawMap
		List<Airport> m_listOfAirport = new ArrayList<Airport>(Arrays.asList(
	            new Airport("SEA", 4, 4, new Coordinator(-122.3088165, 47.4502499)),
	            new Airport("LAX", 8, 8, new Coordinator(-118.40853, 33.9415889)),
	            new Airport("ATL", 10, 10, new Coordinator(-84.42770009, 33.6407282)),
	            new Airport("CDG", 9, 9, new Coordinator(2.541188, 49.013993)),
	            new Airport("PEK", 12, 12, new Coordinator(116.407395, 39.904211))));
		
		try {
			draw(m_listOfAirport,"./Airport_files/resource/test.txt",20);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static class position {
		public double x = 0;
		public double y = 0;
	}
	
	private static void drawFlight(Flight FL) {
		double FLProcess = (currentTime - FL.depart_time) /(FL.arrive_time - FL.depart_time); 
		double current_longitude = 0;
		double current_latitude = (FL.d_latitude-FL.o_latitude) * FLProcess +FL.o_latitude;
		if(FL.o_longitude - FL.d_longitude > 180.0)
		{
			current_longitude = (FL.d_longitude - FL.o_longitude +360.0)*FLProcess + FL.o_longitude;
			if(current_longitude > 180.0)
				current_longitude -= 360;
		}
		else if(FL.o_longitude - FL.d_longitude < -180.0)
		{
			current_longitude = (FL.d_longitude - FL.o_longitude -360) * FLProcess + FL.o_longitude;
			if(current_longitude < -180.0)
				current_longitude += 360;
		}
		else 
			current_longitude = (FL.d_longitude-FL.o_longitude) * FLProcess + FL.o_longitude;
		
		drawPoint(cordToXY(current_longitude,current_latitude));
	}
	
	private static void drawAirport(Airport AP) {
		Coordinator Coor = AP.getCoordinator();
		drawPoint(cordToXY(Coor.getLongitude(),Coor.getLatitude()));
	}
	
	private static void drawPoint(position toDraw)
	{
		StdDraw.point(toDraw.x, toDraw.y);
	}

	private static position cordToXY(double longitude,double latitude) {
		position newPosition = new position();
		latitude = latitude * -1;
		// get x value
		newPosition.x = (longitude+180)*(1.0/360);


		// convert from degrees to radians
		double latRad = latitude*Math.PI/180;

		// get y value
		double mercN = Math.log(Math.tan((Math.PI/4)+(latRad/2)));
		newPosition.y  = (1.0/2)-(1.0*mercN/(2*Math.PI));
		
		return newPosition;
	}
	
	private static class Flight {
		public double o_longitude;
		public double o_latitude;
		public double d_longitude;
		public double d_latitude;
		public double depart_time;
		public double arrive_time;
		
		public Flight(double o_lo,double o_la,double d_lo,double d_la,double de_time,double ar_time)
		{
			o_longitude = o_lo;
			o_latitude = o_la;
			d_longitude = d_lo;
			d_latitude = d_la;
			depart_time = de_time;
			arrive_time = ar_time;
		}
		
	}

}
