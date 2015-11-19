package fragment;

import com.example.friendlybeijing.R;

import android.view.View;

public class MainFragment extends MyBaseFragment {

	@Override
	public View initViews() {
		// TODO Auto-generated method stub
		View v = mactivity.getLayoutInflater().inflate(R.layout.main_menu_fragment, null);
		return v;
	}

}
