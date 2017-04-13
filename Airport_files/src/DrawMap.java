import java.util.List;

//StdDraw use no object model, if would be fine to only use only the method as static method
public class DrawMap {
	
	public static void draw(List<Airport> listOfAirport,String flightRecordFile){
		StdDraw.setCanvasSize(700, 553);
		StdDraw.picture(0.5,0.5 , "moutline.gif");
		StdDraw.setPenRadius(0.01);
		position np = cordToXY(0, 51);
		StdDraw.point(np.x, np.y);
	}
	
	public static void main(String[] args) {
		//test class for DrawMap
			draw();
	}
	
	private static class position {
		public double x = 0;
		public double y = 0;
	}
	
	private static position cordToXY(double longitude,double latitude) {
		position newPosition = new position();
		latitude = latitude * -1;
		// get x value
		newPosition.x = (longitude+180)*(1.0/360);
		if (newPosition.x<0.037)
			newPosition.x = 1.0-newPosition.x;
		else
			newPosition.x = newPosition.x - 0.037;
		

		// convert from degrees to radians
		double latRad = latitude*Math.PI/180;

		// get y value
		double mercN = Math.log(Math.tan((Math.PI/4)+(latRad/2)));
		newPosition.y  = (1.0/2)-(1.0*mercN/(2*Math.PI));
		return newPosition;
		
		
	}

}
