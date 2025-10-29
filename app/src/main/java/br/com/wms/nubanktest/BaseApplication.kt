package br.com.wms.nubanktest

import android.app.Application
import br.com.wms.linkshortener.di.loadLinkShortenerModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            androidLogger()
            loadLinkShortenerModules()
        }
    }
}