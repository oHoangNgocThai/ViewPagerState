package android.thaihn.viewpagerstate.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.thaihn.viewpagerstate.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    private int mPage;
    private String mTitle;
    private TextView mText;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Create only one instance of fragment
     *
     * @param page
     * @param title
     * @return
     */
    public static SettingFragment newInstance(int page, String title) {
        SettingFragment settingFragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        settingFragment.setArguments(args);
        return settingFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt("page", 0);
        mTitle = getArguments().getString("title");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        mText = (TextView) rootView.findViewById(R.id.text);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mText.setText(mTitle);
    }
}
