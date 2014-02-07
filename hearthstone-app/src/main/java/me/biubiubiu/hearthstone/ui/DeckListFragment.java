package me.biubiubiu.hearthstone.ui;

import me.biubiubiu.hearthstone.R;

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

import java.util.*;
import java.io.*;
import com.google.gson.Gson;
import me.biubiubiu.hearthstone.core.*;
import me.biubiubiu.hearthstone.core.HttpHandler.ResponseHandler;
import android.support.v4.app.Fragment;

public class DeckListFragment extends Fragment implements AdapterView.OnItemClickListener {
    
    private ListView mListView;

    private HttpHandler mHttpHandler;
    private ArrayAdapter<Deck> mAdapter;
    private Deck[] mWrapper;
    private String mHero = "术士";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_deck_list, container, false);
        mListView = (ListView)view.findViewById(R.id.list);
        
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra("hero")) {
            mHero = intent.getStringExtra("hero");
        }

        mHttpHandler = new HttpHandler(getActivity());

        mAdapter = new ArrayAdapter<Deck>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<Deck>());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        loadData();
        return view;
    }

    
    private void loadData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", mHero);
        getActivity().setProgressBarIndeterminateVisibility(true);
        mHttpHandler.get("hero_deck", map, new ResponseHandler() {
                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    mWrapper = gson.fromJson(result, Deck[].class);
                    mAdapter.addAll(mWrapper);
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
        CardListActivity.mDeck = mWrapper[position];
        Intent intent = new Intent(getActivity(), CardListActivity.class);
        startActivity(intent);
    }

}
