// design a movie booking app
// 1 million req per day
// 500 bytes memeory to store user details,movie deatils
// 10 million * 500 bytes = 5 gb

// functionla requirement
// 1) user show be able to view list of shows in different cities
// 2) user should be able to book show and then make payment
// 3) user should be able to select seat matrix
// 4) system should be able to handle duplicate seat booking

// tables = users,movie
// user = id,name,address, email,mobile
// theater = id,city,
// screens = id, theater_id(fk),name ,total_seats
// movies = id,name,duration,cast_deatils
// booking_details = id,user_id,show_id,total_payment,status
// show_details = id,movie_id,screen_id,timestamp
// seats: id,row,col,screen_id,seat_type
// Show_Seats: id, show_id,seat_id,booking_id,status,price
// seat_type_pricing: id,type,price
//Concurrency Control: By having a Show_Seats table, you can use database locks when a user selects a seat.
//This prevents two people from clicking "Pay" on the same seat at the exact same millisecond.
//Create a Show_Seats table to track seat availability per show.

//  show_seats  table  to handle double booking
// show_seat_id (PK) ,seat_id (FK) ,status (AVAILABLE, HOLD, BOOKED) hold_until (for temporary locks)

// booking_seats booking_seat_id (PK),booking_id (FK), seat_id (FK)

import javax.swing.*;
import java.sql.Time;
import java.util.*;
import java.util.HashMap;

class User {
    int id;
    String name;
    String email;
    String address;

    User( String name,
    String email,
    String address,){
        this.name = name;
        this.address = address;
        this.email = email;
    }


    public int getUserId(){
        return this.getUserId();
    }
}

class Threater {
    int id;
    String city;
    List<Screens> screen = new ArrayList<>();


    Threater(String city){
        this.city =city;
        this.screen = new ArrayList<>();
    }

    public String getCity(){
        return  this.city;
    }

}

class  Screens{
    int id;
    Movie movie;
    List<Seat> seats;

    Screens(Movie movie){
        this.movie = movie;
        this.seats = new ArrayList<>();
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public List<Seat> getBookedSeats(){
        List<Seat>  alreadyBooked = new ArrayList<>();
        for(int i=0;i< seats.size();i++){
            if(seats.get(i).isBooked){
                alreadyBooked.add(seats.get(i));
            }
        }

        return alreadyBooked;

    }
}


class  Movie {
    int id ;
    String movie_name;
    int duration;
    String cast;

    Movie(String movie_name,
    int duration,
    String cast){
        this.movie_name = movie_name;
        this.duration = duration;
        this.cast = cast;
    }

    public String getCast(){
        return this.cast;
    }


}

class  Show{
    int id;
    Movie movie;
    Threater threater;
    int duration;


    Show(Movie movie,Threater threater){
        this.movie = movie;
        this.threater = threater;
    }




}
class  Seat{
    int id;
    String  rowNo;
    int col;
    boolean isBooked;


    public int getId() {
        return this.id;
    }
    public void book(){
        this.isBooked = true;
    }


}

class  Booking {

}
// servcies
class BookingService {
    int bookingCounter = 1;
    Show show;
    List<Booking> allBookings = new ArrayList<>();


    public List<Seat> bookShow(Show show, User user, List<Seat> requestedSeats) {
        synchronized (show) {


        for (Seat seat : requestedSeats) {
            if (seat.isBooked()) {
                System.out.println("Seat " + seat.getId() + " already booked!");
                return null;
            }
        }
        // book seats
        for (Seat seat : requestedSeats) seat.book();
        synchronized (this) {
            Booking booking = new Booking(bookingCounter++, user, show, requestedSeats);
            allBookings.add(booking);
            System.out.println("Booking " + booking.getId() + " confirmed for movie " + show.getMovie().getTitle());
            return booking;
        }

    }
    }



}


class ThresterSercvice  {
    List<Threater> threaterList = new ArrayList<>();

    ThresterSercvice(List<Threater> list){
        this.threaterList = list;
    }

    public List<Threater> getThreaterListBasedOnLocation(String city){
        List<Threater>  threatersInCity = new ArrayList<>();
        for(Threater threater: threaterList){
           if(threater.getCity() == city){
               threatersInCity.add(threater);
           }
        }

       return threatersInCity;
    }

