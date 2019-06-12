package group14.finalproject.mytodotask

import android.app.Application
import group14.finalproject.mytodotask.sharedpreferences.SharedPreferencesHelper

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesHelper.init(applicationContext)
    }
}