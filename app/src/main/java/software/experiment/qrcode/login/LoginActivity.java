package software.experiment.qrcode.login;

import software.experiment.qrcode.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class LoginActivity extends Activity {
	
	private boolean loggedIn;
	private RelativeLayout backgroudLayout;
	private RelativeLayout contentsLayout;
	private EditText editId;
	private EditText editPW;
	private Button buttonLogin;
	private Button buttonSignup;
	private SharedPreferences sharedPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		backgroudLayout = (RelativeLayout) findViewById(R.id.layout_login);
		contentsLayout = (RelativeLayout) findViewById(R.id.layout_contents);
		
		sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean("LOGGED_IN", false);
		
		new Handler().postDelayed(new Runnable() {
			@SuppressLint("NewApi")
			@Override
			public void run() {
				if(loggedIn)
					finish();       // finish Activity after 2 seconds if loggedIn
				else{
					Drawable background = backgroudLayout.getBackground();
					background.setAlpha(80);
//					backgroudLayout.setBackground(background);
					contentsLayout.setVisibility(View.VISIBLE);
				}
			}
		}, 2000);
		
		editId = (EditText) findViewById(R.id.edit_id);
		editPW = (EditText) findViewById(R.id.edit_pw);
		
		buttonLogin = (Button) findViewById(R.id.button_login);
		buttonLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(login(editId.getText().toString().trim(), editPW.getText().toString().trim()));
			}
		});
		
		buttonSignup = (Button) findViewById(R.id.button_signup);
		buttonSignup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent signupActivityIntent = new Intent(getApplicationContext(), SignupActivity.class);
				startActivity(signupActivityIntent);
			}
		});
	}
	
	private boolean login(String id, String password){
		boolean success = false;
		
		//Socket... login?
		
		SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("LOGGED_IN", success);
        editor.commit();
		
		return success;
	}
}