    public List<Seat>  getSeatMatrix(Screens screenId, Show showId){


    }



}



////design rate limiter
// token bucket ,fixed window , sliding window,
// function requirement
// system should limit the req if made by user more than threshold
// should be able to change algorithm at run time
//
class TokenBaserateLimiter{
    Map<Integer,Integer> tokenUsedByUser = new HashMap<>();
    Map<Integer, Long > lastReffiledTimeStamp = new HashMap<>();
    int capacity = 5;
    double refillRate;
  // 2 per sec
    // refile rate 0.2 per 100 ms  = 2 per sec
//    capacity = 5
//    refillRate = 2 tokens per second
//(User gets 2 new tokens every full second)
//Case: User fires requests rapidly
//  User makes 5 requests fast.
//Request 1 → tokens = 4
//Request 2 → tokens = 3
//Request 3 → tokens = 2
//Request 4 → tokens = 1
//Request 5 → tokens = 0
//Now bucket is empty.
//  Still 0 seconds passed, so:
//elapsedTime = 0
//    no refill
 // Next request at time = 0.3 seconds
    // elapsedTime = 0   (because 300ms / 1000 = 0)
   // still 0 tokens → request denied
 // At time = 1.0 second
    //Now 1000 ms have passed.
    //elapsedTime = 1
    //refill = 1 * 2 = 2 tokens
          //  tokens = 2
    


    TokenBaserateLimiter(int capacity, double refillRate) {
      this.capacity = capacity;
      this.refillRate = refillRate;
    }

    public boolean allowRequest(int userId) {
        long currentTime  =  System.currentTimeMillis();
        lastReffiledTimeStamp.putIfAbsent(userId, currentTime);
        tokenUsedByUser.putIfAbsent(userId,capacity);
        long lastRefillTime  = lastReffiledTimeStamp.get(userId);
        long elapsedTime = (currentTime - lastRefillTime) / 1000;
        // this 0 can be changed if you my refile rate
        if(elapsedTime > 0){
            int newTokens = Math.min(capacity, tokenUsedByUser.get(userId) + (int) (elapsedTime * refillRate));
            tokenUsedByUser.put(userId,newTokens);
            lastReffiledTimeStamp.put(userId, currentTime);

        }

        if(tokenUsedByUser.get(userId) > 0){
            tokenUsedByUser.put(userId, tokenUsedByUser.get(userId) -1);
            return true;
        }

        return false;

    }
}


class FixedSizeWindow{
   private  int limit ;
   private Map<Integer, Integer> tokensUsed ;
   private Map<Integer, Long> startWindowTimeStamp ;
   private int  windowSizeMills;


    FixedSizeWindow(int limit){
          this.limit = limit;
          this.windowSizeMills = System.currentTimeMillis()  ;
          tokensUsed = new HashMap<>();
          startWindowTimeStamp = new HashMap<>();

    }


   public boolean allowRequest(int userId){
        long currTime = System.currentTimeMillis();
        tokensUsed.putIfAbsent(userId,0);
        startWindowTimeStamp.putIfAbsent(userId, currTime);
        long lastRequestTime = startWindowTimeStamp.get(userId);
        if(currTime - lastRequestTime >= windowSizeMills ){
              startWindowTimeStamp.put(userId, currTime);
              tokensUsed.put(userId,0);
        }

        if(currTime - lastRequestTime <  windowSizeMills &&  tokensUsed.get(userId) < limit ){
            tokensUsed.put(userId, tokensUsed.get(userId) +1);
            return true;
        }

        return false;

   }



}


// Slinding window
class SlidingWindow {
    private int requestAllowed;
    private int windowSize;
    private Map<Integer, Deque<Long>> requestLogs;

    SlidingWindow(int requestAllowed, int windowSize) {
        this.requestAllowed = requestAllowed;
        this.windowSize = windowSize;
        requestLogs = new HashMap<>();
    }


