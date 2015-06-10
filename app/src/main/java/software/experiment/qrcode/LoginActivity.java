package software.experiment.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		Button btnSignup = (Button) findViewById(R.id.button);
		btnSignup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent signupActivityIntent = new Intent(getApplicationContext(), SignupActivity.class);
				startActivity(signupActivityIntent);
			}
		});;
	}
}
