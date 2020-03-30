package com.tupt.torqapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tupt.torqapp.Manager.SessionManager;
import com.tupt.torqapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.tupt.torqapp.Manager.URL_Routes.LOGIN;


public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btn_login;
    private ProgressBar loading;
    private ProgressDialog progressDialog;
    private String URL_LOGIN = LOGIN;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        loading = findViewById(R.id.loading);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        //link_register = findViewById(R.id.link_register);

        loading.setVisibility(View.GONE);
        btn_login.setVisibility(View.VISIBLE);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                submitData();
            }
        });
        submitData();


    }

    private void submitData() {
//        loading.setVisibility(View.VISIBLE);
//        btn_login.setVisibility(View.GONE);

//        final String mEmail = email.getText().toString();
//        final String mPass = password.getText().toString();
        final String mEmail = "eriq";
        final String mPass = "qweasd";

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject dataOBJ = jsonObject.getJSONObject("data");
                            String success = dataOBJ.getString("success");

                            if (success.equals("1")) {

                                    JSONObject userOBJ = dataOBJ.getJSONObject("user");
                                    JSONObject roleOBJ = dataOBJ.getJSONObject("role");
                                    String access_token = dataOBJ.getString("access_token");

                                    String username = userOBJ.getString("username").trim();
                                    String title = userOBJ.getString("title").trim();
                                    String first_name = userOBJ.getString("first_name").trim();
                                    String last_name = userOBJ.getString("last_name").trim();
                                    String id = userOBJ.getString("id").trim();
                                    String course_id = userOBJ.getString("course_id").trim();
                                    String role_id = roleOBJ.getString("id").trim();
                                    String role_name = roleOBJ.getString("name").trim();

                                    sessionManager.createSession(title, first_name, last_name , username, id, course_id, role_id, role_name, access_token);

                                    Intent intent= new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("first_name", first_name );
                                    intent.putExtra("title", title );
                                    intent.putExtra("last_name", last_name );
                                    intent.putExtra("username", username);
                                    intent.putExtra("course_id", course_id);
                                    intent.putExtra("role_id", role_id);
                                    intent.putExtra("role_name", role_name);
                                    intent.putExtra("access_token", access_token);
                                    startActivity(intent);

                                    Toast.makeText(LoginActivity.this, "Welcome "+title+" "+first_name+" "+last_name+".", Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(LoginActivity.this,
//                                            "Success Login. \nYour Name:"
//                                                    +first_name+" "+last_name+"\nYour Username: "
//                                                    +username+"\n Course id: "+course_id, Toast.LENGTH_SHORT)
//                                            .show();
                                    loading.setVisibility(View.GONE);



                            } else if(success.equals("0")){
                                Toast.makeText(getApplicationContext(), "Email or password was incorrect.", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyerror) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), volleyerror.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//
////                String credentials = mEmail + ":" + mPass;
////                String encoded = "Basic "+ Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
//                headers.put("username", mEmail);
//                headers.put("password", mPass);
//
//                return headers;
//
//            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", mEmail);
                params.put("password", mPass);

                return params;
            }

        };
        requestQueue.add(stringRequest);

    }
}