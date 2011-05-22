package se.su.dsv.scipro.android.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class StringUtils {

    public static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n = 0;
                while ((n = reader.read(buffer)) != -1)
                    writer.write(buffer, 0, n);
            } catch (Exception e) {
                
            } finally { 
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }
}
