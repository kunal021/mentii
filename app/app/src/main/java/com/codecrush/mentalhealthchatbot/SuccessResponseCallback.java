package com.codecrush.mentalhealthchatbot;

/*import com.google.android.gms.common.internal.safeparcel.SafeParcelable;*/
import com.google.gson.JsonObject;

import retrofit2.Response;

public interface SuccessResponseCallback<T>
{
    void onSuccessfullResponse(T Object);
    default void onFailureResponse(T Object){};
    default void onDataNotfound(){};

}
