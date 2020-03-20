package com.example.practice;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String[] mDataset ={"1","2"} ;
    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        // Instantiate the RequestQueue.
        // 네트워크 통신을 하기위해서 queue (FIFO)
        queue = Volley.newRequestQueue(this);
        getNews();
        //1. 화면이 로딩 -> 뉴스 정보를 받아온다-------
        //2. 정보 -> 어댑터 넘겨준다
        //3. 어댑터 -> 셋팅
    }

    public void getNews(){
        String url ="http://newsapi.org/v2/top-headlines?country=kr&apiKey=5a94ab08a8144abeafba90868168ae93";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("NEWS",response);

                        //얼굴 인식 -> 스티커
                        //문자열을 JSON 형태로 빼오겠다.
                        try {
                            JSONObject jsonObj=new JSONObject(response);

                            //articles라는 이름을 가진 배열을 가져와라
                            JSONArray arrayArticles = jsonObj.getJSONArray("articles");

                            //response ->> NewsData Class 분류
                            List<NewsData> news=new ArrayList<>();

                            for(int i=0,j=arrayArticles.length();i<j;i++){
                               JSONObject obj = arrayArticles.getJSONObject(i);

                               // Log.d("NEWS",obj.toString());

                                NewsData newsData=new NewsData();
                                newsData.setTitle(obj.getString("title"));
                                newsData.setUrlToImage(obj.getString("urlToImage"));
                                newsData.setdescription(obj.getString("description"));

                                news.add(newsData);
                            }

                            // specify an adapter (see also next example)
                            mAdapter = new MyAdapter(news, NewsActivity.this, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Object obj=v.getTag();
                                    if(obj!=null){ //null check는 비어있으면 밑에서 오류
                                        int position=(int)obj;
                                        ((MyAdapter)mAdapter).getNews(position);
                                        Intent intent = new Intent (NewsActivity.this,NewsDetailActivity.class);
                                        intent.putExtra("news",((MyAdapter)mAdapter).getNews(position));

                                        //1. 본문만 넘기기 ((MyAdapter)mAdapter).getNews(position).getdescription();
                                        //2. 전체를 다 넘기기
                                            //2-1. 하나씩 ~
                                            //2-2. 한번에 ~

                                        //1.햄버거 메뉴
                                        //2.채팅앱

                                        startActivity(intent);
                                    }
                                }
                            });
                            recyclerView.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
