package software.experiment.qrcode.login;

import software.experiment.qrcode.R;
import android.app.Activity;
import android.os.Bundle;
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
					Toast.makeText(SignupActivity.this, "Success", Toast.LENGTH_SHORT).show();
				}				
			}
		});
	}
}
