package mickael.koc.channelmessaging2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kocm on 27/01/2017.
 */
public class MessageAdapter extends ArrayAdapter<Message> {
private final Context context;
private final List<Message> values;

public MessageAdapter(Context context,List<Message> Value) {
        super(context, R.layout.activity_channel, Value);
        this.context = context;
        this.values = Value;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layoutmsg, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.textView5);
        TextView stextView = (TextView) rowView.findViewById(R.id.textView6);
        textView.setText(values.get(position).getname()+" : "+values.get(position).getMessage());
        stextView.setText( values.get(position).getdate());
        return rowView;
        }



        }

