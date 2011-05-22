package se.su.dsv.scipro.android.tasks;

public class LoginResult {
    public boolean authenticated;
    public long userid;
    public String username;
    public String apikey;

    public LoginResult(boolean authenticated, String username, String apikey, long userid) {
        this.authenticated = authenticated;
        this.username = username;
        this.apikey = apikey;
        this.userid = userid;
    }
}