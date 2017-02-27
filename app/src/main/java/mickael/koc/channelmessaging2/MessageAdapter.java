package mickael.koc.channelmessaging2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by kocm on 27/01/2017.
 */
public class MessageAdapter extends ArrayAdapter<Message> {
        private final Context context;
        private final List<Message> values;
        private URL URLurl;
        private File Filefile;

        public MessageAdapter(Context context,List<Message> Value) {
        super(context, R.layout.activity_channel, Value);
        this.context = context;
        this.values = Value;
        }

        public static String downloadFromUrl(String fileURL, String fileName)
        {
                try
                {
                        URL url = new URL(fileURL);
                        File file = new File(fileName);
                        file.createNewFile();
                        URLConnection ucon = url.openConnection();
                        InputStream is = ucon.getInputStream();
                        FileOutputStream fos = new FileOutputStream(file);
                        final int BUFFER_SIZE = 23 * 1024;
                        BufferedInputStream bis = new BufferedInputStream(is, BUFFER_SIZE);
                        byte[] baf = new byte[BUFFER_SIZE];
                        int actual = 0;
                        while (actual != -1) {
                                fos.write(baf, 0, actual);
                                actual = bis.read(baf, 0, BUFFER_SIZE);
                        }
                        fos.close();
                } catch (IOException e) {
                        //TODO HANDLER
                        }
            return fileURL;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rowView = inflater.inflate(R.layout.row_layoutmsg, parent, false);
                TextView textView = (TextView) rowView.findViewById(R.id.textView5);
                TextView stextView = (TextView) rowView.findViewById(R.id.textView6);
                ImageView imgView = (ImageView) rowView.findViewById(R.id.imageView);
                textView.setText(values.get(position).getname()+" : "+values.get(position).getMessage());
                stextView.setText( values.get(position).getdate());
                File imgFile = new  File(Environment.getExternalStorageDirectory()+"/Images/cuteredpandaphoto-2.jpg");
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ImageView myImage = (ImageView) rowView.findViewById(R.id.imageView);
                    myImage.setImageBitmap(myBitmap);
                }
            else
                {
                    Downloader d = new Downloader();
                    d.doInBackground("https://lukaskeapproth.files.wordpress.com/2012/01/cuteredpandaphoto-2.jpg","cuteredpandaphoto-2.jpg");
                    d.execute();/*
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ImageView myImage = (ImageView) rowView.findViewById(R.id.imageView);
                    myImage.setImageBitmap(myBitmap);*/
                }

                return rowView;
        }

        }

