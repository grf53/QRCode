package software.experiment.qrcode;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by ¼Ö on 2015-06-10.
 */
public class MyDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder theDialog = new AlertDialog.Builder(getActivity());

       // theDialog.setTitle("Do you want to add new item?");


       theDialog.setMessage("Do you want to add new item?");




        theDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(getActivity(), "Clicked ADD", Toast.LENGTH_SHORT).show();

            }
        });

        	        // Add text for a negative button
        	        theDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            	            @Override
            	            public void onClick(DialogInterface dialogInterface, int i) {

                	                Toast.makeText(getActivity(), "Clicked No", Toast.LENGTH_SHORT).show();

                	            }
            	        });

        	        // Returns the created dialog
        	        return theDialog.create();

    }
}