    public boolean allowRequest(int userId){

        long currentTime = System.currentTimeMillis();
        requestLogs.putIfAbsent(userId, new LinkedList<>() );
        Deque<Long> lastRequestes = requestLogs.get(userId);
        while(!requestLogs.isEmpty() && currentTime - lastRequestes.peek() >= windowSize){
            lastRequestes.pollFirst();
        }
        if(lastRequestes.size() < requestAllowed){
            lastRequestes.push(currentTime);
            return true;
        }

        return false;
    }
}






///// design url shortner
// 10 million requests  per months
// per sec = 10 million / 24 * 60* 60 =
    // data usage per month per url = 10 million * 1 kb * 0.5 kb * 0.5 = 10 milion  * 2kb = 20 gb
// function requirement
// system shoulf be able to generate short url
// should be ablr to genarte short url of partilculat length
// should redirect to original url when passed short url
// non functional req
// highly avalible
// low latency
// base 62 , nd5 hashing, counter based
// base 62 give us more combinations 62^7 genaretes nore combination approx 3.5 trillion
// takes longUrl and generates random num then generates short url
// tables users = usersId,timestamp,name,email, urlMapping shortUrl, longUrl, userId

 class Base64 {
        String base64Str = "ABCDE....abcd...12345...";

        public String  generateShortUrl(int num){
            String  shortUrl = "";
            while(num > 0){
                shortUrl += base64Str.charAt(num%62);
                num = num/ 62;
            }
            if(shortUrl.length() < 7){
                shortUrl += '0';
            }
            return shortUrl;
        }
 }


 // counter base appraoch
 public class URLService {
     HashMap<String, Integer> ltos;
     HashMap<Integer, String> stol;
     static int COUNTER=100000000000;
     String elements;
     URLService() {
         ltos = new HashMap<String, Integer>();
         stol = new HashMap<Integer, String>();
         COUNTER = 100000000000;
         elements = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";    }
     public String longToShort(String url) {
         String shorturl = base10ToBase62(COUNTER);
         ltos.put(url, COUNTER);
         stol.put(COUNTER, url);
         COUNTER++;
         return "http://tiny.url/" + shorturl;
     }
     public String shortToLong(String url) {
         url = url.substring("http://tiny.url/".length());
         int n = base62ToBase10(url);
         return stol.get(n);
     }

     public int base62ToBase10(String s) {
         int n = 0;
         for (int i = 0; i < s.length(); i++) {
             n = n * 62 + convert(s.charAt(i));
         }
         return n;

     }
     public int convert(char c) {
         if (c >= '0' && c <= '9')
             return c - '0';
         if (c >= 'a' && c <= 'z') {
             return c - 'a' + 10;
         }
         if (c >= 'A' && c <= 'Z') {
             return c - 'A' + 36;
         }
         return -1;
     }
     public String base10ToBase62(int n) {
         StringBuilder sb = new StringBuilder();
         while (n != 0) {
             sb.insert(0, elements.charAt(n % 62));
             n /= 62;
         }
         while (sb.length() != 7) {
             sb.insert(0, '0');
         }
         return sb.toString();
     }






//design a twitter
// 1 billion active users
// 10 million active user and making 5 tweets  50 million
// assuming tweet will be taking 50 bytes n 50 million * 50  = 25gb
// per sec requests 50 million / 24* 3600  == supposing 6k req per sec

// functional requirement
/// create a tweet
// user should follow unfollow
// system should load news feeds on home page
//  like a tweet or comment
// should able to comment on tweet

// entities
// user =  userId, name,email,createdAt
// tweets == id, content ,userId , type,
// followee_follower =    followeeId , followerId  1-> 2, 1-> 3
// comments = id,content, tweetId, userId, createdAt
// likes = id,parentId,type,userId
// feeds =  below are columns
// id
//userId   → for whom the feed is generated
//tweetId  → tweet that should appear in the feed
//createdAt
// user_settings
  //   userId,
 //    isCelebrity (boolean),
  //   followerCount,
   //  feedStrategy (PUSH / PULL / HYBRID)

 //    timeline_cursor
     //    userId
     //followeeId
     //lastFetchedTweetId




     class User1 {
    String name;
   public  int id;
    String email;


    Map<User,ArrayList<Tweet>> tweetList = new HashMap<>();



   public String  getUsername(){
       return this.name;
    }

}

class Tweet1{
    int id;
    String content;
   // User user;
    int userId;
    List<Comment> comments;


    Tweet1(int id ,int  userId, String content){
          this.id = id;
          this.userId = userId;
          this.content = content;
          this.comments = new ArrayList<>();
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }




}

class Comment {
    public static String id ;
    String content;
    int  userId;
   // User userId;
    // map of commentid and users who have commebted
//   Map<String,ArrayList<User>> comments;

    Comment(String content, int userId){
          this.id +=1;
          this.content = content;
          this.userId = userId;
    }

}

// services

class TweetService{
   // auto increment
    int tweetId =1;
    Map<Integer, Tweet1>  allTweets = new HashMap<>();
    Map<Integer,ArrayList<Tweet1>> userTweets = new HashMap<>();
    //stores tweetId and timestamp
    Map<Integer, Long> sortedTweets = new HashMap<>();



    public ArrayList<Tweet1> getUserTweets(int userId){
        return  userTweets.get(userId);
    }



  public Tweet1  createTweet(int userId, String content){
        Tweet1  tweet = new Tweet1(tweetId++,userId,content);
        allTweets.put(tweet.id,tweet);
        userTweets.putIfAbsent(userId,new ArrayList<>());
        userTweets.get(userId).add(tweet);
        sortedTweets.putIfAbsent(tweetId, System.currentTimeMillis());
        // add tweet post event in kafka or queue in order to store or load tweet to followerr
        

        return tweet;
    }

    public ArrayList<Tweet1> getRecentTweets(int userId, int pageNo) {
         ArrayList<Tweet1> ans = new ArrayList<>();
         final int limit = 20;
         int startIndex = limit * (pageNo - 1);
         int endIndex = pageNo * limit;
         if(endIndex > userTweets.size()){
             endIndex = userTweets.size();
         }

         if(startIndex < 0 || startIndex > endIndex){
             return ans;
         }

         for(int i= startIndex; i < endIndex;i++){
              Tweet1 currTweet = userTweets.get(userId).get(i);
              ans.add(currTweet);
         }
         return ans;

    }

    public void  addComment(int userId, String commentContent,int tweetId){
         Tweet1 tweet1 = allTweets.get(tweetId);
         Comment comment = new Comment(commentContent,userId);
         tweet1.addComment(comment);
    }
}


}


class FollowingService {
    // ampping of userid ,userid
    Map<Integer,ArrayList<Integer>>  followers  = new HashMap<>();  // userId and there followers

