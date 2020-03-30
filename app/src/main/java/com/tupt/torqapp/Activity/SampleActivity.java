package com.tupt.torqapp.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.tupt.torqapp.Adapter.PpmpAdapter;
import com.tupt.torqapp.Manager.SessionManager;
import com.tupt.torqapp.Model.Ppmp;
import com.tupt.torqapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tupt.torqapp.Manager.URL_Routes.PPMP_ALL;

public class SampleActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
//    private LinearLayoutManager linearLayoutManager;
    private List<Ppmp> ppmpList;
    private RecyclerView.Adapter ppmpAdapter;
    SessionManager sessionManager;
    String access_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        access_token = user.get(sessionManager.ACCESS_TOKEN);
//        Toast.makeText(getApplicationContext(), "this is the sample activity.", Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.rv_ppmp_all);

        ppmpList = new ArrayList<>();
        ppmpAdapter = new PpmpAdapter(getApplicationContext(),ppmpList);

//        mList.setHasFixedSize(true);
        recyclerView.setAdapter(ppmpAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getData();

//        steps on making a successful recyclerview

//        - create a layout and put the recyclclerview - parent layout
//        - create a layout for the child -
//        - create a model for the response that holds the getter, setter and the constructor
//        - create an adapter
//            - on create*
//            - on bind*
//            - get item count*
//            - create a viewholder
//        - in your activity, create an recyclerview instance and load the data to your model and set it to your arraylist inside rv

    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(
                SampleActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                PPMP_ALL,
                null,
                new Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                Ppmp ppmp = new Ppmp();
                                ppmp.setFiscal_year(jsonObject.getString("fiscal_year"));
                                ppmp.setCourse(jsonObject.getString("course"));
                                ppmpList.add(ppmp);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }
                        ppmpAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }, new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("volley", error.toString());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();

        //                String credentials = mEmail + ":" + mPass;
        //                String encoded = "Basic "+ Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                        headers.put("Authorization", "Bearer " + access_token);

                        return headers;

                    }
                };
        requestQueue.add(jsonArrayRequest);
    }
}