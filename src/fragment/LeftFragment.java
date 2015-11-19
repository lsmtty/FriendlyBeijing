package fragment;

import com.example.friendlybeijing.R;

import android.view.View;

public class LeftFragment extends MyBaseFragment {

	@Override
	public View initViews() {
		// TODO Auto-generated method stub
		View v = mactivity.getLayoutInflater().inflate(R.layout.left_menu_fragment, null);
		return v;
	}

}
