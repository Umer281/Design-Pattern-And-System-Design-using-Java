import java.time.LocalDateTime;
import java.util.*;

public class Twitter {
  private   Map<String, User> users;
  private   Map<Long, Tweet > tweets;
  private  CacheService cacheService;


  Twitter(){
      this.users = new HashMap<>();
      this.tweets = new HashMap<>();
      this.cacheService = new CacheService();

  }

  public void  createTweet(userId, content){
      User user = users.get(userId);
      Tweet tweet = new Tweet(user, content, LocalDateTime.now());
      tweets.put((long) tweet.getId(), tweet);
      user.addTweet(tweet);

      //cache tweet
  }

  public void addFollower(String followerId, String followeeId){
        User follower = users.get(followerId);
        User followee = users.get(followeeId);
        followee.add(follower);

  }


  public void likeTweet(String tweetId, String userId){
      Tweet tw = tweets.get(tweetId);
      tw.addLike(user);
  }




}

public class User {
    String userId;
    String name;
    String username;
    List<Tweet> tweets;
    Set<User> followers;
    Set<User> following;

     User(){
         this.followers = new HashSet<>();
         this.following = new HashSet<>();
     }
   public void  addTweet(Tweet tweet){
         tweets.add(tweet);
    }
    public void addFollower(User follower){
         followers.add(follower);
    }
    public void follow(User user){
     followee.add(user);
    }

}


public class Tweet {
     String id;
     User user;
     String content;
     List<String> comments;
     Set<User> likes;

     Tweet(User user, String text, LocalDateTime createdAt){
        this.id = IdGenerator.generateId();
        this.user = user;
        this.content = text;
        this.likes = new HashSet<>();
        this.comments = new ArrayList<>();
     }

    addLike(User user){
       likes.add(user);
    }
    addComment(String comment){
         comments.add(comment);
    }

    public String getId(){
         return this.id;
    }
}


public class CacheService{
    Map<String, List<Tweet>> cachedTweets;

    CacheService(){
         this.cachedTweets = new HashMap<>();
    }

    getCachedTweets(){

    }

    pushTweet(tweetId, userId){
        List<String> recentTweets =  cachedTweets.getOrDefault(userId, new ArrayList<>());
        if(recentTweets.size() < 200){
            recentTweets.add(0,tweetId);
            cachedTweets.put(userId,recentTweets);
        }else{
            recentTweets.remove(recentTweets.size() -1);
        }

    }

}




// 1 billion users
// daily active users could be 200 million =n 20 crores
// 5 * 200 million  = 1 billion tweets considering 80% text, 20% media
// 50 bytes * 5 * 200 million , 1tb

// function requirements
// user should be able to post tweet
// user should be able to follow unfollow another user
// user should be able to see feeds (the tweets done by followers)
// user should be able to update the profile

//users = id,username, email,contact_No
//tweets = user_id,tweet_text,likes, timestamp, s3Url
//following_details = followerId, followee_id
//feeds -- push based and pull based































// Design a parking lot
// functional requrment
// 1) user should be able to able to book parking lot
// 2) system should support parking lots  different types like for car,motocyle etc
// 3) after user parks an parking id should be asssigned
// 4) system should be able to see if parking space is available

// tables = > vehicle_details = {id, vehicle_no,timestamp, spot_id}
// history = {id, vehicle_no,type,parked_at,parked_out,spot_id}
// parking_spot = {id,type,isAvaliable}


public class ParkingLot {

    ParkingLot instance;
    int capacity;
    List<ParkingSpot> parkingSpotList;
    Map<Vehicle, ParkingSpot> parkedVehicles;




    ParkingLot(int capacity){
        this.capacity = capacity;
        this.parkingSpotList = new ArrayList<>();
        this.parkedVehicles = new HashMap<>();

    }
    public static synchronized ParkingLot getInstance(int capacity){
        if(instance == null){
            return new ParkingLot(capacity);
        }
        return  instance;
    }

