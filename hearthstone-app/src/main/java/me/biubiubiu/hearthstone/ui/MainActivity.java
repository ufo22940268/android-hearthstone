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

import java.util.*;

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
import me.biubiubiu.hearthstone.util.SafeAsyncTask;
import com.viewpagerindicator.TitlePageIndicator;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;
import net.simonvt.menudrawer.MenuDrawer;


/**
 * Activity to view the carousel and view pager indicator with fragments.
 */
public class MainActivity extends BootstrapFragmentActivity {

    @InjectView(R.id.content) GridView mGridView;
    private MenuAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Views.inject(this);
        
        mAdapter = new MenuAdapter();
        mGridView.setAdapter(mAdapter);
        
        //Dynamic column count
        float scalefactor = getResources().getDisplayMetrics().density * 142;
        int number = getWindowManager().getDefaultDisplay().getWidth();
        int columns = (int) ((float) number / (float) scalefactor);
        mGridView.setNumColumns(columns);
        mGridView.setOnItemClickListener(mAdapter);
    }
    
    public class MenuAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

        public final int[] MENU_RES = {
            R.drawable.btn_class_druid, 
            R.drawable.btn_class_hunter, 
            R.drawable.btn_class_mage, 
            R.drawable.btn_class_paladin, 
            R.drawable.btn_class_priest, 
            R.drawable.btn_class_rogue, 
            R.drawable.btn_class_shaman, 
            R.drawable.btn_class_warlock, 
            R.drawable.btn_class_warrior, 
        };

        public final String[] MENU_STRS = {
            "德鲁伊",
            "猎人",
            "法师",
            "圣骑士",
            "牧师",
            "盗贼",
            "萨满",
            "术士",
            "战士",
        };

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this, DeckListActivity.class);
            intent.putExtra("hero", MENU_STRS[position]);
            startActivity(intent);
        }
        
        public int getCount() {
            return MENU_RES.length;
        }
        
        public Object getItem(int position) {
            return null;
        }
        
        public long getItemId(int position) {
            return position;
        }
        
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.grid_menu_item, parent, false);
            }

            ((ImageView)view).setImageResource(MENU_RES[position%MENU_RES.length]);

            return view;
        }
    }
}
