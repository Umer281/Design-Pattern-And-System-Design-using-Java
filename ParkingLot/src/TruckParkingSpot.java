public class TruckParkingSpot extends   ParkingsSpot{


    TruckParkingSpot(int id){
        super(id);
    }

    @Override
    public boolean canFitVehicle(Vehicle vehicle) {
        return vehicle instanceof  Truck;
    }
}
