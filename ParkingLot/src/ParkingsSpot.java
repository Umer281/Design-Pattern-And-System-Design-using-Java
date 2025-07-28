public class ParkingsSpot {

    int id;
    boolean isAvailable;

    ParkingsSpot(int id) {
        this.id =id;
        this.isAvailable = true;
    }


    public void parkVehicle(){
         this.isAvailable = false;
    }

    public void freeVehicle() {
         this.isAvailable = true;
    }
}
