Step 1. Add the JitPack repository to your build file

	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}

 Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.KamranShehzadDeveloper:DataStoreLibrary:1.0.1'
	}

How to use this library

initialize it on main activity

    DataStore.initInstance(context = this, dateStoreName = "appDataStore")

How to set datastore values

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

How to fetch datastore values

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

  Note To Set And Fetch Object Must Implement 

    implementation(libs.gson)
