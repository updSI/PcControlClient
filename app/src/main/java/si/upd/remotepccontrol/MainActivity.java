package si.upd.remotepccontrol;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import static java.lang.System.in;

public class MainActivity extends AppCompatActivity {

    private EditText TextEditIp;
    private EditText TextEditPort;
    private EditText TextEditPasswd;

    private String serverMessage;

    public String SERVERIP = "192.168.2.2";
    public String SERVERPORT = "12345";
    public String PASSWD = "";

    private boolean mRun = false;

    PrintWriter out;
    BufferedReader in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        TextEditIp = (EditText)findViewById(R.id.editTextIp);
        TextEditPort = (EditText)findViewById(R.id.editTextPORT);
        TextEditPasswd = (EditText)findViewById(R.id.editTextPasswd);

        TextEditIp.setText(sharedPreferences.getString("IP", "192.168.1.100"));
        TextEditPort.setText(sharedPreferences.getString("PORT", "12345"));
        TextEditPasswd.setText(sharedPreferences.getString("PASSWD", "default"));

        TextEditIp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("IP", s.toString());
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        TextEditPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("PORT", s.toString());
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        TextEditPasswd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("PASSWD", s.toString());
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void sleepClicked(View view)
    {
       // Button button = (Button)view;
       // button.setText("SLEEP OK!");

        EditText editText = (EditText)findViewById(R.id.editTextPasswd);

        ClientSendMsg(editText.getText().toString() + " SLEEP");
    }

//    ipTextEdit
    public void ClientSendMsg(String data)
    {
        BufferedReader in;

        try {
            EditText editTextIP = (EditText)findViewById(R.id.editTextIp);
            EditText editTextPort = (EditText)findViewById(R.id.editTextPORT);

            //here you must put your computer's IP address.
            InetAddress serverAddr = InetAddress.getByName(editTextIP.getText().toString());

            Log.e("TCP Client", "C: Connecting...");

            //create a socket to make the connection with the server
            Socket socket = new Socket(serverAddr, Integer.valueOf(editTextPort.getText().toString()));

            try {

                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                out.println(data);
                //send the message to the server
              //  OutputStream out = socket.getOutputStream();
              //  PrintWriter output = new PrintWriter(out);

                //mStatusText.setText("Sending Data to PC");
              //  output.println("Hello from Android");

                out.flush();
                out.close();
               // mStatusText.setText("Data sent to PC");

            //    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                Log.e("TCP Client", "C: Sent.");

                Log.e("TCP Client", "C: Done.");
/*
                //receive the message which the server sends back
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //in this while the client listens for the messages sent by the server
                while (mRun) {
                    serverMessage = in.readLine();

                    if (serverMessage != null && mMessageListener != null) {
                        //call the method messageReceived from MyActivity class
                        mMessageListener.messageReceived(serverMessage);
                    }
                    serverMessage = null;

                }

                Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + serverMessage + "'");*/

            } catch (Exception e) {

                Log.e("TCP", "S: Error", e);

            } finally {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                socket.close();
            }

        } catch (Exception e) {

            Log.e("TCP", "C: Error", e);

        }
    }
}
