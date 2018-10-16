package com.gmail.arnasrad.recyclerviewdemo.list;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.gmail.arnasrad.recyclerviewdemo.R;
import com.gmail.arnasrad.recyclerviewdemo.util.BaseActivity;

/**
 *
 * Please Read: It's never my intention to mislead anyone. If something in this Code is sub-optimal
 * or downright wrong, please consider creating an issue on GitHub. I greatly appreciate well informed
 * critiques, and I'm happy to give shout outs for good explanations. Putting my Code OS is partly
 * an attempt to get peer review.
 *
 * One doesn't sharpen a knife by rubbing the blade against butter.
 *
 */
public class ListActivity extends BaseActivity {
    private static final String LIST_FRAG = "LIST_FRAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FragmentManager manager = getSupportFragmentManager();

        ListFragment fragment = (ListFragment) manager.findFragmentByTag(LIST_FRAG);

        if (fragment == null) {
            fragment = ListFragment.newInstance();
        }

        addFragmentToActivity(manager,
                fragment,
                R.id.rootActivityList,
                LIST_FRAG
        );
    }
}
