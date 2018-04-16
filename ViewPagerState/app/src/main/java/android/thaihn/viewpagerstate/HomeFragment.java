package android.thaihn.viewpagerstate;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private int mPage;
    private String mTitle;
    private TextView mText;

    private EditText mEditText;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Create only one instance of fragment
     *
     * @param page
     * @param title
     * @return
     */
    public static HomeFragment newInstance(int page, String title) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        homeFragment.setArguments(args);
        return homeFragment;
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mText = (TextView) rootView.findViewById(R.id.text);
        mEditText = rootView.findViewById(R.id.edittext_home);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mText.setText(mTitle);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String text = mEditText.getText().toString().trim();
        if (text != null) {
            outState.putString("text", text);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            String text_old = savedInstanceState.getString("text");
            mEditText.setText(text_old);
        }
    }
}
