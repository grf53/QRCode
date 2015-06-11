package software.experiment.qrcode.login;

import software.experiment.qrcode.MainActivity;
import software.experiment.qrcode.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText editId;
	private EditText editPW;
	private Button buttonLogin;
	private Button buttonSignup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		editId = (EditText) findViewById(R.id.edit_login_id);
		editPW = (EditText) findViewById(R.id.edit_login_pw);
		
		buttonLogin = (Button) findViewById(R.id.button_login);
		buttonLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(login(editId.getText().toString().trim(), editPW.getText().toString().trim()));
				//temporary
				Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(mainActivityIntent);
				finish();
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
		
		Toast.makeText(getApplicationContext(), "Just Press Login to start MainActivity", Toast.LENGTH_LONG).show();
	}
	
	private boolean login(String id, String password){
		boolean success = false;
		
		//Socket... login?
		
		getPreferences(Context.MODE_PRIVATE).edit()
		.putBoolean("LOGGED_IN", success)
		.commit();
		
		return success;
	}
}
