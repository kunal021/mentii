package com.codecrush.mentalhealthchatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/*import com.codecrush.pjeclass.MainActivity;
import com.codecrush.pjeclass.R;
import com.codecrush.pjeclass.adapters.RecyclerMessageAdapter;
import com.codecrush.pjeclass.helpers.LocalChatDatabaseHelper;
import com.codecrush.pjeclass.helpers.LocalTestDatabaseHelper;
import com.codecrush.pjeclass.helpers.MethodsHelper;
import com.codecrush.pjeclass.helpers.SocketHelper;
import com.codecrush.pjeclass.models.DoubtDataModel;
import com.codecrush.pjeclass.models.MessageDataModel;
import com.codecrush.pjeclass.models.TestQuestionModel;*/

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;

/*import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;*/

public class ChatActivity extends AppCompatActivity {

    ImageView IVChatList,IVLogout, iv_send;

    EditText et_msg;

    RecyclerView rv_chatmessage;

    /*ArrayList<MessageDataModel> arr_msg_data = new ArrayList<>();

    RecyclerMessageAdapter adapter;

    LocalChatDatabaseHelper dbHelper;*/
    SQLiteDatabase db;
    String chatTableName = "chat";
    String chatMessageTableName = "chat_message";


    /*private OkHttpClient client;
    private WebSocket webSocket;*/

    boolean isWebSocketConnected = false;

    String sender_name;
    String sender_id;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //connectWebSocket();

        rv_chatmessage = findViewById(R.id.rv_chatmessage);
        iv_send = findViewById(R.id.iv_send);
        IVChatList = findViewById(R.id.iv_chat_list);
        IVLogout = findViewById(R.id.iv_logout);
        et_msg = findViewById(R.id.et_msg);

        IVLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        IVChatList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        rv_chatmessage.setLayoutManager(linearLayoutManager);

        SharedPreferences usr = getSharedPreferences("user", MODE_PRIVATE);

        sender_name=usr.getString("firstname","User") + " " + usr.getString("surname", "Name");
        sender_id=usr.getString("number", "9876543210");

        /*arr_msg_data.add(new MessageDataModel("9998021218_topic1",9998021218L,"Devom Jani","text","Hello!",null,1,1,System.currentTimeMillis()));
        arr_msg_data.add(new MessageDataModel("9998021218_topic1",8780111310L,"Devom Jani","text","Hi",null,1,1,System.currentTimeMillis()));
        arr_msg_data.add(new MessageDataModel("9998021218_topic1",9998021218L,"Devom Jani","text","How Are You",null,1,1,System.currentTimeMillis()));
        arr_msg_data.add(new MessageDataModel("9998021218_topic1",8780111310L,"Devom Jani","text","I am Fine.",null,1,1,System.currentTimeMillis()));
        arr_msg_data.add(new MessageDataModel("9998021218_topic1",8780111310L,"Devom Jani","text","What about You?",null,1,1,System.currentTimeMillis()));




        adapter = new RecyclerMessageAdapter(getApplicationContext(), arr_msg_data);
        rv_chatmessage.setAdapter(adapter);*/

      /*  adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                rv_chatmessage.smoothScrollToPosition(adapter.getItemCount() - 1);
            }
        });*/


        /*iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = et_msg.getText().toString();
                if (!message.isEmpty()) {
                    Log.d("ChatActivity", "Send button clicked with message: " + message);
                    if (!isWebSocketConnected) {
                        connectWebSocket();
                    } else {
                        sendMessage(message);

                        //arr_msg_data.add(new MessageDataModel("9998021218_topic1",Long.parseLong(sender_id),sender_name,"text",message,null,1,1,System.currentTimeMillis()));

                        //adapter.notifyItemInserted(arr_msg_data.size() - 1);
                        *//*adapter.notifyDataSetChanged();
                        rv_chatmessage.smoothScrollToPosition(adapter.getItemCount() - 1);*//*
                        et_msg.setText("");
                    }
                }
            }
        });*/




        /*back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                closeWebSocket();

            }
        });*/


    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        closeWebSocket();
        isWebSocketConnected=false;
    }*/

    /*private void connectWebSocket() {
        client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("wss://api.pjci.in:10091") // replace with your WebSocket URL
                .build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, okhttp3.Response response) {
                super.onOpen(webSocket, response);
                // Handle WebSocket open event
                JSONObject jsonMessage = new JSONObject();
                try {
                    jsonMessage.put("action", "setmyId");
                    jsonMessage.put("customId", MethodsHelper.getnumber());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Send the JSON string
                webSocket.send(jsonMessage.toString()); // Example of sending a message

                isWebSocketConnected = true;
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                Log.d("onMessage-text", "Received message: " + text);

                try {
                    JSONObject jsonObject = new JSONObject(text);
                    Log.d("onMessage-object", jsonObject.toString());

                    if (jsonObject.getString("action").equals("new_message")) {
                        // Add the new message to the array
                        arr_msg_data.add(new MessageDataModel(
                                jsonObject.getString("chat_id"),
                                jsonObject.getLong("sender_id"),
                                jsonObject.getString("sender_name"),
                                jsonObject.getString("message_type"),
                                jsonObject.getString("content"),
                                null,
                                1,
                                1,
                                jsonObject.getLong("time")
                        ));

                        // Update the RecyclerView on the main UI thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                rv_chatmessage.smoothScrollToPosition(adapter.getItemCount() - 1);
                            }
                        });
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }



            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                // Handle incoming binary data
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                webSocket.close(1000, null);
                Log.d("onClosing", "Closing: " + reason);
                isWebSocketConnected = false;
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
                super.onFailure(webSocket, t, response);
                Log.e("WebSocket", "Error: " + t.getMessage());
                if (response != null) {
                    Log.e("WebSocket", "Response: " + response.message());
                }
            }

        });

        client.dispatcher().executorService().shutdown();
    }*/

    /*private void sendMessage(String message) {
        if (webSocket != null && isWebSocketConnected) {
            JSONObject jsonMessage = new JSONObject();
            try {
                jsonMessage.put("action", "send_message");
                jsonMessage.put("chat_id", "9998021218_topic1");
                jsonMessage.put("sender_id",this.sender_id);
                jsonMessage.put("sender_name", this.sender_name);
                jsonMessage.put("message_type", "text");
                jsonMessage.put("content", message);
                jsonMessage.put("time", System.currentTimeMillis());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("ChatActivity", "Sending message: " + jsonMessage.toString());
            webSocket.send(jsonMessage.toString());
        } else {
            Log.d("ChatActivity", "WebSocket not connected, cannot send message.");
            Toast.makeText(this, "Connection not established", Toast.LENGTH_SHORT).show();
        }
    }*/


   /* private void closeWebSocket() {
        if (webSocket != null) {
            webSocket.close(1000, "Goodbye");

            isWebSocketConnected=false;
        }
    }*/



}