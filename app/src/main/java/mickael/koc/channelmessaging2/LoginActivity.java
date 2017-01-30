package mickael.koc.channelmessaging2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements onDownloadCompleteListener, View.OnClickListener {

    private Button btnvalider;
    private EditText edtlogin;
    private EditText edtpassword;
    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String message = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnvalider = (Button) findViewById(R.id.button);
        edtlogin = (EditText) findViewById(R.id.edtlogin);
        edtpassword = (EditText) findViewById(R.id.edtpassword);
        btnvalider.setOnClickListener(this);
    }

    @Override
    public void onDownloadComplete(String news, int requestCode) {
        Gson gson = new Gson();
        Jsonlogin obj2 = gson.fromJson(news, Jsonlogin.class);

        if(obj2.code==200) {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("access", obj2.getaccess());
            editor.commit();
            SharedPreferences Gsettings = getSharedPreferences(PREFS_NAME, 0);
            String access = Gsettings.getString("access", "");
            changeActivity();

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Identifiant incorrect", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button:
                    HashMap<String,String> n = new HashMap<String,String>();
                    n.put("url","http://www.raphaelbischof.fr/messaging/?function=connect");
                    n.put("username",edtlogin.getText().toString());
                    n.put("password",edtpassword.getText().toString());
                    Datalayer d = new Datalayer(n,0);
                    d.setOnNewsDownloadComplete(this);
                    d.execute();
                break;
        }


    }

    public void changeActivity()
    {
        Intent myintent = new Intent(getApplicationContext(),ChannelActivity.class);
        startActivity(myintent);
    }

}
