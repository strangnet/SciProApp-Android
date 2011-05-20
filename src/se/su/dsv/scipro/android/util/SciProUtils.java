package se.su.dsv.scipro.android.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import se.su.dsv.scipro.android.activity.PrivateMessages;
import se.su.dsv.scipro.android.activity.SupervisorHome;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SciProUtils {
    
    public static final String TAG = "SciProUtils";
    
    public static void openHomeActivity(Context context) {
        final Intent intent = new Intent(context, SupervisorHome.class);
        context.startActivity(intent);
    }
    
    public static void openMessagesActivity(Context context) {
        final Intent intent = new Intent(context, PrivateMessages.class);
        context.startActivity(intent);
    }
    
    public static String hash(String plaintext) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(plaintext.getBytes("UTF-8"));
            return String.valueOf(Base64Coder.encode(md.digest()));
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "SHA-1 support is required.");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Unexpected exception", e);
        }
        
        return "";
    }
    
    public static String apiKeyGen(String username) {
        String timehash = hash(String.valueOf(System.currentTimeMillis()));
        String salthash = hash("armysurplusst0re");
        String seed = username + salthash + timehash;
        String apikey = hash(seed);
        return apikey;
    }
}
