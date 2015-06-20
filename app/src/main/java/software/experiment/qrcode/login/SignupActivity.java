package software.experiment.qrcode.login;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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

public class SignupActivity extends Activity {
	
	private Button buttonConfirm;
	private EditText editID;
	private EditText editPW;
	private EditText editPWCheck;
	private EditText editEmail;
	private EditText editPhone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		buttonConfirm = (Button) findViewById(R.id.button_signup_confirm);
		editID = (EditText) findViewById(R.id.edit_signup_id);
		editPW = (EditText) findViewById(R.id.edit_signup_pw);
		editPWCheck = (EditText) findViewById(R.id.edit_signup_pw_check);
		editEmail = (EditText) findViewById(R.id.edit_signup_email);
		editPhone = (EditText) findViewById(R.id.edit_signup_phone);
		
		buttonConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String stringID = editID.getText().toString().trim();
				String stringPW = editPW.getText().toString().trim();
				String stringPWCheck = editPWCheck.getText().toString().trim();
				String stringEmail = editEmail.getText().toString().trim();
				String stringPhone = editPhone.getText().toString().trim();

				if (stringID.equals("")
						|| stringPW.equals("")
						|| stringPWCheck.equals("")
						|| stringEmail.equals("") 
						|| stringPhone.equals("")) {
					Toast.makeText(SignupActivity.this, "Fill all contents.", Toast.LENGTH_SHORT).show();
				}
				else if (!stringPW.equals(stringPWCheck)) {
					Toast.makeText(SignupActivity.this, "check your password.", Toast.LENGTH_SHORT).show();
					editPW.setText("");
					editPWCheck.setText("");
				}
				else {
					if(signup(stringID, stringPW, stringEmail, stringPhone)){
						Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
						try {
							final FileOutputStream fos = openFileOutput("info",Context.MODE_PRIVATE);
							BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
							bw.append(stringPhone);
							bw.close();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						startActivity(mainActivityIntent);
						finish();
					}
					else{
						Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
					}
				}				
			}
		});
	}
	
	private boolean signup(String id, String pw, String email, String phone){
		boolean success = false;
		
		success = runSignupAsync(id, pw, email, phone);
		
		getSharedPreferences("LOGIN", Context.MODE_PRIVATE).edit()
		.putBoolean("LOGGED_IN", success)
		.commit();
		
		return success;
	}
	
	private boolean runSignupAsync(String id, String pw, String email, String phone){
		AsyncTask<String, Void, Boolean> async
			= new AsyncTask<String, Void, Boolean>() {
				Socket socket;
				BufferedReader in;
				BufferedWriter out;
			
				@Override
				protected Boolean doInBackground(String... params) {
					JSONObject jsonSignup = new JSONObject();
					JSONObject jsonContents = new JSONObject();
					JSONObject jsonConfirm;
					
					try {
						socket = new Socket(SocketInterface.serverAddress, SocketInterface.appPort);
						in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
						jsonContents.put(SocketInterface.KEY_JSON_ID, params[0])
						.put(SocketInterface.KEY_JSON_PW, params[1])
						.put(SocketInterface.KEY_JSON_EMAIL, params[2])
						.put(SocketInterface.KEY_JSON_PHONE, params[3]);
						
						jsonSignup.put(SocketInterface.KEY_JSON_TYPE, SocketInterface.TYPE_SIGNUP_REQ);
						jsonSignup.put(SocketInterface.KEY_JSON_CONTENTS, jsonContents);
						
						Log.d("QRCode", String.valueOf(out));
						out.append(jsonSignup.toString());
						out.newLine();
						out.flush();
						jsonConfirm = new JSONObject(in.readLine());
						int confirmType = jsonConfirm.getInt(SocketInterface.KEY_JSON_TYPE);
						boolean success = jsonConfirm.getBoolean(SocketInterface.KEY_JSON_SUCCESS);
						if(confirmType == SocketInterface.TYPE_SIGNUP_CONF && success){
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
			success = async.execute(id, pw, email, phone).get();
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
