package software.experiment.qrcode.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import software.experiment.qrcode.R;

public class SignupActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
	}

	public void onClick(View view) {

		switch (view.getId()) {
			case R.id.btn_OK:
				EditText editID = (EditText) findViewById(R.id.edit_id);
				EditText editPW = (EditText) findViewById(R.id.edit_password);
				EditText editPW2 = (EditText) findViewById(R.id.edit_password2);
				EditText editEmail = (EditText) findViewById(R.id.edit_email);
				EditText editphone1 = (EditText) findViewById(R.id.edit_phone1);
				EditText editphone2 = (EditText) findViewById(R.id.edit_phone2);
				EditText editphone3 = (EditText) findViewById(R.id.edit_phone3);


				if (editID.getText().toString().equals("") || editPW.getText().toString().equals("") || editPW2.getText().toString().equals("") || editEmail.getText().toString().equals("") ||
						editphone1.getText().toString().equals("") || editphone2.getText().toString().equals("") || editphone3.getText().toString().equals("")) {
					Toast.makeText(SignupActivity.this, "Fill all contents.", Toast.LENGTH_SHORT).show();
					break;
				} else if (!editPW.getText().toString().equals(editPW2.getText().toString())) {

					Toast.makeText(SignupActivity.this, "check your password.", Toast.LENGTH_SHORT).show();
					editPW.setText("");
					editPW2.setText("");
					break;

				} else if (!editEmail.getText().toString().contains("@")) {

					Toast.makeText(SignupActivity.this, "check your email.", Toast.LENGTH_SHORT).show();
					editEmail.setText("");
					break;

				} else {
					Toast.makeText(SignupActivity.this, "Success", Toast.LENGTH_SHORT).show();
				}


				break;
			case R.id.btn_Cancel:
				finish();
				break;
			case R.id.btn_ID_Check:
				Toast.makeText(SignupActivity.this, "ID check!", Toast.LENGTH_SHORT).show();
				break;
		}
	}}
