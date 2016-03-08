package deadbird.ua.millionergame.asyncs;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import deadbird.ua.millionergame.helpers.Constans;

public class HttpDataHandler {

    public static String stream = null;

    public String getJsonData(String url){
        try {
            URL jsonUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) jsonUrl.openConnection();

            if(connection.getResponseCode() == 200){
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                StringBuilder builder = new StringBuilder();
                String line;
                while((line = br.readLine()) != null){
                    builder.append(line);
                }
                stream = builder.toString();
                connection.disconnect();
            } else {
                Log.d(Constans.TAG, "Respond code is not equals 200");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stream;
    }
}
