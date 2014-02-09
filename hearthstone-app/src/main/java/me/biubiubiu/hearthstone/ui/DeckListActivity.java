package me.biubiubiu.hearthstone.ui;

import me.biubiubiu.hearthstone.BootstrapApplication;
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
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
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
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.model.ImageTagFactory;


/**
 * Activity to view the carousel and view pager indicator with fragments.
 */
public class DeckListActivity extends BootstrapFragmentActivity {

    @Inject protected BootstrapServiceProvider serviceProvider;
    FragmentPagerAdapter mAdapter;
    private DeckListFragment mDeckFragment;
    private SecretListFragment mSecretFragment;
    private String mHero = "术士";
    private ImageManager imageManager;
    protected ImageTagFactory imageTagFactory;

    private String[] TITLES = {
        "套牌推荐",
        "奥秘牌",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_hero);
        imageManager = BootstrapApplication.getImageLoader();
        imageTagFactory = ImageTagFactory.newInstance(this, R.drawable.card_loading);

        Views.inject(this);
        Intent intent = getIntent();
        if (intent.hasExtra("hero")) {
            mHero = intent.getStringExtra("hero");
        }
        getActionBar().setTitle(mHero);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.content_indicator);
        ViewPager pager = (ViewPager)findViewById(R.id.content_pager);

        if (mAdapter == null) {
            mAdapter = new ContentAdapter(getSupportFragmentManager());
        }
        pager.setAdapter(mAdapter);
        indicator.setViewPager(pager);

        mDeckFragment = new DeckListFragment();
        mSecretFragment = new SecretListFragment();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    //Content fragment. Used to display ticket, summary and nearby restarant.
    private class ContentAdapter extends FragmentPagerAdapter {

        public ContentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return mDeckFragment;
            } else {
                return mSecretFragment;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position % TITLES.length];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }

}
