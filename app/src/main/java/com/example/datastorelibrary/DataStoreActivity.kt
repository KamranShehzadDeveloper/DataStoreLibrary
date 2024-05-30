package com.example.datastorelibrary

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.datastorehelperlib.DataStore
import com.example.datastorelibrary.databinding.ActivityDataStoreBinding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataStoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDataStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataFromDataStore()
    }

    private fun getDataFromDataStore() {
        lifecycleScope.launch(Dispatchers.IO) {
            launch {
                DataStore.getInstance().readStringPreference("user_name", "kamran")
                    .collect { value ->
                        withContext(Dispatchers.Main) {
                            binding.userData.text = value
                        }
                    }
            }
            launch {
                DataStore.getInstance().readBooleanPreference("is_first_time", false).collect { value ->
                    withContext(Dispatchers.Main) {
                        binding.btnSw.isChecked = value
                    }
                }
            }
            launch {
                DataStore.getInstance().readIntPreference("user_age", 20).collect { value ->
                    withContext(Dispatchers.Main) {
                        binding.userAge.text = value.toString()
                    }
                }
            }
            launch {
                DataStore.getInstance().readLongPreference("price", 200L).collect { value ->
                    withContext(Dispatchers.Main) {
                        binding.price.text = value.toString()
                    }
                }
            }
            launch {
                DataStore.getInstance().readDoublePreference("radius",22.22).collect { value ->
                    withContext(Dispatchers.Main) {
                        binding.radius.text = value.toString()
                    }
                }
            }
            launch {
                DataStore.getInstance().readFloatPreference("distance",55.55f).collect { value ->
                    withContext(Dispatchers.Main) {
                        binding.distance.text = value.toString()
                    }
                }
            }
            launch {
                DataStore.getInstance().readObjectPreference("user_object", "user_object").collect { value ->
                    val userObject = Gson().fromJson(value, UserData::class.java)
                    withContext(Dispatchers.Main) {
                        binding.objectData.text = "Name ${userObject.name} Age ${userObject.age} IsAdult ${userObject.isAdult} Height ${userObject.height}"
                    }
                }
            }
        }
    }
}