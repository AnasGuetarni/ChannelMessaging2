package mickael.koc.channelmessaging2;

import android.os.AsyncTask;
import java.lang.String;

/**
 * Created by anas on 06/02/17.
 */

public class Downloader extends AsyncTask<String, Integer, Void> {

    private String fileURL;
    private String fileName;

    @Override
    protected Void doInBackground(String... params) {
        MessageAdapter.downloadFromUrl(this.fileURL,this.fileName);
        return null;
    }

    @Override
    protected void onPostExecute(Void s) {
        super.onPostExecute(s);
    }
}
