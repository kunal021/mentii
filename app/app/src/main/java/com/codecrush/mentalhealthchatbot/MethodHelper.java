package com.codecrush.mentalhealthchatbot;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Response;

public class MethodHelper
{
    public static void getFailureReason(Throwable Throwable, Context Context)
    {
        if (!CheckInternet.isconnected(Context))
        {
            Toast.makeText(Context, "Your internet is unstable, Please check your internet and Open again", Toast.LENGTH_LONG).show();
            Log.d("NetworkLoss", "No internet connection.");
        }
        else if (Throwable instanceof SocketTimeoutException)
        {
            // This handles both connection timeout and read/write timeout
            Toast.makeText(Context, "Request timed out, Open again.", Toast.LENGTH_LONG).show();
            Log.d("Timeout", "Request timeout: " + Throwable.getMessage());
        }
        else if (Throwable instanceof ConnectException)
        {
            // This is triggered when the connection to the server fails, likely due to a connection timeout
            Toast.makeText(Context, "Failed to connect to server, Open again.", Toast.LENGTH_LONG).show();
            Log.d("ConnectionTimeout", "Connection failed: " + Throwable.getMessage());
        }
        else if (Throwable instanceof IOException)
        {
            // This covers general network errors such as DNS issues, timeouts, etc.
            Toast.makeText(Context, "Network failure, Open again.", Toast.LENGTH_LONG).show();
            Log.d("NetworkFailure", "Network issue: " + Throwable.getMessage());
        }
        else
        {
            Toast.makeText(Context, "Unexpected error occurred, Open again.", Toast.LENGTH_LONG).show();
            Log.d("OtherFailure", "Error: " + Throwable.getMessage());
        }
    }

    public static <T> void getResponseConditionWithoutPT(Response<T> Response, Context Context,SuccessResponseCallback Callback)
    {
        if (Response.isSuccessful())
        {
            T data = Response.body();

            if (data!=null)
            {
                Callback.onSuccessfullResponse(data);
            }
            else
            {
                //Toast.makeText(Context, "No Data Found", Toast.LENGTH_LONG).show();
                Callback.onFailureResponse(data);
            }

        }
        else if (Response.code()==404)
        {
            /*ProgressBar.setVisibility(View.GONE);
            TVNoDataFound.setVisibility(View.VISIBLE);*/
            Toast.makeText(Context, "Error(404) : No Data Found", Toast.LENGTH_LONG).show();


        }
        else if (Response.code()==401)
        {
            //ProgressBar.setVisibility(View.GONE);
            Toast.makeText(Context, "Unauthorized Access, Please Try To Re-Login", Toast.LENGTH_LONG).show();
            startActivity(new Intent(),Context);
        }
        else if (Response.code()==500)
        {
            Toast.makeText(Context, "Internal Server Error,Open again", Toast.LENGTH_LONG).show();
        }
        else
        {
            try {

                JSONObject jsonObject = new JSONObject(Response.errorBody().string());
                String Message = jsonObject.getString("message");
                String code=String.valueOf(jsonObject.getInt("status"));
                Toast.makeText(Context, "Error(" + code + ") : " + Message, Toast.LENGTH_LONG).show();

            } catch (JSONException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void startActivity(Intent Intent,Context Context)
    {
        Intent=new Intent(Context, LoginActivity.class);
        startActivity(Intent,Context);
    }

    public static void checkInternetConditionByOpenAgain(Context Context,InternetCheckInterface Callback)
    {
        if (CheckInternet.isconnected(Context))
        {
            Callback.onSuccessInternetCallback();
        }
        else
        {
            Toast.makeText(Context, "No internet connection, Please open again", Toast.LENGTH_LONG).show();
        }
    }

    public static void checkInternetConditionByTryAgain(Context Context, InternetCheckInterface Callback)
    {
        if (CheckInternet.isconnected(Context))
        {
            Callback.onSuccessInternetCallback();
        }
        else
        {
            Toast.makeText(Context, "No internet connection, Please try again", Toast.LENGTH_LONG).show();
        }
    }

    public static <T> void getResponseCondition(Response<T> Response, Context Context, ProgressBar ProgressBar, TextView TVNoDataFound,boolean hasResponseBeenHandled, SuccessResponseCallback Callback)
    {
        if (Response.isSuccessful())
        {
            T data = Response.body();

            if (data!=null)
            {
                Callback.onSuccessfullResponse(data);
            }
            else
            {
                //Toast.makeText(Context, "No Data Found", Toast.LENGTH_LONG).show();
                Callback.onFailureResponse(data);
            }

        }
        else if (Response.code()==404)
        {
            ProgressBar.setVisibility(View.GONE);
            TVNoDataFound.setVisibility(View.VISIBLE);
            Callback.onDataNotfound();

        }
        else if (Response.code()==401)
        {
            ProgressBar.setVisibility(View.GONE);
            Toast.makeText(Context, "Unauthorized Access, Please Try To Re-Login", Toast.LENGTH_LONG).show();

            startActivity(new Intent(),Context);
        }
        else if (Response.code()==500)
        {
            Toast.makeText(Context, "Internal Server Error,Open again", Toast.LENGTH_LONG).show();
        }
        else
        {
            try {

                JSONObject jsonObject = new JSONObject(Response.errorBody().string());
                String Message = jsonObject.getString("message");
                Toast.makeText(Context, "Error: " + Message, Toast.LENGTH_LONG).show();
                Log.d("TAG",jsonObject.toString());
                Log.d("TAG",String.valueOf(Response.code()));

                ProgressBar.setVisibility(View.GONE);
                TVNoDataFound.setVisibility(View.VISIBLE);


            } catch (JSONException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static <T> void getResponseConditionWithP(Response<T> Response, Context Context, ProgressBar ProgressBar, SuccessResponseCallback Callback)
    {
        if (Response.isSuccessful())
        {
            T data = Response.body();

            if (data!=null)
            {
                Callback.onSuccessfullResponse(data);
            }
            else
            {
                //Toast.makeText(Context, "No Data Found", Toast.LENGTH_LONG).show();
                Callback.onFailureResponse(data);
            }

        }
        else if (Response.code()==404)
        {
            ProgressBar.setVisibility(View.GONE);
            Callback.onDataNotfound();

        }
        else if (Response.code()==401)
        {
            ProgressBar.setVisibility(View.GONE);
            Toast.makeText(Context, "Unauthorized Access, Please Try To Re-Login", Toast.LENGTH_LONG).show();

            startActivity(new Intent(),Context);
        }
        else if (Response.code()==500)
        {
            Toast.makeText(Context, "Internal Server Error,Open again", Toast.LENGTH_LONG).show();
        }
        else
        {
            try {

                JSONObject jsonObject = new JSONObject(Response.errorBody().string());
                String Message = jsonObject.getString("message");
                String code=String.valueOf(jsonObject.getInt("status"));
                Toast.makeText(Context, "Error(" + code + ") : " + Message, Toast.LENGTH_LONG).show();

            } catch (JSONException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
