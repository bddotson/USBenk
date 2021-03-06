package android.wku.edu.usbenk;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Account> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent thisIntent = getIntent();
        final Customer currentCust = (Customer) thisIntent.getSerializableExtra("Customer");
        int customerID = currentCust.getId();

        accounts = new ArrayList<>();
        showAccounts(customerID);

        Button paypersonbutton = (Button) findViewById(R.id.paypersonbutton);
        Button paybillbutton = (Button) findViewById(R.id.paybillbutton);
        Button depositcheckbutton = (Button) findViewById(R.id.depositcheckbutton);

        TextView lastLoginView = (TextView) findViewById(R.id.lastLoginView);
        lastLoginView.setText("Your last login was: "+currentCust.getLoginDate());

        TextView accountRefreshView = (TextView) findViewById(R.id.accountRefreshView);
        accountRefreshView.setText("Balances updated as of: "+currentCust.getLoginDate());

        paypersonbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent my = new Intent(MainActivity.this, PayPersonActivity.class);
                my.putExtra("Customer", currentCust);
                startActivity(my);
            }
        });

        paybillbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent my = new Intent(MainActivity.this, PayBillActivity.class);
                my.putExtra("Customer", currentCust);
                startActivity(my);
            }
        });

        depositcheckbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void showAccounts(final int customerID)
    {
        AsyncTask<Integer, Integer, Boolean> asyncTask = new AsyncTask<Integer, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Integer... vars) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(getString(R.string.server)+"/showaccounts.php?id="+customerID)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
                    String time = df.format(new Date());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        Account account = new Account(object.getInt("accountID"), object.getInt("customerID"),
                                object.getInt("balance"), object.getString("accountName"), time);

                        MainActivity.this.accounts.add(account);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                requestListener(result);
            }
        };

        asyncTask.execute(customerID);
    }

    public void requestListener(Boolean result)
    {
        if(result)
        {
            ListView lv = (ListView) findViewById(R.id.listView);
            CustomAccountAdapter arrayAdapter = new CustomAccountAdapter(this, this.accounts);
            lv.setAdapter(arrayAdapter);

            TextView accountRefreshView = (TextView) findViewById(R.id.accountRefreshView);
            accountRefreshView.setText("Balances updated as of: "+accounts.get(0).getDate());
        }
        else{

        }
    }


}
