

package me.biubiubiu.hearthstone;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.FROYO;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import com.github.kevinsawicki.http.HttpRequest;
import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.LoaderSettings.SettingsBuilder;
import com.novoda.imageloader.core.cache.LruBitmapCache;


import dagger.ObjectGraph;
import android.util.LruCache;
import android.graphics.Bitmap;

/**
 * hearthstone application
 */
public class BootstrapApplication extends Application {

    private static BootstrapApplication instance;
    private static ImageManager mImageManager;
    private static LruCache mCache;
    
    /**
     * Create main application
     */
    public BootstrapApplication() {

        // Disable http.keepAlive on Froyo and below
        if (SDK_INT <= FROYO)
            HttpRequest.keepAlive(false);
    }

    public static ImageManager getImageLoader() {
        return mImageManager;
    }


    /**
     * Create main application
     *
     * @param context
     */
    public BootstrapApplication(final Context context) {
        this();
        attachBaseContext(context);

    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        // Perform injection
        Injector.init(getRootModule(), this);

        mImageManager = new ImageManager(this, new SettingsBuilder()
                                         .withCacheManager(new LruBitmapCache(this))
                                         .build(this));

        int cacheSize = 4 * 1024 * 1024; // 4MiB
        mCache = new LruCache(cacheSize) {
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount();
                }
            };

    }

    private Object getRootModule() {
        return new RootModule();
    }


    /**
     * Create main application
     *
     * @param instrumentation
     */
    public BootstrapApplication(final Instrumentation instrumentation) {
        this();
        attachBaseContext(instrumentation.getTargetContext());
    }

    public static BootstrapApplication getInstance() {
        return instance;
    }
}
