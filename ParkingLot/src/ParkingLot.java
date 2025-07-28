import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {
    private List<ParkingsSpot> spots;
    private int capacity;
   private Map<Vehicle, ParkingsSpot> parkedVehilces;
    private static ParkingLot instance;

    ParkingLot(int capacity) {
        this.capacity = capacity;
        this.spots = new ArrayList<>();
        this.parkedVehilces = new HashMap<>();
    }

    public static synchronized ParkingLot getInstance(int capacity){

        if(instance == null){
             instance = new ParkingLot(capacity);
        }

        return instance;
    }

    public  boolean parkVehicle(Vehicle vehicle, ParkingsSpot spot) {
        return  false;
    }

    public void freeVehicle(Vehicle vehicle) {
      ParkingsSpot spot = parkedVehilces.get(vehicle);
      if(spot != null){
          spot.freeVehicle();
          parkedVehilces.remove(vehicle);
      }


    }







}
