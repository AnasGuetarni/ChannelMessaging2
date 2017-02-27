package mickael.koc.channelmessaging2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Button;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by guetarna on 27/01/2017.
 */
public class MessageActivity extends AppCompatActivity implements onDownloadCompleteListener, View.OnClickListener{

    public static final String PREFS_NAME = "MyPrefsFile";
    private static final int PICTURE_REQUEST_CODE = 0;
    private List<Message> lstmessage;
    public ListView lstvmessage;
    private Button btnPhoto;
    private Button btnEnvoyer;
    public EditText editText;
    private Handler handler;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        lstvmessage = (ListView)findViewById(R.id.listView2);
        editText = (EditText)findViewById(R.id.editText);


        btnEnvoyer = (Button) findViewById(R.id.button2);
        btnEnvoyer.setOnClickListener(this);

        btnPhoto = (Button) findViewById(R.id.button3);
        btnPhoto.setOnClickListener(this);

        handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                SharedPreferences Gsettings = getSharedPreferences(PREFS_NAME, 0);
                String access = Gsettings.getString("access", "");
                String channelid = Gsettings.getString("channelid","");
                HashMap<String,String> n = new HashMap<String,String>();
                n.put("url","http://www.raphaelbischof.fr/messaging/?function=getmessages");
                n.put("accesstoken",access);
                n.put("channelid",channelid);
                Datalayer d = new Datalayer(n,0);
                d.setOnNewsDownloadComplete(MessageActivity.this);
                d.execute();
                handler.postDelayed(this, 200);
            }
        };
        handler.postDelayed(r, 200);
    }

    @Override
    public void onDownloadComplete(String news, int requestCode) {
        //Toast.makeText(getApplicationContext(),news,Toast.LENGTH_SHORT).show();
        if (requestCode == 0) {
            Gson gson = new Gson();
            Messages obj2 = gson.fromJson(news, Messages.class);
            lstmessage=obj2.getlist();
            lstvmessage.setAdapter(new MessageAdapter(getApplicationContext(),lstmessage));
        }
        else {}

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button2:
                Toast.makeText(getApplicationContext(), "Envoyé !", Toast.LENGTH_SHORT).show();
                SharedPreferences Gsettings = getSharedPreferences(PREFS_NAME, 0);
                String access = Gsettings.getString("access", "");
                String channelid = Gsettings.getString("channelid","");
                String message = Gsettings.getString("message", "");
                HashMap<String,String> n = new HashMap<String,String>();
                n.put("url","http://www.raphaelbischof.fr/messaging/?function=sendmessage");
                n.put("accesstoken",access);
                n.put("channelid",channelid);
                n.put("message", editText.getText().toString());
                Datalayer d = new Datalayer(n,1);
                d.setOnNewsDownloadComplete(this);
                d.execute();
                editText.setText("");

            case R.id.button3:
               /* Uri uri = Uri.fromFile(new File("https://lukaskeapproth.files.wordpress.com/2012/01/cuteredpandaphoto-2.jpg"));
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                startActivityForResult(intent,PICTURE_REQUEST_CODE);*/
                break;
        }

    }
}
