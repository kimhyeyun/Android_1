package com.example.practice;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;

public class NewsDetailActivity extends Activity {

    private NewsData news;
    private TextView TextView_title,TextView_Description;
    public SimpleDraweeView ImageView_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);
        setComp();
        getNewsDetail();
        setNews();
    }

    //기본 컴포넌트 셋팅
    private void setComp() {
        TextView_title=findViewById(R.id.TextView_title);
        TextView_Description=findViewById(R.id.TextView_Description);
        ImageView_title=findViewById(R.id.ImageView_title);
    }

    //이전 액티비티에서 받아오는 인텐트
    public void getNewsDetail(){
        Intent intent = getIntent();
        if(intent!=null){
            Bundle bld=intent.getExtras();

            Object obj=bld.get("news");

            //intanceof : 참조변수가 참조하고 있는 인스턴스의 실제 타입을 알기 위함
            //왼쪽에는 참조변수, 오른쪽에는 타입(클래스명), 연산값은 boolean 값
            if(obj!=null&&obj instanceof  NewsData){
                this.news = (NewsData)obj;
            }
        }
    }

    //이전 액티비티에서 받아오는 인텐드에서 정보를 확인하여 뉴스표시
    public void setNews(){
        if(this.news!=null){
            String title= this.news.getTitle();
            if(title!=null){
                TextView_title.setText(title);
            }
            String description = this.news.getdescription();
            if(description!=null){
                //전체 본문은 url 값의 실제 뉴스 사이트에 있으며, 해당 전체 본문을 불러오기 위해서는  스크래핑 (크롤링) 기술로 읽어와야함
                TextView_Description.setText(description);
            }

            Uri uri = Uri.parse(news.getUrlToImage());

            ImageView_title.setImageURI(uri);
        }
    }

}
