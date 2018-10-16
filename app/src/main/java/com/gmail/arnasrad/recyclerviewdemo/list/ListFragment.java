package com.gmail.arnasrad.recyclerviewdemo.list;


import android.app.Activity;
import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Fade;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gmail.arnasrad.recyclerviewdemo.R;
import com.gmail.arnasrad.recyclerviewdemo.RoomDemoApplication;
import com.gmail.arnasrad.recyclerviewdemo.create.CreateActivity;
import com.gmail.arnasrad.recyclerviewdemo.data.ListItem;
import com.gmail.arnasrad.recyclerviewdemo.detail.DetailActivity;
import com.gmail.arnasrad.recyclerviewdemo.viewmodel.ListItemCollectionViewModel;

import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
    private static final String EXTRA_ITEM_ID = "EXTRA_ITEM_ID";

    private List<ListItem> listOfData;

    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private Toolbar toolbar;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ListItemCollectionViewModel listItemCollectionViewModel;

    public ListFragment() {
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    /*------------------------------- Lifecycle -------------------------------*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((RoomDemoApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Set up and subscribe (observe) to the ViewModel
        listItemCollectionViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ListItemCollectionViewModel.class);

        listItemCollectionViewModel.getListItems().observe(this, new Observer<List<ListItem>>() {
            @Override
            public void onChanged(@Nullable List<ListItem> listItems) {
                if (listOfData == null) {
                    setListData(listItems);
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recListActivity);
        layoutInflater = getActivity().getLayoutInflater();
        toolbar = (Toolbar) v.findViewById(R.id.tlbListActivity);

        toolbar.setTitle(R.string.titleToolbar);
        toolbar.setLogo(R.drawable.ic_view_list_white_24dp);
        toolbar.setTitleMarginStart(72);

        FloatingActionButton fabulous = (FloatingActionButton) v.findViewById(R.id.fabCreateNewItem);

        fabulous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateActivity();
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void startDetailActivity(String itemId, View viewRoot) {
        Activity container = getActivity();
        Intent i = new Intent(container, DetailActivity.class);
        i.putExtra(EXTRA_ITEM_ID, itemId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            container.getWindow().setEnterTransition(new Fade(Fade.IN));
            container.getWindow().setEnterTransition(new Fade(Fade.OUT));

            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(container,
                            new Pair<>(viewRoot.findViewById(R.id.imvListItemCircle),
                                    getString(R.string.transitionDrawable)),
                            new Pair<>(viewRoot.findViewById(R.id.lblMessage),
                                    getString(R.string.transitionMessage)),
                            new Pair<>(viewRoot.findViewById(R.id.lblDateAndTime),
                                    getString(R.string.transitionTimeAndDate)));

            startActivity(i, options.toBundle());

        } else {
            startActivity(i);
        }
    }

    public void startCreateActivity() {
        startActivity(new Intent(getActivity(), CreateActivity.class));
    }


    public void setListData(List<ListItem> listOfData) {
        this.listOfData = listOfData;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CustomAdapter();
        recyclerView.setAdapter(adapter);


        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                layoutManager.getOrientation()
        );

        itemDecoration.setDrawable(
                ContextCompat.getDrawable(
                        getActivity(),
                        R.drawable.divider_white
                )
        );

        recyclerView.addItemDecoration(
                itemDecoration
        );


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    /*-------------------- RecyclerView Boilerplate ----------------------*/

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {//6

        @Override
        public CustomAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = layoutInflater.inflate(R.layout.item_data, parent, false);
            return new CustomViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CustomAdapter.CustomViewHolder holder, int position) {
            //11. and now the ViewHolder data
            ListItem currentItem = listOfData.get(position);

            holder.coloredCircle.setImageResource(currentItem.getColorResource());


            holder.message.setText(
                    currentItem.getMessage()
            );

            holder.dateAndTime.setText(
                    currentItem.getItemId()
            );

            holder.loading.setVisibility(View.INVISIBLE);
        }

        @Override
        public int getItemCount() {
            // 12. Returning 0 here will tell our Adapter not to make any Items. Let's fix that.
            return listOfData.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            //10. now that we've made our layouts, let's bind them
            private CircleImageView coloredCircle;
            private TextView dateAndTime;
            private TextView message;
            private ViewGroup container;
            private ProgressBar loading;

            public CustomViewHolder(View itemView) {
                super(itemView);
                this.coloredCircle = (CircleImageView) itemView.findViewById(R.id.imvListItemCircle);
                this.dateAndTime = (TextView) itemView.findViewById(R.id.lblDateAndTime);
                this.message = (TextView) itemView.findViewById(R.id.lblMessage);
                this.loading = (ProgressBar) itemView.findViewById(R.id.proItemData);

                this.container = (ViewGroup) itemView.findViewById(R.id.rootListItem);
                /*
                We can pass "this" as an Argument, because "this", which refers to the Current
                Instance of type CustomViewHolder currently conforms to (implements) the
                View.OnClickListener interface. I have a Video on my channel which goes into
                Interfaces with Detailed Examples.

                Search "Android WTF: Java Interfaces by Example"
                 */
                this.container.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                //getAdapterPosition() get's an Integer based on which the position of the current
                //ViewHolder (this) in the Adapter. This is how we get the correct Data.
                ListItem listItem = listOfData.get(
                        this.getAdapterPosition()
                );

                startDetailActivity(listItem.getItemId(), v);

            }
        }

    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            //not used, as the first parameter above is 0
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                listItemCollectionViewModel.deleteListItem(
                        listOfData.get(position)
                );

                //ensure View is consistent with underlying data
                listOfData.remove(position);
                adapter.notifyItemRemoved(position);


            }
        };
        return simpleItemTouchCallback;
    }
}
