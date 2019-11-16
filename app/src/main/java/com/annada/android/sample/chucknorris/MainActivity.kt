package com.annada.android.sample.chucknorris

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.annada.android.sample.chucknorris.ui.main.QuoteFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, QuoteFragment.newInstance())
                .commitNow()
        }
    }

}
