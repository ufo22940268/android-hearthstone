package me.biubiubiu.hearthstone.ui;

import me.biubiubiu.hearthstone.R;
import android.content.DialogInterface;
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
import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.model.ImageTagFactory;

import java.util.*;
import java.io.*;
import com.google.gson.Gson;
import me.biubiubiu.hearthstone.core.*;
import me.biubiubiu.hearthstone.core.HttpHandler.ResponseHandler;
import android.support.v4.app.Fragment;
import com.kanak.emptylayout.EmptyLayout;

public class SecretListFragment extends Fragment implements AdapterView.OnItemClickListener {
    
    private ListView mListView;

    private HttpHandler mHttpHandler;
    private SecretAdapter mAdapter;
    private Secret[] mWrapper;
    private String mHero = "术士";

    private ImageManager imageManager;
    protected ImageTagFactory imageTagFactory;
    private EmptyLayout mEmptyLayout;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageManager = BootstrapApplication.getImageLoader();
        imageTagFactory = ImageTagFactory.newInstance(getActivity(), R.drawable.card_loading);
    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_deck_list, container, false);
        mListView = (ListView)view.findViewById(R.id.list);
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra("hero")) {
            mHero = intent.getStringExtra("hero");
        }

        mHttpHandler = new HttpHandler(getActivity());

        mAdapter = new SecretAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        loadData();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View emptyView = LayoutInflater.from(getActivity()).
            inflate(R.layout.no_secret, null, false);
        ((ViewGroup)(mListView.getParent())).addView(emptyView);
        mListView.setEmptyView(emptyView);
    }


    public void loadPhoto(ImageView view, String url) {
        view.setTag(imageTagFactory.build(url, getActivity()));
        imageManager.getLoader().load(view);
    }
    
    private void showPic(String name, String url) {
        ImageView image = new ImageView(getActivity());
        image.setImageResource(R.drawable.card_loading);
        image.setPadding(20, 20, 20, 20);
        loadPhoto(image, url);
        new AlertDialog.Builder(getActivity())
            .setTitle(name)
            .setView(image)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked OK so do some stuff */
                    }
                })
            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked Cancel so do some stuff */
                    }
                })
            .create()
            .show();
    }
    
    private void loadData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", mHero);
        getActivity().setProgressBarIndeterminateVisibility(true);
        mHttpHandler.get("hero_secret", map, new ResponseHandler() {
                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    mWrapper = gson.fromJson(result, Secret[].class);
                    mAdapter.notifyDataSetChanged();
                    getActivity().setProgressBarIndeterminateVisibility(false);
                }

                @Override
                public void onFinish() {
                    getActivity().setProgressBarIndeterminateVisibility(false);
                }

            });
    }

    public void onItemClick(AdapterView parent, View view, int position, long id) {
        Secret secret = mWrapper[position];
        showPic(secret.name, secret.picurl);
    }


    public class SecretAdapter extends BaseAdapter {
                
        public int getCount() {
            return mWrapper == null ? 0 : mWrapper.length;
        }
        
        public Object getItem(int position) {
            return mWrapper[position];
        }
        
        public long getItemId(int position) {
            return position;
        }
        
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_secret, parent, false);
            }

            Secret secret = (Secret)getItem(position);
            ((TextView)view.findViewById(android.R.id.text1)).setText(String.valueOf(secret.name));
            ((TextView)view.findViewById(R.id.mana)).setText(String.valueOf(secret.mana));
            return view;
        }
    }
}
