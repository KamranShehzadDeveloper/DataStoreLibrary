package com.example.datastorelibrary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.datastorehelperlib.DataStore
import com.example.datastorehelperlib.DataStore.Companion.getInstance
import com.example.datastorelibrary.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDatastore()
        initClickListeners()
    }

    private fun initClickListeners() {
        binding.btnMoveNext.setOnClickListener {
            setDatastoreValues()
            startActivity(Intent(this@MainActivity, DataStoreActivity::class.java))
        }
    }

    private fun setDatastoreValues() {
        lifecycleScope.launch(Dispatchers.IO) {
            DataStore.run {
                getInstance().setString(key = "user_name", value = "Kamran Shehzad")
                getInstance().setBoolean(key = "is_first_time", value = true)
                getInstance().setInt(key = "user_age", value = 30)
                getInstance().setLong(key = "price", value = 550L)
                getInstance().setDouble(key = "radius", value = 99.99)
                getInstance().setFloat(key = "distance", value = 555.55f)
                getInstance().setObject(key = "user_object", value = UserData(name = "Kamran", age = 20, isAdult = true, height = 7.1f))
            }
        }
    }

    private fun initDatastore() {
        DataStore.initInstance(context = this, dateStoreName = "appDataStore")
    }
}