package se.su.dsv.scipro.android.tasks;

/**
 * User: patrick
 * Date: 2011-05-22
 * Time: 03:06
 */
public interface LoginResponder {
    public void loggingIn();

    public void loggedIn(LoginResult result);
}

