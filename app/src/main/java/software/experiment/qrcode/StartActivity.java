package software.experiment.qrcode;

import software.experiment.qrcode.login.LoginActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class StartActivity extends Activity {
		
	private SharedPreferences sharedPreferences;
	private boolean loggedIn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_start);
		sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean("LOGGED_IN", false);
		
		new Handler().postDelayed(new Runnable() {
			private Intent intentAcvitityStart;
			
			@Override
			public void run() {
				
				intentAcvitityStart
					= new Intent(getApplicationContext(),
							loggedIn? MainActivity.class
									: LoginActivity.class);
				startActivity(intentAcvitityStart);
				finish();
			}
		}, 2000);
		
		super.onCreate(savedInstanceState);
	}
}