    public boolean parkVehicle(Vehicle vehicle){
       for(ParkingsSpot spot: parkingSpotList){
           if(spot.checkAvailabity() && spot.canFitVehicle()){
               spot.parkVehicle();
               parkedVehicles.put(vehicle,spot);
           }
       }
    }



}

public abstract  class ParkingSpot{
  protected   boolean isAvailable;
    protected  int id;

    ParkingSpot(int id ){
        this.id = id;
        this.isAvailable = true;
    }
    public boolean checkAvailabity(){
       return this.isAvailable;
    }

    public abstract boolean canFitVehicle(Vehicle vehicle);

    public void freeSpot(){
        isAvailable = true;
    }

    public void parkVehicle(Vehicle vehicle){
        isAvailable = false;
    }
}

public class CarParkingSpot extends ParkingsSpot{

    CarParkingSpot(int spotId){
        super(spotId);
    }
    public boolean canFitVehicle(Vehicle vehicle){
        return vehicle instanceof Car;
    }
}

public class MotorParkingSpot extends ParkingsSpot{

    MotorParkingSpot(int spotId){
        super(spotId);
    }
    public boolean canFitVehicle(Vehicle vehicle){
        return vehicle instanceof MotorCycle;
    }
}



public class Vehicle {
    String vehicleNumber;

    Vehicle(String vehcileNumber){
         this.vehicleNumber = vehcileNumber;
    }

}


public Car extends Vehicle{


    Car(String vehicleNumber){
        super(vehicleNumber);
        }
}
public MotorCycle extends Vehicle{
        MotorCycle(String vehicleNumber){
        super(vehicleNumber);
        }
        }




        class HashMap<K,V> {

         private static int size =16;
         private Entry<K,V>[] buckets;

         static class Entry<K,V>{
                K key;
                V value;
                Entry<K,V> next;

                public Entry(K key, V value){
                    this.key =key;
                    this.value = value;
                    this.next = null;
                }
            }
         HashMap(){
             this.buckets = new Entry[size];
         }

         public int getIndex(K key){
             return key.hashCode() % size;
         }

       public void   putValue(K key,V value ){
                int index = getIndex(key);
                Entry<K,V> existing  = buckets[index];
                if(existing == null){
                    buckets[index] = new Entry<>(key,value);
                }else{

                    while(existing !=null){

                        if(existing.key.equals(key)){
                            existing.value = value;
                            return;
                        }else{
                            if(existing.next == null){
                                 existing.next = new Entry<>(key,value);
                                 return;
                            }
                            existing = existing.next
                        }
                    }
                }

         }

        }






/// Design a lld of food delivery app
//client app, admin app,rider app
//  functionaltional requirement s
// 1) user should be able to get list of resturants based location
// 2) user should be able get list of menu_items
// 3) user should be able to place order and make payment
// 4) user should be able to add items in cart
// 5) system should assign order to nearest rider
// 6) track the rider location realtime location

//storage 100 million
// 10 million * 500 bytes = 5gb memory per day
// 10 million / 24 * 60 * 60 == x req sec
// api design
// /api/v1/resuturantList?lon = ?n& lat =?   // return list of resturamts
// /api/v1/menulist/restuyrantId
// /api/v1/createOrder/orderId
// /api/v1/makePayment/orderId
// /api/v1/addItems to cart
// /api/v1/assignToRider
// /api/v1/orderDetails



// tables
//users = id,name.address, emial, lon, lat, geoHash
// resturants = id,name,address,isActive,lat,lon, geoHash
// menu_items = id, resturant_id,name,price ,available
// orders = id,resturant_id, userId,riderId, status, total_price ,payment_status,order_status
// rider = id,name,isFree,lon,lat
//cart_item= id,userId,items -json
// payments = id,order, status
// order_items = id,orderId, item_id, qty

// qzab12, qzab13

class Location {
    Double lon;
    Double lat ;

    Location(Double lon, Double lat ) {
         this.lon = lon;
         this.lat = lat;
    }


    public Double getLon(){ return this.lon}
    public Double getLat(){ return this.lat}
}


