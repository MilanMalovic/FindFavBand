package com.example.ragaz.findfavoriteband;

import android.app.Dialog;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ragaz.findfavoriteband.Net.Model.Example;
import com.example.ragaz.findfavoriteband.Net.MyService;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("REZ","prvi0");
            setContentView(R.layout.activity_main);
           Log.i("REZ","prvi");
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("REZ","2");
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final EditText input = (EditText) findViewById(R.id.input_artist);

        Button metallicaB = (Button)findViewById(R.id.button_metallica);
        metallicaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getArtistByName(input.getText().toString() );
                getExampleByName(input.getText().toString());
            }
        });

        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_show_image:
                showRandomImage();
                break;
            case R.id.action_about:
                dialog = new AboutDialog(MainActivity.this).prepareDialog();
                dialog.show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showRandomImage() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.band_layout);

        ImageView image = (ImageView) dialog.findViewById(R.id.band_image);

        Picasso.with(this).load("https://source.unsplash.com/random").into(image);

        dialog.show();
    }

    private void getExampleByName(String name) {
        Map<String , String> query = new HashMap<String, String>();
        query.put("app_id", "test");

        Call<List<Example>> call = MyService.apiInterface().getArtistByName(name, query);
        call.enqueue(new Callback<List<Example>>() {
                         @Override
                         public void onResponse(Call<List<Example>> call, Response<List<Example>> response) {
                             if (response.code() == 200) {
                                 List<Example> exemple = response.body();
                                 if (exemple.size() > 0) {
                                     String[] data1 = new String[exemple.size()];
                                     for (int i = 0; i < exemple.size(); i++) {
                                         data1[i] = "Country: " +exemple.get(i).getVenue().getCountry()+ "\n" +" Name : " + exemple.get(i).getVenue().getName() + "\n" + " Date and Time : " +
                                         exemple.get(i).getDatetime() + "\n" + " Lineup : " + exemple.get(i).getLineup() + " \n "
                                         + "On Sale Date : " +exemple.get(i).getOnSaleDatetime() + "\n" + " Description : " + exemple.get(i).getDescription()  ;
                                     }


                                     Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                                     intent.putExtra("data", data1);
                                     startActivity(intent);
                                     Toast.makeText(MainActivity.this, "Prikaz liste koncerata", Toast.LENGTH_SHORT).show();
                                 } else {
                                     Toast.makeText(MainActivity.this, "Nema koncerata", Toast.LENGTH_SHORT).show();
                                 }
                             }
                         }

            @Override
            public void onFailure(Call<List<Example>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });
    }
}








  /* private void getArtistByName(String name){
        Map<String, String> query = new HashMap<String, String>();
        query.put("app_id", "test");

        Call<List<Event>> call = MyService.apiInterface().getArtistByName(name, query);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {

                if (response.code() == 200) {
                    List<Event> events = response.body();

                    if (events.size() > 0) {

                        String[] data = new String[events.size()];

                        for (int i = 0; i < events.size(); i++) {

                            data[i] = events.get(i).getVenue().getName() + " : " + events.get(i).getDatetime()
                            + " : " + events.get(i).getLineup() + " : " + events.get(i).getVenue().getCity()
                                    + " : " + events.get(i).getVenue().getCountry();
                        }

                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        intent.putExtra("data", data);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Prikaz liste koncerata", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Nema koncerata", Toast.LENGTH_SHORT).show();
                    }
                }
            }




            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                //u slucaju da je nesto poslo po zlu, ispisemo sta nije u redu tj sta je poruka greske
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }   */

