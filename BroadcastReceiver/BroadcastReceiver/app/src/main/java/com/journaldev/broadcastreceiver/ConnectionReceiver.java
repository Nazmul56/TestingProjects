package com.journaldev.broadcastreceiver;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by anupamchugh on 11/04/16.
 */



public class ConnectionReceiver extends BroadcastReceiver {
    private static String url = "http://192.168.1.9:3000/streams.json";
    public Context global_context;
    @Override
    public void onReceive(Context context, Intent intent) {

        global_context = context;


        Log.d("API123",""+intent.getAction());

      if(intent.getAction().equals("com.journaldev.broadcastreceiver.SOME_ACTION")) {
          Toast.makeText(context, "SOME_ACTION is received", Toast.LENGTH_LONG).show();
          new GetContacts(context).execute();
      }
 /*
        else {
      ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            if (isConnected) {
                try {
                    Toast.makeText(context, "Network is connected", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(context, "Network is changed or reconnected", Toast.LENGTH_LONG).show();

            }

        }*/
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {
         public Context context_main ;
        public GetContacts(Context context) {
             context_main = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            //   Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    // JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray contacts =  new JSONArray(jsonStr);
                    //jsonObj.getJSONArray("contacts");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("name");


                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("name", id);
                        contact.put("email", name);
                        contact.put("mobile", name);

                        if(name.equals("Guest"))
                        {
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText( context_main, "Guest is connected", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        // adding contact to contact list

                    }
                } catch (final JSONException e) {
                    //   Log.e(TAG, "Json parsing error: " + e.getMessage());


                }
            } else {
                //  Log.e(TAG, "Couldn't get json from server.");

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog

            /**
             * Updating parsed JSON data into ListView
             * */
        }

    }

}
