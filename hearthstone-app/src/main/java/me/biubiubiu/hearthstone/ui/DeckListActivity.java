package me.biubiubiu.hearthstone.ui;

import android.util.*;
import android.widget.*;
import android.view.*;
import android.content.*;
import android.app.*;
import android.os.*;
import android.text.*;
import android.database.*;
import android.net.*;
import android.opengl.*;
import android.graphics.*;
import android.view.animation.*;
import android.text.TextUtils;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.accounts.OperationCanceledException;

import java.util.*;
import java.io.*;
import com.google.gson.Gson;

import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import me.biubiubiu.hearthstone.BootstrapServiceProvider;
import me.biubiubiu.hearthstone.R;
import me.biubiubiu.hearthstone.core.BootstrapService;
import me.biubiubiu.hearthstone.core.BootstrapService.DeckWrapper;
import me.biubiubiu.hearthstone.core.*;
import me.biubiubiu.hearthstone.core.HttpHandler.ResponseHandler;
import me.biubiubiu.hearthstone.util.SafeAsyncTask;
import com.viewpagerindicator.TitlePageIndicator;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;
import net.simonvt.menudrawer.MenuDrawer;


/**
 * Activity to view the carousel and view pager indicator with fragments.
 */
public class DeckListActivity extends BootstrapFragmentActivity
    implements AdapterView.OnItemClickListener {

    @InjectView(R.id.list) ListView mListView;
    @Inject protected BootstrapServiceProvider serviceProvider;

    private HttpHandler mHttpHandler;
    private ArrayAdapter<Deck> mAdapter;
    private Deck[] mWrapper;
    private String mHero = "术士";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deck_list);
        Views.inject(this);
        mHttpHandler = new HttpHandler(this);
        mAdapter = new ArrayAdapter<Deck>(this, android.R.layout.simple_list_item_1, new ArrayList<Deck>());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        Intent intent = getIntent();
        if (intent.hasExtra("hero")) {
            mHero = intent.getStringExtra("hero");
        }
        getActionBar().setTitle(mHero + "推荐套牌");
        loadData();
    }

    private void loadData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", mHero);
        mHttpHandler.get("hero_deck", map, new ResponseHandler() {
                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    mWrapper = gson.fromJson(result, Deck[].class);
                    mAdapter.addAll(mWrapper[0], mWrapper[1]);
                    mAdapter.notifyDataSetChanged();
                }
            });
    }

    public void onItemClick(AdapterView parent, View view, int position, long id) {
        CardListActivity.mDeck = mWrapper[position];
        Intent intent = new Intent(this, CardListActivity.class);
        startActivity(intent);
    }
}
