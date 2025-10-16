public class Main {
    public static void main(String[] args) {
       ParkingLot parkingLot = ParkingLot.getInstance(4);
       parkingLot.addParkingSpot(ParkingSpotFactory.createParkingSpot("CAR", 1));
       parkingLot.addParkingSpot(ParkingSpotFactory.createParkingSpot("TRUCK", 2));
       parkingLot.addParkingSpot(ParkingSpotFactory.createParkingSpot("Motorcycle", 3));

       //create vehicle
        Vehicle car = new Car("JK01AB 1234");
        Vehicle truck = new Truck(" JK01BB 1838");
        Vehicle motorcycle = new MotorCycle("JK01AC 9899");

        parkingLot.parkVehicle(car);
        parkingLot.parkVehicle(truck);
        parkingLot.parkVehicle(motorcycle);

    }
}