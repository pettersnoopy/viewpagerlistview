package com.example.mac.viewpagerlistview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mac.viewpagerlistview.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapter.MyListViewAdapter;
import holder.ViewHolder;
import view.InterceptorFrameLayout;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private List<String> items = new ArrayList<String>();

    private ListView mListView;

    private List<Integer> resList = Arrays.asList(R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        mListView = new ListView(this);

        ViewHolder holder = new ViewHolder(resList);

        View mHeaderView = holder.getRootView();
        mListView.addHeaderView(mHeaderView);
        mListView.setAdapter(new MyListViewAdapter(items, mListView.getHeaderViewsCount()));
        mListView.setOnItemClickListener(this);

        for (int i = 0; i < 20; i++) {
            StringBuffer sb = new StringBuffer();
            sb.append("item");
            sb.append(i);
            items.add(sb.toString());
        }

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        InterceptorFrameLayout ifl = new InterceptorFrameLayout(MainActivity.this);

        ifl.addView(mListView);
        ifl.addInterceptorView(mHeaderView, InterceptorFrameLayout.ORIENTATION_LEFT | InterceptorFrameLayout.ORIENTATION_RIGHT);
        rl.addView(ifl);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MainActivity.this, "item" + (position - mListView.getHeaderViewsCount()) + "被点击了！", Toast.LENGTH_SHORT).show();
    }
}
