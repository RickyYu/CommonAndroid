package com.safetys.nbsxs.ui.view;

import java.text.SimpleDateFormat;

import cn.safetys.nbsxs.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PullToRefresh extends ListView implements OnScrollListener {

	private ProgressBar pb;
	private TextView tv_state;
	private TextView tv_time;
	private ImageView iv_arrow;
	private float downY;
	private View header;
	private int headerHeight;
	private int firstVisibleItem;
	private boolean needRefresh = true;
	private Paint mPaint;

	public PullToRefresh(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		// initHeader();
		initFooter();
		setListener();
		
		mPaint = new Paint();
		textSize = (int) getResources().getDimension(R.dimen.normal_text_size);
		mPaint.setColor(getResources().getColor(R.color.grey));
		mPaint.setTextSize(textSize);
	}

	private void setListener() {
		setOnScrollListener(this);
	}

	private void initFooter() {
		footer = View.inflate(getContext(), R.layout.footer_pull_refresh_progress, null);
		footer.measure(0, 0);
		footerHeight = footer.getMeasuredHeight();
		footer.setPadding(0, -footerHeight, 0, 0);
		this.addFooterView(footer);
	}

	// private void initHeader() {

	// header = View.inflate(getContext(), R.layout.header, null);
	// tv_state = (TextView) header.findViewById(R.id.tv_state);
	// tv_time = (TextView) header.findViewById(R.id.tv_time);
	// pb = (ProgressBar) header.findViewById(R.id.pb);
	// iv_arrow = (ImageView) header.findViewById(R.id.iv_arrow);
	//
	// // 注:
	// // 1,加载.2,测量.3,布局.4,绘制
	// // int height = header.getHeight();
	// // Log.i("test", "height:"+height);
	// // 提前对header进行测量
	// header.measure(0, 0);
	// headerHeight = header.getMeasuredHeight();
	// // Log.i("test", "height:"+headerHeight);
	//
	// finishLoading(true);
	// this.addHeaderView(header);
	//
	// }

	private boolean isRecorded = false;
	// 定义状态
	// 利用枚举定义状态
	// 枚举里面的元素没有实际值,是枚举值,不占用任何的实际值
	// 使用枚举和使用类一样
	// 枚举里面的属性都是常量,多个状态之间用","区分
	private PullState state;

	public enum PullState {
		PULL_TO_REFRESH, RELEASH_REFRESH, REFRESHING
	}

	// @Override
	// public boolean onTouchEvent(MotionEvent ev) {
	// switch (ev.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	// if (!isRecorded && firstVisibleItem == 0) {
	// downY = ev.getY();
	// isRecorded = true;
	// }
	// break;
	// case MotionEvent.ACTION_MOVE:
	//
	// if (!isRecorded && firstVisibleItem == 0) {
	// downY = ev.getY();
	// isRecorded = true;
	// }
	// float moveY = ev.getY();
	// float dY = moveY - downY;
	//
	// if (isRecorded && dY >= 0 && state != PullState.REFRESHING) {
	//
	// // Log.i("test", "dY:"+dY);
	// int top = (int) (-headerHeight + dY);
	// if (top >= 0 && state == PullState.PULL_TO_REFRESH) {
	// state = PullState.RELEASH_REFRESH;
	// tv_state.setText("释放刷新");
	// Log.i("test", "释放刷新");
	// animation(true);
	// } else if (top < 0 && state == PullState.RELEASH_REFRESH) {
	// state = PullState.PULL_TO_REFRESH;
	// tv_state.setText("下拉刷新");
	// Log.i("test", "下拉刷新");
	// animation(false);
	// }
	// header.setPadding(0, top, 0, 0);
	//
	// // 当符合下拉条件的时候,截断事件,由当前自定义控件处理,其他情况交由ListView的滑动事件处理
	// return true;
	// }
	//
	// break;
	// case MotionEvent.ACTION_UP:
	// isRecorded = false;
	// if (state == PullState.PULL_TO_REFRESH) {
	// header.setPadding(0, -headerHeight, 0, 0);
	// } else if (state == PullState.RELEASH_REFRESH) {
	// state = PullState.REFRESHING;
	// tv_state.setText("正在刷新...");
	// // 在执行动画的控件,调用隐藏不起作用
	// // 清除控件身上的动画
	// iv_arrow.clearAnimation();
	// iv_arrow.setVisibility(View.INVISIBLE);
	// pb.setVisibility(View.VISIBLE);
	// header.setPadding(0, 0, 0, 0);
	//
	// if (listener != null) {
	// listener.onRefresh();
	// }
	// }
	//
	// break;
	//
	// default:
	// break;
	// }
	// return super.onTouchEvent(ev);
	// }

	private OnRefreshListener listener;
	private int footerHeight;

	public interface OnRefreshListener {
//		void onRefresh();

		void onLoadingMore();
	}

	public void setOnRefreshListener(OnRefreshListener listener) {
		this.listener = listener;
	}

	// 箭头的旋转
	private void animation(boolean isChangeToReleashRefresh) {
		RotateAnimation ra = new RotateAnimation(isChangeToReleashRefresh ? 0
				: -180, isChangeToReleashRefresh ? -180 : -360,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		ra.setDuration(200);
		ra.setFillAfter(true);
		iv_arrow.startAnimation(ra);
	}

	private boolean isBottom;
	private View footer;
	private int textSize;

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (needRefresh&&scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			// 获取最后一个可见条目的索引如果等于ListView的数据总数-1,则证明到底了
			if (this.getLastVisiblePosition() == this.getCount() - 1) {
				if (!isBottom) {
					isBottom = true;
					// Log.i("test", "到底了");
					footer.setPadding(0, 0, 0, 0);
					this.setSelection(getCount());
					if (listener != null) {
						listener.onLoadingMore();
					}
				}
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// Log.i("test", "firstVisibleItem:" + firstVisibleItem);
		// 第一个可见条目的索引
		this.firstVisibleItem = firstVisibleItem;
	}

	public void finishLoading(boolean isPullToRefresh) {
		if (isPullToRefresh) {
			tv_state.setText("下拉刷新");

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sf.format(System.currentTimeMillis());
			tv_time.setText("最后的刷新时间:" + time);

			header.setPadding(0, -headerHeight, 0, 0);

			pb.setVisibility(View.INVISIBLE);
			iv_arrow.setVisibility(View.VISIBLE);
			state = PullState.PULL_TO_REFRESH;
		} else {
			// 将footer隐藏
			footer.setPadding(0, -footerHeight, 0, 0);
			isBottom = false;
		}
	}
	
	/**
	 * @param needRefresh 是否需要刷新功能
	 */
	public void setNeedRefresh(boolean needRefresh) {
		this.needRefresh = needRefresh;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(getCount()<2){
			canvas.drawText("没有查询到相关信息。", (getWidth()-9*textSize)/2, getHeight()/2, mPaint);
		}
	}

}
