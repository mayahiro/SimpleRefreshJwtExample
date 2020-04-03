package xyz.mayahiro.simplerefreshjwtexample

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import xyz.mayahiro.simplerefreshjwtexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.bind(findViewById<ViewGroup>(android.R.id.content)[0])

        viewModel.result.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }

        binding.getTokenButton.setOnClickListener {
            viewModel.getToken()
        }

        binding.withoutAutoRefreshButton.setOnClickListener {
            viewModel.withoutAutoRefreshApiAccess()
        }

        binding.withAutoRefreshButton.setOnClickListener {
            viewModel.withAutoRefreshApiAccess()
        }
    }
}
