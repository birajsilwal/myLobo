package com.example.mylobo.Marketplace;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mylobo.HomeScreen;
import com.example.mylobo.MenuActivity.MenuActivityMarketplace;
import com.example.mylobo.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class Marketplace extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView rvPostsMarkerplace;
    private PostsAdapterMarketplace adapterMarketplace;
    private List<PostMarketplace> mPostsMarketplace;

    ImageView ivMenuMp, ivBackMp;

    public static final String TAG = "Marketplace";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace);
        ivMenuMp = findViewById(R.id.ivMenuMp);
        ivBackMp = findViewById(R.id.ivBackMp);

        ivMenuMp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Marketplace.this, MenuActivityMarketplace.class);
                startActivity(i);
            }
        });

        ivBackMp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Marketplace.this, HomeScreen.class);
                startActivity(i);
//                finish();
            }
        });

        rvPostsMarkerplace = findViewById(R.id.rvPostsMp);

        // 2. create the data source. This will be a list of different post objects. gets from PostMarketplace
        mPostsMarketplace = new ArrayList<>();
        // 3. now create the adapter
        adapterMarketplace = new PostsAdapterMarketplace(this, mPostsMarketplace);
        // 4. set the adapter on the recycler view. This adapter tells the recycler view how to show the content onto the screen
        rvPostsMarkerplace.setAdapter(adapterMarketplace);


        // set the layout manager on the recycler view
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        // 5. set the layout manager on the recycler view
        rvPostsMarkerplace.setLayoutManager(gridLayoutManager);
//        rvPostsMarkerplace.addItemDecoration(new GridSpacing(spanCount, spacing, includeEdge));

        // 1. created this after adding recycler view in the layout
        // TODO:look at the linerlayout manager here
//        rvPostsMarkerplace.setLayoutManager(new LinearLayoutManager(this));


        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryPostMarketplace();

            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        queryPostMarketplace();
    }

    private void queryPostMarketplace() {
        // querying objects
        ParseQuery<PostMarketplace> postMarketplaceQuery = new ParseQuery<PostMarketplace>(PostMarketplace.class);
        postMarketplaceQuery.include(PostMarketplace.KEY_USER);
        //setting limit of 20 posts
        postMarketplaceQuery.setLimit(20);
        postMarketplaceQuery.addDescendingOrder(PostMarketplace.KEY_CREATED_AT);
        postMarketplaceQuery.findInBackground(new FindCallback<PostMarketplace>() {
            @Override
            public void done(List<PostMarketplace> postsMarketplace, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                mPostsMarketplace.addAll(postsMarketplace);
                adapterMarketplace.notifyDataSetChanged();
                // Remember to CLEAR OUT old items before appending in the new ones
                adapterMarketplace.clear();
                // ...the data has come back, add new items to your adapter...
                adapterMarketplace.addAll(postsMarketplace);
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
//                for (int i = 0; i < postsMarketplace.size(); i++ ){
//                    PostMarketplace postMarketplace = postsMarketplace.get(i);
//                    Log.d(TAG, "PostMarketplace: " + postMarketplace.getTitle() + ", username: " + postMarketplace.getUser().getUsername());
//                }
            }
        });
    }
}
