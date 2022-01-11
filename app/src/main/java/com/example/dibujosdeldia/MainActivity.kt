package com.example.dibujosdeldia

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dibujosdeldia.ui.main.MainFragment
import com.example.dibujosdeldia.ui.main.ThemeHolder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeHolder.myTheme = getSharedPreferences(ThemeHolder.THEME_KEY, Context.MODE_PRIVATE).getInt(ThemeHolder.THEME_KEY, 0)
        if (ThemeHolder.myTheme == ThemeHolder.THEME_DEFAULT){
            setTheme(R.style.Theme_DibujosDelDia)
        }
        if (ThemeHolder.myTheme == ThemeHolder.THEME_BLACK_AND_WHITE){
            setTheme(R.style.Theme_DibujosDelDiaBlackAndWhite)
        }
        if (ThemeHolder.myTheme == ThemeHolder.THEME_BRIGHT){
            setTheme(R.style.Theme_DibujosDelDiaBright)
        }
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}