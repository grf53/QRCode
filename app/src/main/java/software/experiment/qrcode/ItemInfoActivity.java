package software.experiment.qrcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import software.experiment.qrcode.database.DatabaseHelper;
import software.experiment.qrcode.database.Item;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentProvider;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemInfoActivity extends Activity {
	
	public static final String publicDirectoryPath = "QRCode"; 
	
	Item item;
	
	private ImageView imageQRCode;
	private TextView textItemName;
	private TextView textPrice;
	private TextView textLost;
	
	private DatabaseHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_info);
		
		Intent intent = getIntent();
		item = (Item) intent.getSerializableExtra("ITEM");
		dbHelper = new DatabaseHelper(getApplicationContext());
		
		imageQRCode = (ImageView) findViewById(R.id.image_qrcode_info);
		textItemName = (TextView) findViewById(R.id.text_item_name_info);
		textPrice = (TextView) findViewById(R.id.text_price_info);
		textLost = (TextView) findViewById(R.id.text_lost_info);
		
		Bitmap bitmapQRCode = null;
		try {
			bitmapQRCode = BitmapFactory.decodeStream(openFileInput(item.getQRCodeFileName()));
		} catch (FileNotFoundException e) {
			Log.e("QRCODE", e.getLocalizedMessage());
			e.printStackTrace();
		}
		if(bitmapQRCode != null)
			imageQRCode.setImageBitmap(bitmapQRCode);
		textItemName.setText(item.getItemName());
		textPrice.setText(String.valueOf(item.getPrice())+"만 원");
		textLost.setText(item.isLost()? "QR 인식됨":"이상 없음");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (menuItem.getItemId()) {
		case R.id.action_save_qrcode:
			FileInputStream in = null;
			FileOutputStream out = null;
			try {
				File targetDirectory = new File(Environment.getExternalStoragePublicDirectory(
											Environment.DIRECTORY_PICTURES),publicDirectoryPath);
				if(!targetDirectory.exists())
					targetDirectory.mkdir();
				
				File targetFile = new File(targetDirectory, item.getQRCodeFileName());
				
				in = openFileInput(item.getQRCodeFileName());
				out = new FileOutputStream(targetFile);
				for(int data;(data = in.read()) != -1;){
					out.write(data);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				try {
					if(out != null)
						out.close();
					if(in != null)
						in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Toast.makeText(getApplicationContext(), "저장 완료", Toast.LENGTH_SHORT).show();
			break;
		case R.id.action_share_qrcode:
			Intent intentSend = new Intent(Intent.ACTION_SEND);
			intentSend.setType("image/*");
			intentSend.putExtra(Intent.EXTRA_STREAM, 
					Uri.parse(getFilesDir()+"/"+item.getQRCodeFileName()));
			startActivity(Intent.createChooser(intentSend, "공유하기"));
			break;
		case R.id.action_remove_item:
			AlertDialog dialog = new AlertDialog.Builder(ItemInfoActivity.this)
					.setTitle("물품 삭제")
					.setMessage("정말 삭제하시겠습니까?")
					.setPositiveButton("네",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									File fileQRCode = new File(item.getItemName());
									fileQRCode.delete();
									dbHelper.deleteByName(item.getItemName());
									finish();
								}
							})
					.setNegativeButton("아니요",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							}).create();
			dialog.show();
			return true;
		}
		
		return super.onOptionsItemSelected(menuItem);
	}
}
