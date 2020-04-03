package xyz.mayahiro.simplerefreshjwtexample

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _result = MutableLiveData<String>()
    val result: LiveData<String>
        get() = _result

    fun getToken() {
        viewModelScope.launch {
            try {
                val token = ApiClient.service(NetworkService::class.java).getToken()
                SharedPreferenceManager.setToken(token)

                _result.value = "get Token"
            } catch (e: Exception) {
                Log.e("MainViewModel", e.toString())
                _result.value = e.toString()
            }
        }
    }

    fun withoutAutoRefreshApiAccess() {
        viewModelScope.launch {
            try {
                _result.value = ApiClient.service(NetworkService::class.java).getSecret()
            } catch (e: Exception) {
                Log.e("MainViewModel", e.toString())
                _result.value = e.toString()
            }
        }
    }

    fun withAutoRefreshApiAccess() {
        viewModelScope.launch {
            try {
                _result.value = ApiClient.autoRefreshService(NetworkService::class.java).getSecret()
            } catch (e: Exception) {
                Log.e("MainViewModel", e.toString())
                _result.value = e.toString()
            }
        }
    }
}
