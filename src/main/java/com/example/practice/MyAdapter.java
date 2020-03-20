package com.example.practice;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<NewsData> mDataset;
    private static View.OnClickListener onClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    //뷰 홀더가 반복되면서 onBind~ 함수에서 값을 세팅
    public static class MyViewHolder extends RecyclerView.ViewHolder {//그 안의 요소를 찾아가는
        //RecyclerView.ViewHolder : android가 제공, 메모리 효율적등등
        // each data item is just a string in this case
        public TextView TextView_Title;
        public TextView TextView_description;
        public SimpleDraweeView ImageView_Title;
        public View rootView;
        public MyViewHolder(View v) {
            super(v);
            TextView_Title      = v.findViewById(R.id.TextView_Title);
            TextView_description    = v.findViewById(R.id.TextView_description);
            ImageView_Title     = v.findViewById(R.id.ImageView_Title);
            //부모의 xml뷰가 누군지 알수 없으므로 명확하게 row_new.xml이 부모라고 지정한것
            rootView=v;
            v.setClickable(true);  //누를수 있냐 없냐
            v.setEnabled(true);   //활성화상태이냐 아니야
            v.setOnClickListener(onClickListener);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<NewsData> myDataset, Context context,View.OnClickListener onClick) {
        //NewsActivity에서의 값이 이로 넘어옴
        //뷰 홀더가 원본 데이터의 크기만큼 반복
        mDataset = myDataset;
        onClickListener=onClick;
        Fresco.initialize(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) { //recyclerview의 항목화면 연결
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_news, parent, false);
        //inflate: 특정한 컴포넌트(여기서는 리사이클러 뷰)의 특정 항목의 레이아웃을 바꿈
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    //값 세팅
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) { //binding 함
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        NewsData news = mDataset.get(position);
        holder.TextView_Title.setText(news.getTitle()); //string 배열을 textview_title로

        String content=news.getdescription();
        if(content!="null" && content.length()>0){
            holder.TextView_description.setText(content);
        }
        else{
            holder.TextView_description.setText("-");
        }

        Uri uri = Uri.parse(news.getUrlToImage());

        holder.ImageView_Title.setImageURI(uri);

        //tag-label
        holder.rootView.setTag(position);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //삼항연산자
        return mDataset==null?0:mDataset.size();
    }

    public NewsData getNews(int position){
        return mDataset!=null?mDataset.get(position):null;
    }
}