class User {
    int id;
    String name;
    String address;
    String email;
    String mobile;
    Location location;


    User(String name,
    String address,
    String email,
    String mobile,
    Location location){
         this.name = name;
        this.address = address;
        this.email =  email;
        this.mobile =  mobile;
        this.location = location;
    }

    public Location getLocation() {
        return this.location;
    }
}

class Resturant{
    int id;
    String name;
    Location location;
    List<MenuItems>  menu;


    Resturant(int id,
    String name,
    Location location){
         this.id = id;
        this.name =  name;
        this.location =  location;
        List<MenuItems>  menu = new ArrayList<>();
    }

     public Location getLocation(){
        return this.location;
     }
    public List<MenuItems> getMenuItems (){
        return this.menu;
    }

}

class MenuItems {
  int id;
  String name;
  Double price;
    public double getPrice() { return price; }
}

 class Rider{
    int id;
    String name;
    Location location;
    String status;



     public int getId() { return id; }
     public Location getLocation() { return location; }
     public void setLocation(Location loc) { this.location = loc; }
     public String  getStatus() { return status; }
     public void setStatus(String status) { this.status = status; }
}

public enum OrderStatus {
    CREATED, ACCEPTED, PREPARING, PICKED_UP, DELIVERED
}
class Order{
    int id;
    private User user;
    private Resturant resturant;
    private List<MenuItems> items;
    private String orderStatus;
    private Rider assignedRider;
    public Order(int id, User user, Resturant restaurant, List<MenuItems> items) {
        this.id = id;
        this.user = user;
        this.resturant = restaurant;
        this.items = items;
        this.orderStatus = OrderStatus.CREATED;
    }

    public int getId() { return id; }
    public Resturant getRestaurant() { return resturant; }
    public OrderStatus getStatus() { return orderStatus; }
    public void setStatus(OrderStatus status) { this.orderStatus = status; }
    public void setAssignedRider(Rider rider) { this.assignedRider = rider; }
    public Rider getAssignedRider() { return assignedRider; }

}

public enum RiderStatus {
    AVAILABLE, ASSIGNED, DELIVERING
}
class RiderService {
    private List<Rider> riders;


    RiderService(List<Rider> riders){
        this.riders = riders;
    }


    public Rider assignRider(Order order){
        double  min_dist = Integer.MAX_VALUE;
        Rider nearest = null;

        for(Rider rider: riders){
            if(rider.getStatus() == RiderStatus.AVAILABLE){
                double dist = distance(order.getRestaurant().getLocation(),rider.getLocation());
                if(dist < min_dist){
                    min_dist = dist;
                    nearest = rider
                }
            }
        }

        if(nearest != null){
            nearest.setStatus(RiderStatus.ASSIGNED);
        }
        return nearest;
    }



    public double distance(Location l1, Location l2){
        return Math.sqrt(Math.pow(l1.getLat() - l2.getLat(), 2) + Math.pow(l1.getLon() - l2.getLon(), 2) )
    }



}


class TrackingService{
    Map<Integer,Location> riderLocations = new HashMap<>();


    public void  updateRiderLocation(int riderId, Location location) {
        riderLocations.put(riderId,location);
    }

    public Location getRiderLocation(int riderId){
        return riderLocations.get(riderId);
    }
}


class OrderService {
    private RiderService riderService;
    TrackingService trackingService;
    private int orderCount = 1;

    public Order createOrder(User user,Resturant resturant,List<MenuItems> items){
        Order order = new Order(orderCount, resturant,user);
        order.setStatus(OrderStatus.ACCEPTED);

        Rider rider = riderService.assignRider(order);
        return order
    }

    public void updateOrderStatus(Order order, OrderStatus status) {
        order.setStatus(status);
        System.out.println("Order " + order.getId() + " status updated to " + status);
    }

    public Location trackOrder(Order order){
        if(order.getAssignedRider() !=null){
            return trackingService.getRiderLocation(order.getAssignedRider().getId())
        }
        return null;
    }
}
















