
package me.biubiubiu.hearthstone;

import android.accounts.AccountsException;
import android.app.Activity;

import me.biubiubiu.hearthstone.authenticator.ApiKeyProvider;
import me.biubiubiu.hearthstone.core.BootstrapService;
import me.biubiubiu.hearthstone.core.UserAgentProvider;
import javax.inject.Inject;

import java.io.IOException;

/**
 * Provider for a {@link me.biubiubiu.hearthstone.core.BootstrapService} instance
 */
public class BootstrapServiceProvider {

    @Inject ApiKeyProvider keyProvider;
    @Inject UserAgentProvider userAgentProvider;

    /**
     * Get service for configured key provider
     * <p>
     * This method gets an auth key and so it blocks and shouldn't be called on the main thread.
     *
     * @return bootstrap service
     * @throws IOException
     * @throws AccountsException
     */
    public BootstrapService getService(Activity activity) throws IOException, AccountsException {
        // return new BootstrapService(keyProvider.getAuthKey(activity), userAgentProvider);
        return new BootstrapService("adsf", "asdf");
    }
}
