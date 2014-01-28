package me.biubiubiu.hearthstone;

import android.accounts.AccountManager;
import android.content.Context;

import me.biubiubiu.hearthstone.authenticator.BootstrapAuthenticatorActivity;
import me.biubiubiu.hearthstone.authenticator.LogoutService;
import me.biubiubiu.hearthstone.core.CheckIn;
import me.biubiubiu.hearthstone.core.TimerService;
import me.biubiubiu.hearthstone.ui.*;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module
(
        complete = false,

        injects = {
                BootstrapApplication.class,
                BootstrapAuthenticatorActivity.class,
                CarouselActivity.class,
                BootstrapTimerActivity.class,
                CheckInsListFragment.class,
                NewsActivity.class,
                NewsListFragment.class,
                UserActivity.class,
                UserListFragment.class,
                TimerService.class,
                MainActivity.class
        }

)
public class BootstrapModule  {

    @Singleton
    @Provides
    Bus provideOttoBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    LogoutService provideLogoutService(final Context context, final AccountManager accountManager) {
        return new LogoutService(context, accountManager);
    }

}
