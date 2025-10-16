public abstract class ParkingsSpot {

    int spotId;
    boolean isAvailable;


    ParkingsSpot(int id) {
        this.spotId =id;
        this.isAvailable = true;
    }

    public abstract boolean   canFitVehicle(Vehicle vehicle);

    public void parkVehicle() {
        this.isAvailable = false;
    }
    public boolean  isAvailable(){
         return this.isAvailable ;
    }

    public void freeVehicle() {
         this.isAvailable = true;
    }
}
