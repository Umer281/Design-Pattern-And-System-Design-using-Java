public interface Ratelimiter {
    boolean allowRequests(String userId);
}
