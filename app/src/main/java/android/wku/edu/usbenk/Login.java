package android.wku.edu.usbenk;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Login extends AppCompatActivity {

    private List<Customer> customers;
    private Boolean ready;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ready = false;
        Button login = (Button) findViewById(R.id.loginButton);

        customers = new ArrayList<>();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText user = (EditText) findViewById(R.id.userLogin);
                EditText pass = (EditText) findViewById(R.id.passLogin);
                TextView error = (TextView) findViewById(R.id.errorText);

                if(user.getText()!= null && pass.getText() != null){
                    String username = user.getText().toString();
                    String password = pass.getText().toString();

                    checkLogin(username, password);
                }
            }
        });

    }

    private void checkLogin(final String username, final String password)
    {
        AsyncTask<String, String, Boolean> asyncTask = new AsyncTask<String, String, Boolean>() {
            @Override
            protected Boolean doInBackground(String... vars) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(getString(R.string.server)+"/checklogin.php?username="+username+"&&password="+password)
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

                        Login.this.customers.add(customer);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    return false;
                }
                if(customers.get(0).getUsername().equals(username) && customers.get(0).getPassword().equals(password))
                {
                    return true;
                }
                else
                    return false;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                requestListener(result);
            }
        };

        asyncTask.execute(username);
    }

    public void requestListener(Boolean result)
    {
        if(result)
        {
            Intent my = new Intent(Login.this, MainActivity.class);
            my.putExtra("Customer", customers.get(0));
            startActivity(my);
        }
        else{
            TextView error = (TextView) findViewById(R.id.errorText);
            error.setText("Bad username or password");
        }
    }

}
