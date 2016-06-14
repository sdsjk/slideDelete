package com.example.zs.svndemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.base.CommonBaseAdapter;
import com.example.adapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listview;

    private List<String> all = new ArrayList<>();
    private CommonBaseAdapter<String> adapter;
    private SlideLayout slideLayout1;
    private SlideLayout.OnStateChangeListener listener=new SlideLayout.OnStateChangeListener() {
        @Override
        public void onDown(SlideLayout slideLayout) {
          if( slideLayout1!=null&&slideLayout!=slideLayout1){
              slideLayout1.close();
          }
        }

        @Override
        public void onOpen(SlideLayout slideLayout) {
           MainActivity.this.slideLayout1=slideLayout;
        }

        @Override
        public void onClose(SlideLayout slideLayout) {
         if(slideLayout==slideLayout1){
             slideLayout1=null;
         }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.listview);

        for (int i = 0; i < 50; i++) {
            all.add("Tom" + i);

        }

        adapter = new CommonBaseAdapter<String>(this, all, R.layout.item_slide) {
            @Override
            public void convert(ViewHolder holder, int position) {
                holder.setText(R.id.tv_item_content, all.get(position));
                holder.setOnclickListener(R.id.tv_item_content, MainActivity.this);
                holder.getView(R.id.tv_item_content).setTag(position);
                holder.setOnclickListener(R.id.tv_item_menu, MainActivity.this);
                holder.getView(R.id.tv_item_menu).setTag(position);
               SlideLayout slideLayout= (SlideLayout) holder.getConvertView();
                slideLayout.setOnstatechangelistener(listener);


            }
        };
        listview.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
          int postion= (int) v.getTag();
        switch (v.getId()) {
            case  R.id.tv_item_content:
            Toast.makeText(MainActivity.this, all.get(postion), Toast.LENGTH_SHORT).show();
                break;
            
            case R.id.tv_item_menu:
                all.remove(postion);
                adapter.notifyDataSetChanged();
                SlideLayout sl= (SlideLayout) v.getParent();
                sl.close();

                break;
        }
    }
}
