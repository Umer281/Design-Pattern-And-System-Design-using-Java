import java.util.Locale;

public class ParkingSpotFactory {

    public static ParkingsSpot createParkingSpot(String type, int id){

        switch(type.toLowerCase()) {
            case "car":
                return new CarParkingSpot(id);
            case "truck":
                return new TruckParkingSpot(id);
            case "motorcycle":
                return new MotorCycleParkingSpot(id);
            default:
                throw new IllegalArgumentException("Unknown parking spot type");
        }

    }
}
