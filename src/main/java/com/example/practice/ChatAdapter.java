package com.example.practice;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatData> mDataset;
    private String myNickName;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    //뷰 홀더가 반복되면서 onBind~ 함수에서 값을 세팅
    public static class MyViewHolder extends RecyclerView.ViewHolder {//그 안의 요소를 찾아가는
        //RecyclerView.ViewHolder : android가 제공, 메모리 효율적등등
        // each data item is just a string in this case
        public TextView TextView_nickname;
        public TextView TextView_msg;
        public View rootView;
        public MyViewHolder(View v) {
            super(v);
            TextView_nickname      = v.findViewById(R.id.TextView_nickname);
            TextView_msg    = v.findViewById(R.id.TextView_msg);
            //부모의 xml뷰가 누군지 알수 없으므로 명확하게 row_new.xml이 부모라고 지정한것
            rootView=v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatAdapter(List<ChatData> myDataset, Context context, String myNickName) {
        //NewsActivity에서의 값이 이로 넘어옴
        //뷰 홀더가 원본 데이터의 크기만큼 반복
        mDataset = myDataset;
        this.myNickName=myNickName;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //recyclerview의 항목화면 연결
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat, parent, false);
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
        ChatData chat = mDataset.get(position);

        holder.TextView_nickname.setText(chat.getNickname());
        holder.TextView_msg.setText(chat.getMsg()); //DTO

        if(chat.getNickname().equals(this.myNickName)){
            holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

        }
        else{
            holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //삼항연산자
        return mDataset==null?0:mDataset.size();
    }

    public ChatData getChat(int position){

        return mDataset!=null?mDataset.get(position):null;
    }

    //뉴스랑 달리 이전 데이터들이 유지되어야함
    public void addChat(ChatData chat){
        mDataset.add(chat);
        notifyItemInserted(mDataset.size()-1);
        //add와 같이 데이터가 삽입되는 것의 변화를 위해
    }
}