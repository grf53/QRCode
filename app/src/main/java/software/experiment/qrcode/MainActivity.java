package software.experiment.qrcode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import software.experiment.qrcode.database.DatabaseHelper;
import software.experiment.qrcode.database.Item;
import software.experiment.qrcode.login.LoginActivity;
import software.experiment.qrcode.network.SocketInterface;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

public class MainActivity extends Activity {

	private String userID;
	public static final String serverAddress = SocketInterface.serverAddress;
	public static final String QRPort = String.valueOf(SocketInterface.QRPort);

	public static final String fileNamePrefix = "qrcode-";
	public static final String fileNamePostfix = ".png";

	private ListView listView;
	private CustomCursorAdapter adapter;
	private LayoutInflater inflater;
	private DatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent intent = getIntent();
		userID = intent.getStringExtra("USER_ID");

		dbHelper = new DatabaseHelper(getApplicationContext());
		listView = (ListView) findViewById(R.id.list_view);
		adapter = new CustomCursorAdapter(getApplicationContext(),
				dbHelper.getDBCursor(), false);

		listView.setAdapter(adapter);

		inflater = LayoutInflater.from(getApplicationContext());

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				Item itemOnPosition = dbHelper.getItemByIndex(position);
				Intent intent = new Intent(getApplicationContext(),
						ItemInfoActivity.class);
				intent.putExtra("ITEM", itemOnPosition);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		dbHelper.renewCursorAdapter(adapter);
		adapter.notifyDataSetChanged();
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_log_out:
			AlertDialog dialogLogout = new AlertDialog.Builder(MainActivity.this)
					.setTitle("�α׾ƿ�")
					.setMessage("������ �α׾ƿ��Ͻðڽ��ϱ�?")
					.setPositiveButton("Ȯ��",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									getSharedPreferences("LOGIN",
											Context.MODE_PRIVATE).edit()
											.putBoolean("LOGGED_IN", false)
											.commit();
									dbHelper.clearAll();
									Intent intentAcvitityLogin = new Intent(
											getApplicationContext(),
											LoginActivity.class);
									startActivity(intentAcvitityLogin);
									finish();
								}
							})
					.setNegativeButton("���",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).create();

			dialogLogout.show();
			return true;
		case R.id.action_add_item:
			View dialogView = inflater.inflate(R.layout.dialog_layout,
					(ViewGroup) findViewById(R.id.root_dialog));

			final EditText editItemNameDialog = (EditText) dialogView
					.findViewById(R.id.edit_item_name_dialog);
			final EditText editPriceDialog = (EditText) dialogView
					.findViewById(R.id.edit_price_dialog);

			AlertDialog dialogAdd = new AlertDialog.Builder(MainActivity.this)
					.setTitle("��ǰ �߰�")
					.setMessage("�̸��� �� �� ������ ������ ���� Ȯ���� �����ּ���.")
					.setPositiveButton("Ȯ��",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

									String stringEditItemName = editItemNameDialog
											.getText().toString();
									String stringEditPrice = editPriceDialog
											.getText().toString();
									if (stringEditItemName == null
											|| stringEditItemName.length() == 0
											|| stringEditPrice == null
											|| stringEditPrice.length() == 0) {
										dialog.dismiss();
									} else {
										dbHelper.insert(
												stringEditItemName,
												false,
												Integer.parseInt(stringEditPrice),
												getQRCodePath(stringEditItemName));

										if (generateQRCode(dbHelper
												.getLast_id())) {
											Item itemJustPut = dbHelper
													.getItemById(dbHelper
															.getLast_id());

											Intent intent = new Intent(
													getApplicationContext(),
													ItemInfoActivity.class);
											intent.putExtra("ITEM", itemJustPut);
											startActivity(intent);
										}

										Toast.makeText(getApplicationContext(),
												"Working", Toast.LENGTH_SHORT)
												.show();
									}
								}
							})
					.setNegativeButton("���",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).create();

			dialogAdd.setView(dialogView);
			dialogAdd.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private String getQRCodePath(String itemName) {
		return fileNamePrefix + itemName + fileNamePostfix;
	}

	// private String getQRCodeContents(int _id){
	// return serverAddress+":"+QRPort+"/userID="+userID+"&"+"itemID="+_id;
	// }

	private String getQRCodeContents(int _id) {
		BufferedReader in = null;
		String phone = null;
		try {
			in = new BufferedReader(
					new InputStreamReader(openFileInput("info")));
			phone = in.readLine().trim();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int price = dbHelper.getItemById(_id).getPrice();
		return "Phone: " + phone + " Reward: " + price + "�� ��";
	}

	@SuppressWarnings("deprecation")
	private boolean generateQRCode(int _id) {
		boolean success = false;
		try {
			Bitmap bitmap = QRCodeGenerator.encodeAsBitmap(
					getQRCodeContents(_id), BarcodeFormat.QR_CODE, 1000, 1000);
			final FileOutputStream fos = openFileOutput(
					dbHelper.getItemById(_id).getQRCodeFileName(),
					Context.MODE_WORLD_READABLE);
			success = bitmap.compress(CompressFormat.PNG, 0, fos);
		} catch (WriterException e) {
			Log.e("QRCODE", e.getLocalizedMessage());
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			Log.e("QRCODE", e.getLocalizedMessage());
			e.printStackTrace();
		}

		return success;
	}
}
