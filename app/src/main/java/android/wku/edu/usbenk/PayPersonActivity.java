package android.wku.edu.usbenk;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PayPersonActivity extends AppCompatActivity {

    private ArrayList<Account> accounts;
    private List<Customer> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_bill);

        people = new ArrayList<>();
        Intent thisIntent = getIntent();
        Customer currentCust = (Customer) thisIntent.getSerializableExtra("Customer");
        int customerID = currentCust.getId();
        showPeople(customerID);
        accounts = new ArrayList<>();
        showAccounts(customerID);
    }

    private void showPeople(final int customerID)
    {
        AsyncTask<Integer, Integer, Boolean> asyncTask = new AsyncTask<Integer, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Integer... vars) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(getString(R.string.server)+"/showpeople.php?id="+customerID)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
                    String time = df.format(new Date());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        Customer customer = new Customer(object.getInt("customerID"), object.getString("name"),
                                object.getString("username"), object.getString("password"), time);

                        PayPersonActivity.this.people.add(customer);
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
                requestListenerForBillers(result);
            }
        };

        asyncTask.execute(customerID);
    }

    public void requestListenerForBillers(Boolean result)
    {
        if(result)
        {
            ListView lv2 = (ListView) findViewById(R.id.listView);
            ArrayAdapter<Customer> arrayAdapter2 = new ArrayAdapter<Customer>(
                    this, android.R.layout.simple_list_item_1, people
            );
            lv2.setAdapter(arrayAdapter2);
        }
        else{

        }
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

                        PayPersonActivity.this.accounts.add(account);
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
                requestListenerForAccounts(result);
            }
        };

        asyncTask.execute(customerID);
    }

    public void requestListenerForAccounts(Boolean result)
    {
        if(result)
        {
            ListView lv = (ListView) findViewById(R.id.listView2);
            CustomAdapter arrayAdapter = new CustomAdapter(this, this.accounts);
            lv.setAdapter(arrayAdapter);

        }
        else{

        }
    }
}
