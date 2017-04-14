// Author: Xiaoyang MENG, Wuchang LI, Cong DU, Soowoo CHANG

public class Coordinator {
    private double m_longitude;
    private double m_latitude;

    public double getLongitude() {
        return m_longitude;
    }

    public double getLatitude() {
        return m_latitude;
    }

    public Coordinator(double longitude, double latitude)
    {
        m_longitude = longitude;
        m_latitude = latitude;
    }

    // Get distance from current coordinator to another given coordinator
    public double getDistance(Coordinator coordinator) {
        return distance(coordinator.getLatitude(), coordinator.getLongitude(), m_latitude, m_longitude);
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        dist = dist * 1.609344;

        return dist;
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
