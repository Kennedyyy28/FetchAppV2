//Kennedy Smith
//3/21/2024
//This program uses Volley to read from a JSON string, deserialize it
//in Java objects and sorts it by group id, name and does not display any objects with null or " " as name
package com.example.fetchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String url = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
    //Master list of all objects
    List<itemModel> allItemsList = new ArrayList<>();
    //Create 4 different lists to hold objects based on item id
    ArrayList<itemModel> itemsList1 = new ArrayList();
    ArrayList<itemModel> itemsList2 = new ArrayList();
    ArrayList<itemModel> itemsList3 = new ArrayList();
    ArrayList<itemModel> itemsList4 = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();
    }
    private void getData(){
        /*This method uses Volley to deserialize JSON array of objects into Java objects
          It also calls Collections.sort and printAllGroups to first sort the data by name and then display on the screen
          See the SortByName class for more details on Collections.sort
        */
        RequestQueue queue = Volley.newRequestQueue(this);
        TextView textView = findViewById(R.id.textViewMain);
        //This allows for the user to scroll using the scroll view
        textView.setMovementMethod(new ScrollingMovementMethod());


    //Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++){
                                JSONObject singleObject = array.getJSONObject(i);
                                //creates a new object of type itemModel at spot i and gets corresponding elements from JSON
                                itemModel singleModel = new itemModel(
                                        singleObject.getInt("id"),
                                        singleObject.getInt("listId"),
                                        singleObject.getString("name")
                                );
                                //allItemsList.add(singleModel);
                                //Add to list depending on list id, any object with nulls or spaces as name will not be displayed
                                if(singleModel.getName() != "null" && !singleModel.getName().isEmpty()){
                                    if(singleModel.getListId() == 1){
                                        itemsList1.add(singleModel);
                                    }else if(singleModel.getListId() == 2){
                                        itemsList2.add(singleModel);
                                    }else if(singleModel.getListId() == 3){
                                        itemsList3.add(singleModel);
                                    }else if(singleModel.getListId() == 4){
                                        itemsList4.add(singleModel);
                                    }
                                }
                            }
                            Collections.sort(itemsList1, new SortByName());
                            Collections.sort(itemsList2, new SortByName());
                            Collections.sort(itemsList3, new SortByName());
                            Collections.sort(itemsList4, new SortByName());

                            //Call printGroup
                            printAllGroups();
                            Log.e(url, "onResponse: " + allItemsList.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(url, "onResponse: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("url", "onErrorResponse: " + error.getLocalizedMessage());
            }
        });

    //Add request to the RequestQueue
        queue.add(stringRequest);
    }

    private void printAllGroups(){
        /*
        This method gets the text view and creates a new string builder
        It then calls the method stringBuilderList for each list by feeding it the specified parameters
        The text view is set after the method calls
        */
        TextView textView = findViewById(R.id.textViewMain);
        StringBuilder stringBuilder = new StringBuilder();

        //Append items from all the lists
        stringBuilderList(itemsList1, stringBuilder);
        stringBuilderList(itemsList2, stringBuilder);
        stringBuilderList(itemsList3, stringBuilder);
        stringBuilderList(itemsList4, stringBuilder);

        //Set the textView after appending all items
        textView.setText(stringBuilder.toString());
    }

    private void stringBuilderList(ArrayList<itemModel> list, StringBuilder stringBuilder ){
    /*This method takes two parameters, an array list and a string builder
      A for loop is ran to append all the properties of the object at spot i until the end of the list
    */
        for(int i = 0; i < list.size(); i++){
            stringBuilder.append("ID: " + list.get(i).getId() + ", List ID: " + list.get(i).getListId() + ", Name: " + list.get(i).getName()).append("\n");
        }
    }
}