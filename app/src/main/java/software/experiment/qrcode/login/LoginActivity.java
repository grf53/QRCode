package software.experiment.qrcode.login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import software.experiment.qrcode.MainActivity;
import software.experiment.qrcode.R;
import software.experiment.qrcode.network.SocketInterface;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText editID;
	private EditText editPW;
	private Button buttonLogin;
	private Button buttonSignup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		editID = (EditText) findViewById(R.id.edit_login_id);
		editPW = (EditText) findViewById(R.id.edit_login_pw);
		
		buttonLogin = (Button) findViewById(R.id.button_login);
		buttonLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String stringID = editID.getText().toString().trim();
				String stringPW = editPW.getText().toString().trim();
				if(login(stringID, stringPW)){
					Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(mainActivityIntent);
					finish();
				}
				else{
					Toast.makeText(getApplicationContext(), "Failed. Check your Account.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		buttonSignup = (Button) findViewById(R.id.button_login_signup);
		buttonSignup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent signupActivityIntent = new Intent(getApplicationContext(), SignupActivity.class);
				startActivity(signupActivityIntent);
			}
		});
	}
	
	private boolean login(String id, String pw){
		boolean success = false;
		
		success = runLoginAsync(id, pw);
		
		getSharedPreferences("LOGIN", Context.MODE_PRIVATE).edit()
		.putBoolean("LOGGED_IN", success)
		.commit();
		
		return success;
	}
	
	private boolean runLoginAsync(String id, String pw){
		AsyncTask<String, Void, Boolean> async
			= new AsyncTask<String, Void, Boolean>() {
				Socket socket;
				BufferedReader in;
				BufferedWriter out;
				
				@Override
				protected Boolean doInBackground(String... params) {
					JSONObject jsonLogin = new JSONObject();
					JSONObject jsonContents = new JSONObject();
					JSONObject jsonConfirm;
					
					try {
						socket = new Socket(SocketInterface.serverAddress, SocketInterface.appPort);
						in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
						jsonContents.put(SocketInterface.KEY_JSON_ID, params[0])
						.put(SocketInterface.KEY_JSON_PW, params[1]);
						
						jsonLogin.put(SocketInterface.KEY_JSON_TYPE, SocketInterface.TYPE_LOGIN_REQ);
						jsonLogin.put(SocketInterface.KEY_JSON_CONTENTS, jsonContents);
						
						Log.d("QRCode", String.valueOf(out));
						out.append(jsonLogin.toString());
						out.newLine();
						out.flush();
						jsonConfirm = new JSONObject(in.readLine());
						int confirmType = jsonConfirm.getInt(SocketInterface.KEY_JSON_TYPE);
						boolean success = jsonConfirm.getBoolean(SocketInterface.KEY_JSON_SUCCESS);
						if(confirmType == SocketInterface.TYPE_LOGIN_CONF && success){
							return true;
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return false;
				}
			};
			
		boolean success = false;
		try {
			success = async.execute(id, pw).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return success;
	}

}