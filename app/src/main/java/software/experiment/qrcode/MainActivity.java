package software.experiment.qrcode;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

public class MainActivity extends Activity {

	private String[] tvShows = { "Saturday Night Live", "House of Cards",
			"Game of Thrones", "The Flash", "Marvel's Agent of S.H.I.E.L.D",
			"Pushing Daisies", "Better of Ted", "Twin Peaks",
			"Freaks and Geeks", "Orphan Black", "Walking Dead", "Breaking Bad",
			"The 400", "Alphas", "Life of Mars" };

	private ListView listView;
	private CustomAdapter adapter;
	private LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.list_view);
		adapter = new CustomAdapter(getApplicationContext(),
				R.layout.row_layout, tvShows);
//		adapter = new SimpleCursorAdapter(this, R.layout.row_layout, getDBCursor,
//				DatabaseOpenHelper.columns, new int[] { R.id._id, R.id.name }, 0);

		listView.setAdapter(adapter);

		inflater = LayoutInflater.from(getApplicationContext());

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {

//				final String dirPath = Environment.getExternalStorageDirectory()+"/QRCode";
//		    		final String filePath = dirPath+"/phonebook.txt";
//		    		Intent msg = new Intent(Intent.ACTION_SEND);

//		    		msg.addCategory(Intent.CATEGORY_DEFAULT);
//		    		msg.putExtra(Intent.EXTRA_SUBJECT, "����");
//		    		msg.putExtra(Intent.EXTRA_TEXT, "����");
//		    		msg.putExtra(Intent.EXTRA_TITLE, "����");
//		    		msg.setType("text/plain");    
//		    		startActivity(Intent.createChooser(msg, "����"));
		    		
//		    		File dir = new File(dirPath);
//		    		if(!dir.exists())
//		    			dir.mkdirs();
//		    		new BufferedWriter(new FileWriter(filePath, true)).append();
			}
		});
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
		int id = item.getItemId();
		if (id == R.id.action_add_item) {
			View dialogView = inflater.inflate(R.layout.dialog_layout,
					(ViewGroup) findViewById(R.id.root_dialog));

			final EditText editDialog = (EditText) dialogView
					.findViewById(R.id.edit_name_dialog);

			AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
					.setTitle("Modify TV show")
					.setMessage("Original Title: ")
					.setPositiveButton("Modify",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

									String editString = editDialog.getText().toString();
									if (editString == null
											|| editString.length() == 0){
										dialog.dismiss();
									}
									else {
										// tvShows[position] = editString;
										// //data changed
										adapter.notifyDataSetChanged();

										Toast.makeText(getApplicationContext(),
												"Modified!", Toast.LENGTH_SHORT)
												.show();
									}
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).create();

			dialog.setView(dialogView);
			dialog.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
