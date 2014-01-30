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
import android.support.v4.app.Fragment;
import android.accounts.OperationCanceledException;
import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.model.ImageTagFactory;

import java.util.*;
import java.io.*;
import com.google.gson.Gson;

import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.novoda.imageloader.core.ImageManager;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import me.biubiubiu.hearthstone.BootstrapServiceProvider;
import me.biubiubiu.hearthstone.R;
import me.biubiubiu.hearthstone.core.BootstrapService;
import me.biubiubiu.hearthstone.core.Deck;
import me.biubiubiu.hearthstone.core.Deck.Card;
import me.biubiubiu.hearthstone.core.BootstrapService.DeckWrapper;
import me.biubiubiu.hearthstone.core.*;
import me.biubiubiu.hearthstone.core.HttpHandler.ResponseHandler;
import me.biubiubiu.hearthstone.util.SafeAsyncTask;
import com.viewpagerindicator.TitlePageIndicator;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;
import net.simonvt.menudrawer.MenuDrawer;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

public class CardListActivity extends BootstrapFragmentActivity {

    @InjectView(R.id.list) ListView mListView;
    @Inject protected BootstrapServiceProvider serviceProvider;

    public static Deck mDeck;
    private ImageManager imageManager;
    protected ImageTagFactory imageTagFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card_list);
        imageManager = BootstrapApplication.getImageLoader();
        imageTagFactory = ImageTagFactory.newInstance(this, R.drawable.card_loading);

        Views.inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (mDeck == null) {
            throw new RuntimeException("deck must be set before start activity.");
        }

        getActionBar().setTitle(mDeck.desc);

        FancyCoverFlow fancyCoverFlow = (FancyCoverFlow) findViewById(R.id.fancyCoverFlow);
        fancyCoverFlow.setReflectionEnabled(true);
        fancyCoverFlow.setReflectionRatio(0.3f);
        fancyCoverFlow.setReflectionGap(0);

        fancyCoverFlow.setAdapter(new ViewGroupExampleAdapter());

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

    public  void loadPhoto(ImageView view, String url) {
        view.setTag(imageTagFactory.build(url, this));
        imageManager.getLoader().load(view);
    }

    private class ViewGroupExampleAdapter extends FancyCoverFlowAdapter {

        // =============================================================================
        // Private members
        // =============================================================================

        private Card[] cards = mDeck.cards;

        // =============================================================================
        // Supertype overrides
        // =============================================================================

        @Override
        public int getCount() {
            return cards.length;
        }

        @Override
        public Card getItem(int i) {
            return cards[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
            View galleryItem = null;

            if (reuseableView != null) {
                galleryItem = (View) reuseableView;
            } else {
                galleryItem = LayoutInflater.from(CardListActivity.this).inflate(R.layout.gallery_item, viewGroup, false);
                galleryItem.setLayoutParams(new FancyCoverFlow.LayoutParams(300, 600));
            }
            Card card = getItem(i);

            ImageView iv = (ImageView)galleryItem.findViewById(R.id.image);
            loadPhoto(iv, card.pic);

            TextView tv = (TextView)galleryItem.findViewById(R.id.desc);
            tv.setText(card.name + " x" + card.count);

            return galleryItem;
        }
    }
}
