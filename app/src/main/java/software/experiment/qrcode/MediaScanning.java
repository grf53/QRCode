<<<<<<< HEAD
package software.experiment.qrcode;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

import java.io.File;

/**
 * Created by 박수진 on 2015-06-19.
 */
public class MediaScanning implements MediaScannerConnection.MediaScannerConnectionClient {

    private MediaScannerConnection mConnection;
    private File mTargetFile;

    public MediaScanning(Context mContext, File targetFile) {
        this.mTargetFile = targetFile;

        mConnection = new MediaScannerConnection(mContext, this);
        mConnection.connect();
    }

    @Override
    public void onMediaScannerConnected() {
        mConnection.scanFile(mTargetFile.getAbsolutePath(), null);
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        mConnection.disconnect();
    }
=======
package software.experiment.qrcode;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

import java.io.File;

/**
 * Created by 박수진 on 2015-06-19.
 */
public class MediaScanning implements MediaScannerConnection.MediaScannerConnectionClient {

    private MediaScannerConnection mConnection;
    private File mTargetFile;

    public MediaScanning(Context mContext, File targetFile) {
        this.mTargetFile = targetFile;

        mConnection = new MediaScannerConnection(mContext, this);
        mConnection.connect();
    }

    @Override
    public void onMediaScannerConnected() {
        mConnection.scanFile(mTargetFile.getAbsolutePath(), null);
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        mConnection.disconnect();
    }
>>>>>>> origin/master
}