package com.gmail.arnasrad.recyclerviewdemo.list;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Fade;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.gmail.arnasrad.recyclerviewdemo.R;
import com.gmail.arnasrad.recyclerviewdemo.data.FakeDataSource;
import com.gmail.arnasrad.recyclerviewdemo.data.ListItem;
import com.gmail.arnasrad.recyclerviewdemo.logic.Controller;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListActivity extends AppCompatActivity implements ViewInterface, View.OnClickListener {

    private static final String EXTRA_DATE_AND_TIME = "EXTRA_DATE_AND_TIME";
    private static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private static final String EXTRA_DRAWABLE = "EXTRA_DRAWABLE";

    private List<ListItem> listOfData;

    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recListActivity);
        layoutInflater = getLayoutInflater();
        Toolbar toolbar = findViewById(R.id.tlbListActivity);

        toolbar.setTitle(R.string.titleToolbar);
        toolbar.setLogo(R.drawable.ic_view_list_white_24dp);
        //toolbar.setTitleMarginStart(72);

        FloatingActionButton fabulous = findViewById(R.id.fabCreateNewItem);

        fabulous.setOnClickListener(this);

        // This is Dependency Injection here
        controller = new Controller(this, new FakeDataSource());
    }

    @Override
    public void startDetailActivity(String dateAndTime, String message, int colorResource, View viewRoot) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra(EXTRA_DATE_AND_TIME, dateAndTime);
        i.putExtra(EXTRA_MESSAGE, message);
        i.putExtra(EXTRA_DRAWABLE, colorResource);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade(Fade.IN));
            getWindow().setEnterTransition(new Fade(Fade.OUT));


            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(
                            this,
                            new Pair<>(viewRoot.findViewById(R.id.imvListItemCircle),
                                    getString(R.string.transitionDrawable)),
                            new Pair<>(viewRoot.findViewById(R.id.lblMessage),
                                    getString(R.string.transitionMessage)),
                            new Pair<>(viewRoot.findViewById(R.id.lblDateAndTime),
                                    getString(R.string.transitionTimeAndDate))
                            );
            startActivity(i, options.toBundle());
        } else {
            startActivity(i);
        }

    }

    @Override
    public void setUpAdapterAndView(List<ListItem> listOfData) {
        this.listOfData = listOfData;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // new GridLayoutManager or StaggeredGridLayoutManager
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CustomAdapter();
        recyclerView.setAdapter(adapter);


        // Check the documentation for more ideas on item decoration
        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                layoutManager.getOrientation()
        );

        itemDecoration.setDrawable(
                Objects.requireNonNull(ContextCompat.getDrawable(
                        ListActivity.this,
                        R.drawable.divider_white
                ))
        );

        recyclerView.addItemDecoration(
                itemDecoration
        );

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void addNewListItemToView(ListItem newItem) {
        listOfData.add(newItem);

        int endOfList = listOfData.size() - 1;

        adapter.notifyItemInserted(endOfList);

        recyclerView.smoothScrollToPosition(endOfList);
    }

    @Override
    public void deleteListItemAt(int position) {
        listOfData.remove(position);

        adapter.notifyItemRemoved(position);
    }

    @Override
    public void showUndoSnackbar() {
        Snackbar.make(
                findViewById(R.id.rootListActivity),
                getString(R.string.actionDeleteItem),
                Snackbar.LENGTH_LONG
        )
        .setAction(R.string.actionUndo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onUndoConfirmed();
            }
        })
        .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);

                controller.onSnackBarTimeout();
            }
        })
        .show();
    }

    @Override
    public void InsertListItemAt(int position, ListItem listItem) {
        listOfData.add(position, listItem);

        adapter.notifyItemInserted(position);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.fabCreateNewItem) {
            // User wishes to create a new RecyclerView Item
            controller.createNewListItem();
        }
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
        /**
         * 13.
         * Inflates a new View (in this case, R.layout.item_data), and then creates/returns a new
         * CustomViewHolder object.
         *
         * @param viewGroup   Unfortunately the docs currently don't explain this at all :(
         * @param i Unfortunately the docs currently don't explain this at all :(
         * @return CustomViewHolder
         */
        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = layoutInflater.inflate(R.layout.item_data, viewGroup, false);

            return new CustomViewHolder(v);
        }


        /**
         * This method "Binds" or assigns Data (from listOfData) to each View (ViewHolder).
         *
         * @param customViewHolder   The current ViewHolder instance for a given position
         * @param i The current position of the ViewHolder we are Binding to, based upon
         *          our (listOfData). So for the second ViewHolder we create, we'll bind data
         *          from the second Item in listOfData.
         */
        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
            ListItem currentItem = listOfData.get(i);

            customViewHolder.coloredCircle.setImageResource(
                    currentItem.getColorResource()
            );

            customViewHolder.message.setText(
                    currentItem.getMessage()
            );

            customViewHolder.dateAndTime.setText(
                    currentItem.getDateAndTime()
            );

            customViewHolder.loading.setVisibility(View.INVISIBLE);
        }


        /**
         * This method let's our Adapter determine how many ViewHolders it needs to create, based on
         * the size of the Dataset (List) which it is working with.
         *
         * @return the size of the dataset, generally via List.size()
         */
        @Override
        public int getItemCount() {
            // Helps the Adapter decide how many Items it will need to manage
            return listOfData.size();
        }


        /**
         * 5.
         * Each ViewHolder contains Bindings to the Views we wish to populate with Data.
         */
        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            /**10. now that we've made our layouts, let's bind them*/
            private CircleImageView coloredCircle;
            private TextView dateAndTime;
            private TextView message;
            private ViewGroup container;
            private ProgressBar loading;

            CustomViewHolder(@NonNull View itemView) {
                super(itemView);

                this.coloredCircle = itemView.findViewById(R.id.imvListItemCircle);
                this.dateAndTime = itemView.findViewById(R.id.lblDateAndTime);
                this.message = itemView.findViewById(R.id.lblMessage);
                this.container = itemView.findViewById(R.id.rootListItem);
                this.loading = itemView.findViewById(R.id.proItemData);
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

                ListItem listItem = listOfData.get(
                        this.getAdapterPosition()
                );
                controller.onListItemClick(
                        listItem,
                        itemView
                );
            }
        }
    }

    private ItemTouchHelper.Callback createHelperCallback() {
        /*First Param is for Up/Down motion, second is for Left/Right.
        Note that we can supply 0, one constant (e.g. ItemTouchHelper.LEFT), or two constants (e.g.
        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) to specify what directions are allowed.
        */

        return new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            //not used, as the first parameter above is 0
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView1,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }


            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                controller.onListItemSwiped(
                        position,
                        listOfData.get(position)
                );
            }
        };
    }
}
