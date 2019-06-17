package com.zemingo.ekaterinatemnogrudova.zemingo.api;

import com.zemingo.ekaterinatemnogrudova.zemingo.models.ModelResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("api")
    Observable<ModelResponse> getModel(@Query("something") String something);
}