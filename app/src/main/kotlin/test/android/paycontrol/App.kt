package test.android.paycontrol

import android.app.Application
import tech.paycon.sdk.v5.PCSDK

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()
        PCSDK.setLogLevel(PCSDK.PC_LOG_DEBUG)
        PCSDK.init(this)
    }
}
