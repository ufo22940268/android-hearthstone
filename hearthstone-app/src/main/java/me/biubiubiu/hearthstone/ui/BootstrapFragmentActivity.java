package me.biubiubiu.hearthstone.ui;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import me.biubiubiu.hearthstone.BootstrapApplication;
import me.biubiubiu.hearthstone.Injector;

import butterknife.Views;

/**
 * Base class for all Bootstrap Activities that need fragments.
 */
public class BootstrapFragmentActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Injector.inject(this);
    }

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);

        Views.inject(this);
    }

}
