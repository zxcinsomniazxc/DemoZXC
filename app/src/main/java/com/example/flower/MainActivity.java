package com.example.flower;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ProgressBar mProgressBar;
    RecyclerView mRecyclerView,mRecyclerView1;
    List<Flower> mFlowers;
    LoginResponse loginResponse;
    ImageView favwhite, mainpagewhite, profilewhite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            loginResponse = (LoginResponse) intent.getSerializableExtra("data");
            Log.e("TAG", "=====>" + loginResponse.getEmail());
        }

        favwhite = (ImageView)findViewById(R.id.favwhite);
        mainpagewhite = (ImageView)findViewById(R.id.mainpagewhite);
        profilewhite = (ImageView)findViewById(R.id.profilewhite);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);

        mFlowers = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerView2) ;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView1.setLayoutManager(layoutManager1);

        FlowerAdapter adapter = new FlowerAdapter(mFlowers);
        mRecyclerView.setAdapter(adapter);

        ScrollAdapter adapter1 = new ScrollAdapter(mFlowers);
        mRecyclerView1.setAdapter(adapter1);

        mProgressBar.setVisibility(View.INVISIBLE);

        FlowersAPI flowersAPI = FlowersAPI.retrofit.create(FlowersAPI.class);
        final Call<List<Flower>> call = flowersAPI.getData();
        call.enqueue(new Callback<List<Flower>>() {
                         @Override
                         public void onResponse(Call<List<Flower>> call, Response<List<Flower>> response) {
                             // response.isSuccessfull() ???????????????????? true ???????? ?????? ???????????? 2xx
                             if (response.isSuccessful()) {
                                 mFlowers.addAll(response.body());
                                 mRecyclerView.getAdapter().notifyDataSetChanged();
                                 mProgressBar.setVisibility(View.INVISIBLE);
                             } else {
                                 // ???????????????????????? ????????????
                                 ResponseBody errorBody = response.errorBody();
                                 try {
                                     Toast.makeText(MainActivity.this, errorBody.string(),
                                             Toast.LENGTH_SHORT).show();
                                     mProgressBar.setVisibility(View.INVISIBLE);
                                 } catch (IOException e) {
                                     e.printStackTrace();
                                 }
                             }
                         }

                         @Override
                         public void onFailure(Call<List<Flower>> call, Throwable throwable) {
                             Toast.makeText(MainActivity.this, "??????-???? ?????????? ???? ??????",
                                     Toast.LENGTH_SHORT).show();
                             mProgressBar.setVisibility(View.INVISIBLE);
                         }
                     }
        );


        ScrollApi scrollAPI = ScrollApi.retrofit.create(ScrollApi.class);
        final Call<List<Flower>> call1 = scrollAPI.getData();
        call1.enqueue(new Callback<List<Flower>>() {
                          @Override
                          public void onResponse(Call<List<Flower>> call, Response<List<Flower>> response) {
                              // response.isSuccessfull() ???????????????????? true ???????? ?????? ???????????? 2xx
                              if (response.isSuccessful()) {
                                  mFlowers.addAll(response.body());
                                  mRecyclerView1.getAdapter().notifyDataSetChanged();
                                  mProgressBar.setVisibility(View.INVISIBLE);
                              } else {
                                  // ???????????????????????? ????????????
                                  ResponseBody errorBody = response.errorBody();
                                  try {
                                      Toast.makeText(MainActivity.this, errorBody.string(),
                                              Toast.LENGTH_SHORT).show();
                                      mProgressBar.setVisibility(View.INVISIBLE);
                                  } catch (IOException e) {
                                      e.printStackTrace();
                                  }
                              }
                          }

                          @Override
                          public void onFailure(Call<List<Flower>> call, Throwable throwable) {
                              Toast.makeText(MainActivity.this, "??????-???? ?????????? ???? ??????",
                                      Toast.LENGTH_SHORT).show();
                              mProgressBar.setVisibility(View.INVISIBLE);
                          }
                      }
        );
    }

    public void onClickMainPage (View view) {
        Intent i = new Intent(MainActivity.this, MainActivity.class);
        startActivity(i);
    }


    public void onClickFav (View view) {
        Intent i = new Intent(MainActivity.this, MainActivityFav.class);
        startActivity(i);
    }

    public void onClickProfile (View view) {
        Intent i = new Intent(MainActivity.this, MainActivityProfile.class);
        startActivity(i);
    }
}