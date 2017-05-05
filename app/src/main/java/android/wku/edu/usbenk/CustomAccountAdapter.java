package android.wku.edu.usbenk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ben on 5/3/2017.
 */


public class CustomAccountAdapter extends ArrayAdapter<Account> {
    public CustomAccountAdapter(Context context, ArrayList<Account> accounts) {
        super(context, 0, accounts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Account acc = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        }
        // Lookup view for data population
        TextView acctName = (TextView) convertView.findViewById(R.id.acctName);
        TextView balance = (TextView) convertView.findViewById(R.id.balance);
        // Populate the data into the template view using the data object
        acctName.setText(acc.getAccountName());
        balance.setText("$"+Integer.toString(acc.getBalance()));
        // Return the completed view to render on screen
        return convertView;
    }
}

