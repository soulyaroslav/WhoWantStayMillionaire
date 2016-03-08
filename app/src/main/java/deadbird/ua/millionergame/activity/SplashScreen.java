package deadbird.ua.millionergame.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import deadbird.ua.millionergame.R;
import deadbird.ua.millionergame.asyncs.HttpDataHandler;
import deadbird.ua.millionergame.entities.Answer;
import deadbird.ua.millionergame.entities.Question;
import deadbird.ua.millionergame.helpers.Constans;

public class SplashScreen extends Activity {

    public static final int LAYOUT = R.layout.splash_layout;
    // url
    public static final String URL = "http://ec2-52-36-104-50.us-west-2.compute.amazonaws.com/api/game";
    // Views
    private TextView tvLoading, tryAgainst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        //init TextView instance
        tvLoading = (TextView) findViewById(R.id.loading);
        tryAgainst = (TextView) findViewById(R.id.tryAgainst);

        if(isConnected()){
            tvLoading.setText(R.string.loading_text);
            tryAgainst.setText("");
            new HttpRequestTask().execute(URL);
        } else {
            tvLoading.setText(R.string.internet_permission);
            tryAgainst.setText(R.string.try_against);
        }
    }

    public void isHasConnections(View view){
        if(isConnected()){
            new HttpRequestTask().execute(URL);
            tvLoading.setText(R.string.loading_text);
            tryAgainst.setText("");
        } else {
            tvLoading.setText(R.string.internet_permission);
            tryAgainst.setText(R.string.try_against);
        }
    }

    private boolean isConnected(){
        boolean isWifiConnection = false;
        boolean isMobileConnection = false;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for(NetworkInfo networkInfo : networkInfos){
            if(Constans.WIFI.equalsIgnoreCase(networkInfo.getTypeName())){
                if(networkInfo.isConnected()) {
                    isWifiConnection = true;
                }
            }
            if(Constans.MOBILE.equalsIgnoreCase(networkInfo.getTypeName())){
                if(networkInfo.isConnected()) {
                    isMobileConnection = true;
                }
            }
        }
        return isWifiConnection || isMobileConnection;
    }

    class HttpRequestTask extends AsyncTask<String, Void, ArrayList<Question>> {

        // list of questions
        private ArrayList<Question> questions;

        public HttpRequestTask() {
            questions = new ArrayList<Question>();
        }

        @Override
        protected ArrayList<Question> doInBackground(String... params) {
            String stream = null;
            final String url = params[0];
            HttpDataHandler httpDataHandler = new HttpDataHandler();
            stream = httpDataHandler.getJsonData(url);
            // parsing questions
            if (stream != null) {
                Log.d(Constans.TAG, stream);
                try {
                    // Get json array
                    JSONArray inputJsonArray = new JSONArray(stream);
                    for (int i = 0; i < inputJsonArray.length(); i++) {
                        // Get JsonObject from json array
                        JSONObject jsonObject = inputJsonArray.getJSONObject(i);
                        // Get json array (answers) from json object
                        JSONArray answersJsonArray = jsonObject.getJSONArray("answers");
                        ArrayList<Answer> answers = new ArrayList<Answer>();
                        for (int j = 0; j < answersJsonArray.length(); j++) {
                            JSONObject answerJsonObject = answersJsonArray.getJSONObject(j);
                            //
                            Answer answer = new Answer(
                                    answerJsonObject.getInt("id"),
                                    answerJsonObject.getString("body"),
                                    answerJsonObject.getBoolean("is_correct")
                            );
                            answers.add(answer);
                        }
                        Question question = new Question(
                                jsonObject.getInt("id"),
                                jsonObject.getString("body"),
                                answers
                        );
                        questions.add(question);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d(Constans.TAG, "stream is null");
            }

            return questions;
        }

        @Override
        protected void onPostExecute(ArrayList<Question> questions) {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            intent.putExtra(Constans.QUESTIONS, questions);
            startActivity(intent);
            finish();
        }

    }
}
