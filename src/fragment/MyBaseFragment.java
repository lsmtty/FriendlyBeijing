package fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class MyBaseFragment extends Fragment {
	public Activity mactivity;  //每个继承的类都会有activity
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mactivity = getActivity();
		super.onCreate(savedInstanceState);
	}
	//生成布局
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		return initViews();
	}
	//当activity事务处理完毕，最好从这里进行数据的处理和加载
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initData();
		super.onActivityCreated(savedInstanceState);
	}
	/**
	 * 不需要一定被继承的创建数据的函数
	 */
	private void initData() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 必须被继承的创建View的函数
	 * @return
	 */
	public abstract View initViews();
	
}
