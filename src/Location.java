
public class Location
{
    private double latitude, longitude;
    private String cityName;
    
    
    public Location(double latitude, double longitude, String cityName)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityName = cityName;
    }
    
    public Location()
    {
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.cityName = "";
    }
    
    public static double dmsToDecimal(String coord)
    {
        int indexOfDeg = coord.indexOf("°");
        int indexOfMin = coord.indexOf("'");
        int indexOfSec = coord.indexOf("\"");

        double degree = Double.parseDouble(coord.substring(0, indexOfDeg));
        double minutes = Double.parseDouble(coord.substring(indexOfDeg + 2, indexOfMin));
        double seconds = Double.parseDouble(coord.substring(indexOfMin + 2, indexOfSec));

        int sign = 1;
        char dir = coord.charAt(coord.length() - 1);
        if (dir == 'S' || dir == 'W')
            sign = -1;

        return (degree + (minutes / 60) + (seconds / 3600)) * sign;
    }
    
    public double getLatitude()
    {
        return latitude;
    }
    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }
    public double getLongitude()
    {
        return longitude;
    }
    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }
    public String getCityName()
    {
        return cityName;
    }
    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }
    
    public String toString()
    {
        return "Latitude: " + latitude + ", Longitude: " + longitude + " City Name: " + cityName;
    }
    
}
