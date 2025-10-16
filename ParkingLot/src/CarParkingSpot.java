class CarParkingSpot extends  ParkingsSpot {


    CarParkingSpot(int id){
         super(id);
    }

    @Override
    public boolean canFitVehicle(Vehicle vehicle) {
        return vehicle instanceof  Car;
    }
}
