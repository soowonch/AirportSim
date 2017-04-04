package airport_event_simulation;

public class AirportSim {
	public static void main(String[] args) {
		Airport lax = new Airport("LAX", 10.0, 600.0, 20.0,34.052200,-118.243700); //Los Angeles
        Airport katl = new Airport("KATL", 30.0, 600.0, 20.0, 33.640700, -84.427700); //Atlanta
        Airport rjtt = new Airport("RJTT", 10.0, 600.0, 10.0, 35.5494, 139.7798);//tokyo, Haneda
        Airport pek = new Airport("PEK",10.0,600.0,10.0,40.0799,116.6031);
        Airport pvg = new Airport("PVG",10.0,600.0, 10.0, 31.1443, 121.8083);
        
        Airplane airplane1 = new Airplane("1",lax,katl);
        Airplane airplane2 = new Airplane("2",lax,rjtt);
        Airplane airplane3 = new Airplane("3",lax,pek);
        Airplane airplane4 = new Airplane("4",lax,pvg);
        Airplane airplane5 = new Airplane("5",pek,lax);
        Airplane airplane6 = new Airplane("6",pek,pvg);
        Airplane airplane7 = new Airplane("7",pek,rjtt);
        Airplane airplane8 = new Airplane("8",pek,katl);
        Airplane airplane9 = new Airplane("9",katl,pek);
        Airplane airplane10 = new Airplane("10",katl,rjtt);
        Airplane airplane11 = new Airplane("11",katl,pvg);
        Airplane airplane12 = new Airplane("12",katl,lax);
        Airplane airplane13 = new Airplane("13",pvg,pek);
        Airplane airplane14 = new Airplane("14",pvg,rjtt);
        Airplane airplane15 = new Airplane("15",pvg,katl);
        Airplane airplane16 = new Airplane("16",pvg,lax);
        Airplane airplane17 = new Airplane("17",rjtt,lax);
        Airplane airplane18 = new Airplane("18",rjtt,katl);
        Airplane airplane19 = new Airplane("19",rjtt,pek);
        Airplane airplane20 = new Airplane("20",rjtt,pvg);
        
        AirportEvent departure1 = new AirportEvent(5,lax,AirportEvent.PLANE_DEPARTS_READY,airplane1);
        AirportEvent departure2 = new AirportEvent(5,lax,AirportEvent.PLANE_DEPARTS_READY,airplane2);
        AirportEvent departure3 = new AirportEvent(5,lax,AirportEvent.PLANE_DEPARTS_READY,airplane3);
        AirportEvent departure4 = new AirportEvent(5,lax,AirportEvent.PLANE_DEPARTS_READY,airplane4);
        AirportEvent departure5 = new AirportEvent(5,pek,AirportEvent.PLANE_DEPARTS_READY,airplane5);
        AirportEvent departure6 = new AirportEvent(5,pek,AirportEvent.PLANE_DEPARTS_READY,airplane6);
        AirportEvent departure8 = new AirportEvent(5,pek,AirportEvent.PLANE_DEPARTS_READY,airplane8);
        AirportEvent departure7 = new AirportEvent(5,pek,AirportEvent.PLANE_DEPARTS_READY,airplane7);
        AirportEvent departure9 = new AirportEvent(5,katl,AirportEvent.PLANE_DEPARTS_READY,airplane9);
        AirportEvent departure10 = new AirportEvent(5,katl,AirportEvent.PLANE_DEPARTS_READY,airplane10);
        AirportEvent departure11 = new AirportEvent(5,katl,AirportEvent.PLANE_DEPARTS_READY,airplane11);
        AirportEvent departure12 = new AirportEvent(5,katl,AirportEvent.PLANE_DEPARTS_READY,airplane12);
        AirportEvent departure13 = new AirportEvent(5,pvg,AirportEvent.PLANE_DEPARTS_READY,airplane13);
        AirportEvent departure14 = new AirportEvent(5,pvg,AirportEvent.PLANE_DEPARTS_READY,airplane14);
        AirportEvent departure15 = new AirportEvent(5,pvg,AirportEvent.PLANE_DEPARTS_READY,airplane15);
        AirportEvent departure16 = new AirportEvent(5,pvg,AirportEvent.PLANE_DEPARTS_READY,airplane16);
        AirportEvent departure17 = new AirportEvent(5,rjtt,AirportEvent.PLANE_DEPARTS_READY,airplane17);
        AirportEvent departure18 = new AirportEvent(5,rjtt,AirportEvent.PLANE_DEPARTS_READY,airplane18);
        AirportEvent departure19 = new AirportEvent(5,rjtt,AirportEvent.PLANE_DEPARTS_READY,airplane19);
        AirportEvent departure20 = new AirportEvent(5,rjtt,AirportEvent.PLANE_DEPARTS_READY,airplane20);
        Simulator.schedule(departure1);
        Simulator.schedule(departure2);
        Simulator.schedule(departure3);
        Simulator.schedule(departure4);
        Simulator.schedule(departure5);
        Simulator.schedule(departure6);
        Simulator.schedule(departure7);
        Simulator.schedule(departure8);
        Simulator.schedule(departure9);
        Simulator.schedule(departure10);
        Simulator.schedule(departure11);
        Simulator.schedule(departure12);
        Simulator.schedule(departure13);
        Simulator.schedule(departure14);
        Simulator.schedule(departure15);
        Simulator.schedule(departure16);
        Simulator.schedule(departure17);
        Simulator.schedule(departure18);
        Simulator.schedule(departure19);
        Simulator.schedule(departure20);
        
        
        
        Simulator.stopAt(150*60);
        Simulator.run();
        
        System.out.println(lax.getArriving()+" people get to LAX airport by flight");
        System.out.println(lax.getLeaving()+" people leave LAX airport by flight");
        System.out.println(lax.getCircling()+" lax");
        System.out.println(katl.getArriving()+" people get to LAX airport by flight");
        System.out.println(katl.getLeaving()+" people leave LAX airport by flight");
        System.out.println(katl.getCircling()+" katl");
        System.out.println(pek.getArriving()+" people get to LAX airport by flight");
        System.out.println(pek.getLeaving()+" people leave LAX airport by flight");
        System.out.println(pek.getCircling()+" pek");
        System.out.println(rjtt.getArriving()+" people get to LAX airport by flight");
        System.out.println(rjtt.getLeaving()+" people leave LAX airport by flight");
        System.out.println(rjtt.getCircling()+" rjtt");
        System.out.println(pvg.getArriving()+" people get to LAX airport by flight");
        System.out.println(pvg.getLeaving()+" people leave LAX airport by flight");
        System.out.println(pvg.getCircling()+" pvg");
	}

}
