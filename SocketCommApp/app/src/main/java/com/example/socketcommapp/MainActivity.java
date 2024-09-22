package com.example.socketcommapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonSocket = findViewById(R.id.button_socket);
        Button buttonHttp = findViewById(R.id.button_http);
        Button buttonCheckNetwork = findViewById(R.id.button_check_connection);
        textViewResult = findViewById(R.id.textView_result);

        // Checking your network connection
        buttonCheckNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewResult.setText("Проверка подключения...");

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isNetworkAvailable()) {
                            textViewResult.setText("Подключение к сети доступно");
                        } else {
                            textViewResult.setText("Нет подключения к сети");
                        }
                    }
                }, 3000);
            }
        });

        buttonSocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RequestSocketTask().execute();
            }
        });

        buttonHttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpGetRequestTask().execute("https://jsonplaceholder.typicode.com/posts/1");
            }
        });
    }

    // Method to check if there is a network connection
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // Asynchronous task for working with sockets
    private class RequestSocketTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Socket requestSocket = new Socket("time.nist.gov", 13);
                BufferedReader br = new BufferedReader(new InputStreamReader(requestSocket.getInputStream()));

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line).append("\n");
                }

                requestSocket.close();
                return response.toString();
            } catch (Exception e) {
                Log.d("Socket Request", "Failed: " + e.getMessage());
                return "Failed to connect via Socket";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            textViewResult.setText(result);
        }
    }

    // Asynchronous task to perform an HTTP GET request
    private class HttpGetRequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0];
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    return response.toString();
                } else {
                    return "Failed to fetch data. Response code: " + responseCode;
                }
            } catch (Exception e) {
                Log.e("HTTP GET", "Error: " + e.getMessage());
                return "Failed to fetch data via HTTP GET";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            textViewResult.setText(result);
        }
    }
}
