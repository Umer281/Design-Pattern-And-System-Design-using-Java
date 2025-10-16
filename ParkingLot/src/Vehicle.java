public abstract class Vehicle {
    String plateNumber;

    Vehicle(String plateNumber){
         this.plateNumber = plateNumber;
    }

    public String getPlateNumber(){
        return this.plateNumber;
    }
}