    // who this user follows
    Map<Integer, ArrayList<Integer>> following = new HashMap<>();

    public void  follow (int followeeId, int followerId){
          followers.putIfAbsent(followeeId, new ArrayList<>());
          followers.get(followeeId).add(followerId);
          following.putIfAbsent(followerId,new ArrayList<>());
          following.get(followerId).add(followeeId);
    }

    public void unfollow(int followeeId, int followerId){
        if(followers.containsKey(followeeId)) {
            followers.get(followeeId).remove(followerId);
        }
        if(following.containsKey(followerId)){
            following.get(followerId).remove(followeeId)
        }

    }

    public Set<Integer> getFollowers(int userId){
         followers.get(userId);
    }

}


//
class feedService {
    User user;
    Map<Integer,List<Tweet1>> cachedFeeds = new HashMap<>();
    FollowingService followingService;
    TweetService tweetService;

    feedService(User user){
         this.user  = user;
    }

    public void pushTweetToFoloowers (int authorId, Tweet1 tweet){

        Set<Integer> followers = this.followingService.getFollowers(authorId);
        for(int followerId: followers){
            cachedFeeds.putIfAbsent(followerId, new ArrayList<>());
            cachedFeeds.get(followerId).add(0,tweet);
        }
    }
    public List<Tweet> getFeed(int userId) {
        return  cachedFeeds.getOrDefault(userId, new ArrayList<>());
    }

    // pull mechanism
    public void pullFeeds(int userId) {
       ArrayList<Integer> following  = followingService.following.get(userId);
       for(int authorId: following){
       List<Tweet1>  recentTweets = this.tweetService.getUserTweets(authorId);


       }


    }
}





// design a hotel booking app
// 100 million requests  per day
// 10, 00000 million / 24 * 36 =  10, 00000 / 844 = 10, 000 / 4
// 50 bytes * 100 million  = 5gb per month


//functional requiremnt
// 1) user should be see list of hotels based on location
// 2) user should be able to book room whoch is available
// 3) user should be able to cancel the room
// 4) user should be able to make payments

// user -> userId,name,email,lon,lat,address
// hotels -> id,name,lon.lat,address
// room -> id,hotelId,isBooked,roomNo,status
// order_deatail->booking_id, roomId,userId,bookedAt,no_of_rooms,total_amount,check_in,check_out,status
// payment -> id,status,amount
// booking_seats -> booking_id, room_id
// A booking can contain multiple rooms, and a room can be booked multiple times over time.










































// practice design a parkinglot
// 100 million  per montb = 100 million  / 24 * 3600  = 1,000,000 / 800 + 45 = 5000 approx
// memory 100 * 50 bytes = 5000 million = 5 gb
// functiion requiremnt
// system should allocate parking spot to differnt vehicle
// should park on spot of is is vacaant other wise should show error
// shouled be able to park differnr vehicle
// should assign id to parking spot
// should allow to create only one instance


public  abstract class Vehicle{
    String numberPlate;


