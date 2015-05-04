package holder;

import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.mac.viewpagerlistview.R;

import java.util.HashMap;
import java.util.List;

import application.BaseApplication;
import view.SmartViewPager;

/**
 * Created by mac on 15/5/4.
 */
public class ViewHolder implements View.OnTouchListener {

    private View viewToReturn;
    private List<Integer> mDatas;
    private SmartViewPager viewPager;
    private RelativeLayout.LayoutParams rlp;
    private AutoPlayTask autoPlayTask;
    private final int AUTO_PLAY_TIME = 2000;

    public ViewHolder(List<Integer> data) {
        viewToReturn = initView();
        viewToReturn.setTag(this);
        setData(data);
    }

    public View getRootView() {
        return viewToReturn;
    }

    public void setData(List<Integer> data) {
        this.mDatas = data;
        refreshView();
    }

//    public View getViewToReturn() {
//        this.mDatas = data;
//        refreshView();
//    }

    public void refreshView() {
        mDatas = getData();
        viewPager.setCurrentItem(0);
    }

    public List<Integer> getData() {
        return this.mDatas;
    }

    // 初始化View
    protected View initView() {
        RelativeLayout mHeaderView = new RelativeLayout(BaseApplication.getContext());

        // 设置轮播图的宽高
        // 设置LayoutParams的时候，一定要带一个父容器的限制，这里使用AbsListView的原因是因为ListView没有.LayoutParams选项
        mHeaderView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        viewPager = new SmartViewPager(BaseApplication.getContext());
        viewPager = (SmartViewPager) View.inflate(BaseApplication.getContext(), R.layout.list_header, null);
        rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        viewPager.setLayoutParams(rlp);

        ViewPagerAdapter adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        autoPlayTask = new AutoPlayTask();
        autoPlayTask.start();
        viewPager.setOnTouchListener(this);
        mHeaderView.addView(viewPager);
        return mHeaderView;
    }

    private class ViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        private HashMap<Integer, Drawable> drawables = new HashMap<Integer, Drawable>();

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(BaseApplication.getContext());

            Integer id = mDatas.get(position % mDatas.size());
            Drawable drawable = BaseApplication.getContext().getResources().getDrawable(id);
            imageView.setImageDrawable(drawable);

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }

    private class AutoPlayTask implements Runnable {
        private boolean IS_PLAYING = false;

        @Override
        public void run() {
            if (IS_PLAYING) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                BaseApplication.getMainThreadHandler().postDelayed(this, AUTO_PLAY_TIME);
            }
        }

        public void stop() {
            BaseApplication.getMainThreadHandler().removeCallbacks(this);
            IS_PLAYING = false;
        }

        public void start() {
            if (!IS_PLAYING) {
                IS_PLAYING = true;
                BaseApplication.getMainThreadHandler().removeCallbacks(this);
                BaseApplication.getMainThreadHandler().postDelayed(this, AUTO_PLAY_TIME);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_CANCEL) {
            autoPlayTask.stop();
        } else if (action == MotionEvent.ACTION_UP) {
            autoPlayTask.start();
        }
        return false;
    }




}
