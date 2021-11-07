package com.sandy.task1.api;


import com.sandy.task1.model.GetListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface RestApiInterface {

    /*Get list*/
    @GET("posts")
    Call<List<GetListResponse>> gtetlistResponseCall(@Header("Content-Type") String type);

}