    public String getNumberPlate(){
         return numberPlate;
    }

    public Vehicle(String licensePlate) {
        this.numberPlate = licensePlate;
    }
}

public class Car extends  Vehicle{


    Car(String numberPlate){
        super(numberPlate);
    }
}


public class MotorCycle extends  Vehicle{


    MotorCycle(String numberPlate){
        super(numberPlate);
    }
}

public class Truck extends  Vehicle{


    Truck(String numberPlate){
        super(numberPlate);
    }
}

public class ParkingSpot{
    int id;
    Boolean isAvailable;

    ParkingSpot(int id){
         this.id = id;
         this.isAvailable = true;
    }

    public abstract boolean canFitVehicle(Vehicle vehicle);
    public void parkVehicle(Vehicle vehicle){
        this.isAvailable = false;
    }

    public void freeVehicle(Vehicle vehicle){
        this.isAvailable = true;
    }
}

public class CarParkingSpot extends  ParkingSpot {

    CarParkingSpot(int id){
        super(id);
    }

    @Override
    public boolean canFitVehicle(Vehicle vehicle) {
        return vehicle instanceof  Car;
    }


}

public class TruckParkingSpot extends  ParkingSpot {

    TruckParkingSpot(int id){
        super(id);
    }

    @Override
    public boolean canFitVehicle(Vehicle vehicle) {
        return vehicle instanceof  Car;
    }


}




class ParkingLot {
    ParkingLot instance;
    ArrayList<ParkingSpot> spots;
    Map<Vehicle, ParkingSpot> parkedVehilces;
    int capacity;


    ParkingLot(int capacity){
         this.capacity = capacity;
         spots = new ArrayList<>();
         parkedVehilces = new HashMap<>();
    }
   public void synchonised getInstance(int capacity){

        if(instance == null){
            return new ParkingLot(capacity);
        }
        return instance;
    }


    public void parkVehicle(Vehicle vehicle){

       for (ParkingSpot spot: spots){
           if(spot.isAvailable && spot.canFitVehicle(vehicle)){
               parkedVehilces.put(vehicle, spot);
               spot.parkVehicle(vehicle);
               return true;

           }
       }
    }

    public boolean addParkingSpot(ParkingSpot spot){
       if(spots.size() < capacity){
             spots.add(spot);
             return  true;
       }

       return false;
    }


    public int getAvailableSpots() {
        int availableSpots = 0;
        for (ParkingSpot spot : spots) {
            if (spot.isAvailable()) {
                availableSpots++;
            }
        }
        return availableSpots;
    }






}


public  class ParkingLotFactory{

      public static ParkingSpot  createSpot(String type, int id ){

        switch (type.toLowerCase()){
            case "Car":
                return new CarParkingSpot(id);
            case "Truck":
                return new TruckParkingSpot(id);
            case "Motor":
                return new MotorCycleParkingSpot(id)

        }
    }
}





public class main{
    ParkingLotFactory parkingLotFactory = new ParkingLotFactory.create("Car, 1");
}








public class WeatherSystem{
    List<Device> devices;
    String name;

    WeatherSystem(String name){
         this.name = name;
         this.devices = new ArrayList<>();
    }

    public void addDevice(Device device){
      devices.add(device);
    }

    public void changeInTemperature(){
      for(devices: device){
          device.notify(12);
      }
    }


}

 interface Device{

     String getDeviceName();
     void notiyTemperatureChange();
}

public MobileDevice implements Device{
      String deviceName;
        MobileDevice(String name){
            this.deviceName = name;
        }
    public String getDeviceName(){
        return this.deviceName;
        }

        public void  notiyTemperatureChange(int temp){
            System.out.println("temprature changed to" + temp);
        }


        }





