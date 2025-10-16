// design a movie booking app
// 1 million req per day
// 500 bytes memeory to store user details,movie deatils
// 10 million * 500 bytes = 5 gb

// functionla requirement
// 1) user show be able to view list of shows in different cities
// 2) user should be able to book show and then make payment
// 3) user should be able to select seat matrix
// 4) system should be able to handle duploicate seat booking

// tables = users,movie
// user = id,name,address, email,mobile
// theater = id,city,
// screens = id, theater_id(fk),name ,total_seats
// movies = id,name,duration,cast_deatils
// booking_deatils = id,user_id,show_id,seat_No,total_payment
// show_details = id,movie_id,screen_id,timestamp
// seats: id,row,col,is_booked,screen_id

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
        List<Threater> threatersInCity = new ArrayList<>();
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
  // 10 per sec
    // refile rate 1 per 100 ms  = 5 per sec


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
        if(currTime - lastRequestTime >= windowSizeMills {
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
// should redierct to original url when passed short url
// non functional req
// highly avalible
// low latency
// base 62 , nd5 hashing, counter based
// base 62 give us more combinations 62^7 genaretes nore combination approx 3.5 trillion
// taokes longUrl and generates random num then generates short url
// tables users= usersId,timestamp,name,email, urlMapping shortUrl, longUrl, userId

 class Base64 {
        String base64Str = "ABCDE....";

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
class CounterBaseApproach{
    private String  base64Char = "ABC.abc..123";
    private int counter = 10000000;
    Map<Integer, String> shortUrls = new HashMap<>();
    Map<String ,String>  longUrlsToShortMapping = new HashMap<>();


     public String  generateShortUrl(int num){
         String  shortUrl = "";
         while(num > 0){
             shortUrl += base64Char.charAt(num%62);
             num = num/ 62;
         }
         if(shortUrl.length() < 7){
             shortUrl += '0';
         }
         shortUrls.put(num, shortUrl);
         return shortUrl;
     }

     public String getShortUrl(int number){
          return shortUrls.get(number);
     }
 }







//design a twitter
// 1 billion active users
// 10 million active user and making 5 tweets  50 million
// assumeing tweet will be taking 50 bytes n 50 milloin * 50  = 25gb
// per sec requests 50 million / 24* 3600  == supposing 6k reqper sec

// functional requirement
/// create a post
// user should follow unfollow
// system shoulf load news feeds on home page
//  like a tweet
// should able to comment on tweet

// entities
// user =  userId, name,email,createdAt
// tweets == id, content ,userId , type,
// followers = usewrId , followeeId   1-> 2, 1-> 3
// comments = id,content, tweetId, userId, createdAt
// likes = id,tweetId,userId


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


    Tweet1(int id ,int  userId, String content){
          this.id = id;
          this.user = userId;
          this.content = content;
    }



}

class Comment {
    String id;
    String content;
    User userId;
    // map of commentid and users who have commebted
   Map<String,ArrayList<User>> comments;

}

// services

class TweetService{
   // auto increment
    int tweetId =1;
    Map<Integer, Tweet>  allTweets = new HashMap<>();
    Map<Integer,List<Tweet>> userTweets = new HashMap<>();



    public ArrayList<Tweet> getUserTweets(int userId){
        return  tweetList.get(userId);
    }



  public void  createTweet(int userId, String content){
        Tweet1  tweet = new Tweet1(tweetId++,userId,content);
        user.getUserTweets(user.id).add(tweet);
    }


}


class FollowingService {
    // ampping of userid ,userid
    Map<Integer,ArrayList<Integer>>  followers  = new HashMap<>();

    public void  addFollower (int userId, int follwerId){
          this.followers.get(userId).add(follwerId);
    }

    public ArrayList<Integer> getFollowers(int userId){
         this.followers.get(userId);
    }

}


//
class feedService {
    User user;
    Map<Integer,ArrayList<Tweet>> cachedFeeds = new HashMap<>();
    FollowingService followingService;
    feedService(User user){
         this.user  = user;
    }

    publiv void pushTweetInUserFeeds (int userId, Tweet tweet){

        ArrayList<Integer> followers = this.followingService.getFollowers(user.id);
        for(int i=0;i<followers.length;i++){
            int user = cachedFeeds.get(followers[i]);
            user.put(tweet);
        }
    }
}









