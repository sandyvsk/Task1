package com.sandy.task1.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sandy.task1.R;
import com.sandy.task1.adapter.ShowSelectedTitleListAdapter;
import com.sandy.task1.adapter.ShowTitleListAdapter;
import com.sandy.task1.api.APIClient;
import com.sandy.task1.api.RestApiInterface;
import com.sandy.task1.controller.AddTitleListListener;
import com.sandy.task1.controller.RemoveTitleListListener;
import com.sandy.task1.model.GetListResponse;
import com.sandy.task1.utils.ConnectionDetector;
import com.sandy.task1.utils.RestUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AddTitleListListener, RemoveTitleListListener {


    private final String TAG = "MainActivity";

    List<GetListResponse> dataBeanList;

    List<String> titlelist = new ArrayList<>();

    List<String> selectedTitle = new ArrayList<>();

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_selected_list)
    RecyclerView rv_selected_list;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ProgressBar)
    ProgressBar ProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Log.w(TAG, "onCreate");



        if (new ConnectionDetector(MainActivity.this).isNetworkAvailable(MainActivity.this)) {

            getListResponseCall();
        }



    }

    @SuppressLint("LogNotTimber")
    public void getListResponseCall(){

        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<List<GetListResponse>> call = apiInterface.gtetlistResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<List<GetListResponse>>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<List<GetListResponse>> call, @NonNull Response<List<GetListResponse>> response) {

                if (response.body() != null) {

                        ProgressBar.setVisibility(View.GONE);

                        Log.w(TAG,"GetListResponse" + new Gson().toJson(response.body()));

                        dataBeanList = response.body();

                        for(GetListResponse getListResponse:dataBeanList){

                            if(getListResponse.getTitle()!=null&&!getListResponse.getTitle().isEmpty()){

                                titlelist.add(getListResponse.getTitle());
                            }

                        }

                        Log.w(TAG,"TitleList" + new Gson().toJson((titlelist)));

                        if(titlelist!=null&&titlelist.size()>0){

                            setViewTitle(titlelist);
                        }


                }

            }


            @Override
            public void onFailure(@NonNull Call<List<GetListResponse>> call,@NonNull  Throwable t) {

                ProgressBar.setVisibility(View.GONE);

                Log.w(TAG,"GetListResponse flr"+t.getMessage());
            }
        });

    }

    private void setViewTitle(List<String> titlelist) {
        rv_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_list.setItemAnimator(new DefaultItemAnimator());
        ShowTitleListAdapter showTitleListAdapter = new ShowTitleListAdapter(getApplicationContext(), titlelist,this);
        rv_list.setAdapter(showTitleListAdapter);
    }


    @Override
    public void addTitleListListener(String title) {

        Log.w(TAG,"Selected title: "+ title);

        selectedTitle.add(title);

        setViewSelected(selectedTitle);


    }

    private void setViewSelected(List<String> titlelist) {
        rv_selected_list.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        rv_selected_list.setItemAnimator(new DefaultItemAnimator());
        ShowSelectedTitleListAdapter showSelectedTitleListAdapter = new ShowSelectedTitleListAdapter(getApplicationContext(), titlelist,this);
        rv_selected_list.setAdapter(showSelectedTitleListAdapter);
    }

    @Override
    public void removeTitleListListener(String title) {

        Log.w(TAG,"Removed title: "+ title);

        titlelist.add(title);

        setViewTitle(titlelist);
    }
}