package mvp.implement.kotlin

import android.app.Application
import android.content.Context
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import com.crashlytics.android.Crashlytics
import mvp.implement.kotlin.common.Prefs
import io.fabric.sdk.android.Fabric
import io.realm.Realm
import io.realm.RealmConfiguration


class AppApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: AppApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        //Fabric.with(this, Crashlytics())
        configuraFontePadrao()
        dbConfig()
    }

    private fun configuraFontePadrao() {
        try {
            CalligraphyConfig.initDefault(CalligraphyConfig.Builder().setDefaultFontPath(
                resources.getString(R.string.roboto)).setFontAttrId(R.attr.fontPath).build())
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    fun dbConfig(){
        Realm.init(applicationContext)
        var c = RealmConfiguration.Builder()
        c.name("fastfeedback")
        c.deleteRealmIfMigrationNeeded()
        Realm.setDefaultConfiguration(c.build())
    }


}
