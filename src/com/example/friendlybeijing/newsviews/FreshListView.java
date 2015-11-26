package com.example.friendlybeijing.newsviews;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.friendlybeijing.R;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FreshListView extends ListView implements android.widget.AdapterView.OnItemClickListener
{

	private LinearLayout firstView;
	private ImageView iv;
	private TextView state;
	private ProgressBar pb;
	private TextView time;
	private int measuredHeight;
	private static final int STATE_PULL_REFRESH = 0; //下拉状态
	private static final int STATE_RELEASE_REFRESH = 1 ;//松开刷新
	private static final int STATE_REFRESHING = 2;  //正在刷新
	private int currentState = STATE_PULL_REFRESH;
	private RotateAnimation animUp;
	private RotateAnimation animDown;
	private int startY;
	public FreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		initView();
	}

	public FreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	public FreshListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	/**
	 * 初始化头view
	 */
	private void initView() {
		firstView = (LinearLayout) LinearLayout.inflate(getContext(),R.layout.firstlistviewitem, null);
		this.addHeaderView(firstView);
		iv = (ImageView) firstView.findViewById(R.id.iv_down);
		pb = (ProgressBar) firstView.findViewById(R.id.pb);
		state = (TextView) firstView.findViewById(R.id.tv_state);
		time = (TextView) firstView.findViewById(R.id.tv_time);
		firstView.measure(0, 0);
		initAnimation();
		measuredHeight = firstView.getMeasuredHeight();
		firstView.setPadding(0, -measuredHeight, 0, 0);
		time.setText("最后刷新时间"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));  //怎么能忘了 new Date()拿到 date呢？
	}
	private void initAnimation() {
		// TODO Auto-generated method stub
		animUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animUp.setDuration(500);
		animUp.setFillAfter(true);
		
		animDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animDown.setDuration(500);
		animDown.setFillAfter(true);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_CANCEL:
			startY = (int)ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			if(startY == -1)
				startY = (int)ev.getRawY();
			if(currentState==STATE_REFRESHING)  //正在刷新不做处理
				break;
			int endY = (int) ev.getRawY();
			int dy = endY  - startY ; // 设置偏移量
			if(dy >0 && getFirstVisiblePosition()==0)  //只有是第一个并且向下拉才可以拉出
			{	
				int padding = dy - measuredHeight;
				firstView.setPadding(0, padding, 0, 0);
				if(padding >0 &&currentState!=STATE_RELEASE_REFRESH)  //必须要拉出去才能变为松开刷新
				{
					currentState = STATE_RELEASE_REFRESH;
					refreshState();
				}
				else if (padding<=0 && currentState!=STATE_PULL_REFRESH) {
					currentState = STATE_PULL_REFRESH;
					refreshState();
				}
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			startY = -1;
			if(currentState==STATE_RELEASE_REFRESH)  //由松开刷新，转为正在刷新应该是Touch抬起的时候触发，非常正确
			{
				currentState = STATE_REFRESHING;
				firstView.setPadding(0, 0, 0, 0);  //显示
				refreshState();
			}
			else if(currentState==STATE_PULL_REFRESH){
				firstView.setPadding(0, -measuredHeight, 0, 0);
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	private void refreshState() {
		// TODO Auto-generated method stub
		switch (currentState) {
		case STATE_PULL_REFRESH:
			state.setText("下拉刷新");
			iv.setVisibility(View.VISIBLE);
			pb.setVisibility(View.INVISIBLE);
			iv.startAnimation(animDown);
			break;
		case STATE_RELEASE_REFRESH:
			state.setText("松开刷新");
			iv.setVisibility(View.VISIBLE);
			pb.setVisibility(View.INVISIBLE);
			iv.startAnimation(animUp);
			break;
		case STATE_REFRESHING:
			state.setText("正在刷新");
			iv.clearAnimation();
			iv.setVisibility(View.INVISIBLE);
			pb.setVisibility(View.VISIBLE);
			if(freshListener!=null)
				freshListener.onFresh();
			break;
		default:
			break;
		}
	}
	public void setOnFreshListener(OnFreshListener freshListener)
	{
		this.freshListener =  freshListener;
	}
	private OnFreshListener freshListener = null;
	public interface OnFreshListener
	{
		public void onFresh();
		public void onLoadMore();
	}
	/**
	 * 如何重写接口是我的难点，需要花时间去理解
	 */
	OnItemClickListener mItemClickListener;
	
	@Override
	public void setOnItemClickListener(
			android.widget.AdapterView.OnItemClickListener listener) {
		// TODO Auto-generated method stub
		super.setOnItemClickListener(listener);
		mItemClickListener = listener;
	}
	/**
	 * 重写itmeClick
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if(mItemClickListener!=null)
		{
			mItemClickListener.onItemClick(parent, view, position-getHeaderViewsCount(), id);
		}
	};
}
