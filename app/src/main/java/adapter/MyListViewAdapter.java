package adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mac.viewpagerlistview.R;

import java.util.List;
import java.util.Objects;

import application.BaseApplication;

/**
 * Created by mac on 15/5/4.
 */
public class MyListViewAdapter extends BaseAdapter {
    private List<String> items;
    private int ListViewHeaderCounts;

    public MyListViewAdapter(List<String> items, int ListViewHeaderCounts) {
        this.items = items;
        this.ListViewHeaderCounts = ListViewHeaderCounts;
    }

    ListViewItemHolder holder;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {

        // int headerViewsCount = mListView.getHeaderViewsCount();
        // return position - headerViewsCount;
        return position - ListViewHeaderCounts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            holder = (ListViewItemHolder) convertView.getTag();
        } else {
            convertView = View.inflate(BaseApplication.getContext(),
                    R.layout.list_item, null);
            holder = new ListViewItemHolder();

            convertView.setTag(holder);
            holder.tv = (TextView) convertView.findViewById(R.id.tv);
        }
        holder.tv.setText(items.get(position));
        return convertView;
    }

    class ListViewItemHolder {
        TextView tv;
    }
}
