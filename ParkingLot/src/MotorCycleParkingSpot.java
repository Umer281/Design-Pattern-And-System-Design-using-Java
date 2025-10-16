public class MotorCycleParkingSpot extends  ParkingsSpot {
    MotorCycleParkingSpot(int id){
        super(id);
    }

    @Override
    public boolean canFitVehicle(Vehicle vehicle) {
        return vehicle instanceof  MotorCycle;
    }
}
