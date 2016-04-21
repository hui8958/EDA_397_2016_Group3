package chalmers.eda397_2016_group3.slack;

/**
 * Created by Sebastian Blomberg on 2016-04-20.
 */
public interface TrelloApp {
    String getAuthenticationURL();
    String getApplicationID();
    String getApplicationName();
    String getAuthenticationExpiration();
    boolean isAuthenticated();
    String getAuthenticationToken();
}
