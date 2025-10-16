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

    public  boolean parkVehicle(Vehicle vehicle) {
        for(ParkingsSpot spot: spots){
            if(spot.isAvailable() && spot.canFitVehicle(vehicle)){
                parkedVehilces.put(vehicle,spot);
                spot.parkVehicle();
                return true;

            }
        }
        return  false;
    }

    public void freeVehicle(Vehicle vehicle) {
      ParkingsSpot spot = parkedVehilces.get(vehicle);
      if(spot != null){
          spot.freeVehicle();
          parkedVehilces.remove(vehicle);
      }

    }

    public int getNumberOfAvailableSpots(){
        int availableSpots =0;
        for(ParkingsSpot spot: spots){
            if(spot.isAvailable()){
                availableSpots++;
            }
        }
        return availableSpots;
    }

    public void addParkingSpot(ParkingsSpot spot){
        if(spots.size() < capacity ){
            spots.add(spot);
        }else{
            System.out.println("Parking space is full");
        }
    }

}

// function requirement
// should assign parking spot and spot id to to vehicle
// system should show if parking spot is available (is capacity full)
// once vehicle is leaved system should make spot free
// system should support to park different type of vehicles
