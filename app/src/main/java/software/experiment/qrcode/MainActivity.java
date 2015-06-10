package software.experiment.qrcode;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.io.File;
import java.io.FileWriter;

import software.experiment.qrcode.login.LoginActivity;

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

        Intent loginActivityIntent = new Intent(getApplicationContext(),
                LoginActivity.class);
        startActivity(loginActivityIntent);

        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        adapter = new CustomAdapter(getApplicationContext(),
                R.layout.row_layout, tvShows);
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
//		    		msg.putExtra(Intent.EXTR
// A_SUBJECT, "林力");
//		    		msg.putExtra(Intent.EXTRA_TEXT, "郴侩");
//		    		msg.putExtra(Intent.EXTRA_TITLE, "力格");
//		    		msg.setType("text/plain");
//		    		startActivity(Intent.createChooser(msg, "傍蜡"));

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

            final EditText editDialog_name = (EditText) dialogView
                    .findViewById(R.id.edit_name_dialog);
            final EditText editDialog_price = (EditText) dialogView
                    .findViewById(R.id.edit_price_dialog);

            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Add New Item")
                    .setMessage("Put information ")
                    .setPositiveButton("Add",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {


                                    String editString_name = editDialog_name.getText().toString();
                                    String editString_price=editDialog_price.getText().toString();
                                    File file = new File("log.txt");
                                    FileWriter fw=null;

                                        Toast.makeText(getApplicationContext(),
                                                editString_name + ":" + editString_price, Toast.LENGTH_SHORT)
                                                .show();


                                    if (editString_name == null
                                            || editString_name.length() == 0){
                                        dialog.dismiss();
                                    }
                                    else {
                                        // tvShows[position] = editString;
                                        // //data changed
                                        adapter.notifyDataSetChanged();

                                        Toast.makeText(getApplicationContext(),
                                                "Added!", Toast.LENGTH_SHORT)
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
