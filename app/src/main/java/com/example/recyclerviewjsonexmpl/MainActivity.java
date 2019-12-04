package com.example.recyclerviewjsonexmpl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExampleAdapter.OnItemClickListener {

    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String LIKE_COUNT = "likeCount";

    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExampleList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJson();
    }

    private void parseJson(){
        String url = "https://pixabay.com/api/?key=14493715-eabd191744c9a9431438f3f7f&q=yellow+flowers&image_type=photo&pretty=true";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject hit = jsonArray.getJSONObject(i);
                        String creatorName = hit.getString("user");
                        String imageUrl = hit.getString("webformatURL");
                        int likes = hit.getInt("likes");

                        mExampleList.add(new ExampleItem(imageUrl, creatorName, likes));
                    }

                    mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
                    mRecyclerView.setAdapter(mExampleAdapter);
                    mExampleAdapter.setOnItemClickListener(MainActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent toDetailActivity = new Intent(MainActivity.this, DetailActivity.class);
        ExampleItem selectedItem = mExampleList.get(position);
        toDetailActivity.putExtra(EXTRA_URL, selectedItem.getImageUrl());
        toDetailActivity.putExtra(EXTRA_CREATOR, selectedItem.getCreator());
        toDetailActivity.putExtra(LIKE_COUNT, selectedItem.getmLikes());
        startActivity(toDetailActivity);
    }
}
