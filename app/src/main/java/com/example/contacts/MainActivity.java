package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://api.androidhive.info/contacts/";
    private RecyclerView contactrecyclerView;
    private ArrayList<ContactData> contactsList = new ArrayList<>();
    private ArrayList<ContactData> contactsListDatabase;
    private ContactsAdapter contactAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView erroImage;
    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue contactQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();
        erroImage = findViewById(R.id.erro_image);
        contactrecyclerView = findViewById(R.id.contact_recycler_view);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        contactrecyclerView.setLayoutManager(layoutManager);

        contactQueue = Volley.newRequestQueue(this);
        callAPIVolley();
        contactQueue.add(jsonObjectRequest);
        SearchView searchView = findViewById(R.id.search_bar1);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void callAPIVolley(){
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                try {
                    String contactJsonString = response.getJSONArray("contacts").toString();
                    contactsList = gson.fromJson(contactJsonString,new TypeToken<ArrayList<ContactData>>(){}.getType());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                contactAdapter = new ContactsAdapter(contactsList);
                contactrecyclerView.setAdapter(contactAdapter);
                contactQueue.stop();
            }
        } ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                erroImage.setVisibility(View.VISIBLE);
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink);
                erroImage.startAnimation(anim);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.database_option,menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_to_database:
                new myThreadSaveToDataBase().start();
                Toast.makeText(this,"Save To Database SUCCESSFULLY",Toast.LENGTH_LONG).show();
                return true;
            case R.id.retrieve_from_database:
                new myThreadRetreieveFromDataBase().start();
                erroImage.setVisibility(View.GONE);
                Toast.makeText(this,"Retrieve From Database SUCCESSFULLY",Toast.LENGTH_LONG).show();
                return true;
                }
        return super.onOptionsItemSelected(item);
    }

    public void saveToRoomDB() {
        ContactDatabase database = ContactDatabase.getInstance(this);
        for (ContactData data : contactsList) {
            database.contactDAO().insertContact(data);
        }
    }

    public List<ContactData> retrieveFromRoomDB() {
        ContactDatabase database = ContactDatabase.getInstance(this);
        return database.contactDAO().getContacts();
    }

    class myThreadSaveToDataBase extends Thread {
        @Override
        public void run() {
            super.run();
            saveToRoomDB();
        }
    }

    class myThreadRetreieveFromDataBase extends Thread {
        @Override
        public void run() {
            super.run();
            contactsListDatabase = (ArrayList<ContactData>) retrieveFromRoomDB();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    contactAdapter = new ContactsAdapter(contactsListDatabase);
                    contactrecyclerView.setAdapter(contactAdapter);
                    contactAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}
