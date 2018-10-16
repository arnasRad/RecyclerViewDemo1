package com.gmail.arnasrad.recyclerviewdemo.detail;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.gmail.arnasrad.recyclerviewdemo.R;

import static com.gmail.arnasrad.recyclerviewdemo.util.BaseActivity.addFragmentToActivity;

/**
 * 18.
 *
 * This Activity simply displays Data from a ListItem in a "Detailed" View.
 * Although I'd still give it it's own Presenter/Controller/Whatever in a real App, I won't bother
 * doing that here since the Activity is so simplistic anyway.
 * Created by R_KAY on 6/3/2017.
 */

public class DetailActivity extends AppCompatActivity {
    private static final String EXTRA_ITEM_ID = "EXTRA_ITEM_ID";
    private static final String DETAIL_FRAG = "DETAIL_FRAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



        Intent i = getIntent();

        //if extra is null, not worth even bothering to set up the MVVM stuff; Kill it with fire.
        if (i.hasExtra(EXTRA_ITEM_ID)){
            String itemId = i.getStringExtra(EXTRA_ITEM_ID);

            FragmentManager manager = getSupportFragmentManager();

            DetailFragment fragment = (DetailFragment) manager.findFragmentByTag(DETAIL_FRAG);

            if (fragment == null) {
                fragment = DetailFragment.newInstance(itemId);
            }

            addFragmentToActivity(manager,
                    fragment,
                    R.id.rootActivityDetail,
                    DETAIL_FRAG
            );

        } else {
            Toast.makeText(this, R.string.errorNoExtraFound, Toast.LENGTH_LONG).show();
        }

    }
}
