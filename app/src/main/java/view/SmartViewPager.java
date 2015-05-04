package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.example.mac.viewpagerlistview.R;


public class SmartViewPager extends ViewPager {

	private float ratio;

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public SmartViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public SmartViewPager(Context context) {
		super(context);
	}

	private void init(Context context, AttributeSet attrs) {
		int[] styleableIds = R.styleable.smart_viewpager;
		float ratio = getViewPagerRatio(context, attrs, styleableIds);
		setRatio(ratio);
	}

	private float getViewPagerRatio(Context context, AttributeSet attrs,
			int[] styleableIds) {
		TypedArray attributes = context.obtainStyledAttributes(attrs,
				styleableIds);
		float ratio = 0.0f;

		int count = attributes.getIndexCount();

		for (int i = 0; i < count; i++) {
			int styleableId = attributes.getIndex(i);

			switch (styleableId) {
			case R.styleable.smart_viewpager_ratio:
				ratio = attributes.getFloat(styleableId, 0.0f);
				break;

			default:
				break;
			}
		}

		return ratio;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int wSize = MeasureSpec.getSize(widthMeasureSpec);
		int wMode = MeasureSpec.getMode(widthMeasureSpec);

		int hSize = MeasureSpec.getSize(heightMeasureSpec);
		int hMode = MeasureSpec.getMode(heightMeasureSpec);

		if (wMode == MeasureSpec.EXACTLY && hMode != MeasureSpec.EXACTLY
				&& ratio != 0.0f) {
			hSize = (int) (wSize / ratio + 0.5f);
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(hSize,
					MeasureSpec.EXACTLY);
		} else if (hMode == MeasureSpec.EXACTLY && wMode != MeasureSpec.EXACTLY
				&& ratio != 0.0f) {
			wSize = (int) (hSize * ratio + 0.5f);
			widthMeasureSpec = MeasureSpec.makeMeasureSpec(wSize,
					MeasureSpec.EXACTLY);
		}

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
