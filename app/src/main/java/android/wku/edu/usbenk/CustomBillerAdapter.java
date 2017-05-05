package android.wku.edu.usbenk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ben on 5/5/2017.
 */

public class CustomBillerAdapter extends ArrayAdapter<Customer> {
    public CustomBillerAdapter(Context context, ArrayList<Customer> billers) {
        super(context, 0, billers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Customer cust = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.billerrow, parent, false);
        }
        // Lookup view for data population
        TextView billerAcctName = (TextView) convertView.findViewById(R.id.billerAcctName);
        // Populate the data into the template view using the data object
        billerAcctName.setText(cust.getName());

        // Return the completed view to render on screen
        return convertView;
    }
}
