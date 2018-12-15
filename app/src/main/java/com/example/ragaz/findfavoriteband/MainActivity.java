package com.example.ragaz.findfavoriteband;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ragaz.findfavoriteband.Net.Model.Event;
import com.example.ragaz.findfavoriteband.Net.MyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final EditText input = (EditText) findViewById(R.id.input_artist);

        Button metallicaB = (Button)findViewById(R.id.button_metallica);
        metallicaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getArtistByName(input.getText().toString());
            }
        });

        return true;
    }


    private void getArtistByName(String name){
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
                            data[i] = events.get(i).getVenue().getName() + " : " + events.get(i).getDatetime();
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
    }
}
