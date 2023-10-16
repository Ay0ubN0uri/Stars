package com.a00n.stars.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.a00n.stars.R
import com.a00n.stars.data.local.entities.Star
import com.a00n.stars.databinding.ActivityMainBinding
import com.a00n.stars.ui.adapters.StarListAdapter
import com.a00n.stars.ui.viewmodels.MainViewModel
import java.util.Locale

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val adapter: StarListAdapter by lazy { StarListAdapter() }
    private lateinit var speechIntentResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(binding.root)
        setSupportActionBar(binding.materialToolbar)
        binding.recycleview.adapter = adapter
        binding.recycleview.layoutManager = LinearLayoutManager(this)
        binding.recycleview.showShimmer()
//        Handler(Looper.getMainLooper()).postDelayed({
        init()
//        }, 3000)

        speechIntentResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                val res = it.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                Log.i("info", "onCreate: ${res?.get(0).toString()}")
                res?.let { txt ->
                    adapter.filter.filter(txt[0].toString())
                }
//                if(res.isNullOrEmpty() && !res?.get(0).toString().isNullOrEmpty()){
//                    adapter.filter.filter(res?.get(0).toString())
//                }
            }


//        setFabVisibility(false)

        binding.fab.setOnClickListener {
            adapter.submitList(viewModel.findAll().toMutableList())
        }
    }

    private fun setFabVisibility(visible: Boolean) {
        if (visible) {
            binding.fab.visibility = View.VISIBLE
        } else {
            binding.fab.visibility = View.GONE
        }
    }

    private fun init() {
        val star = Star("hello", "", 5)
        viewModel.add(star)
        viewModel.add(
            Star(
                "John Doe",
                "https://images.unsplash.com/photo-1618077360395-f3068be8e001?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=200&ixid=MnwxfDB8MXxyYW5kb218MHx8bWFufHx8fHx8MTY5NzQ4NjI3OA&ixlib=rb-4.0.3&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=200",
                5
            )
        )
        viewModel.add(
            Star(
                "Andrew Tate",
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fcdn.allfamous.org%2Fpeople%2Fheadshots%2Fandrew-tate-fi5a-allfamous.org-3.jpg&f=1&nofb=1&ipt=9ec27311b96fa7390995e4dd0207fe84ad0e58586b04711bba5990f42a79297e&ipo=images",
                4
            )
        )
        binding.recycleview.hideShimmer()
        Log.i("info", "init: ${viewModel.findAll()}")
        adapter.setFullList(viewModel.findAll())
        adapter.submitList(viewModel.findAll().toMutableList())
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menu?.let {
            val search = it.findItem(R.id.search_menu)
            val searchView = search.actionView as SearchView
            searchView.setOnQueryTextListener(this)
            searchView.setOnCloseListener {
                adapter.submitList(viewModel.findAll().toMutableList())
                false
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.voice_search) {
            Log.i("info", "onOptionsItemSelected: Voice search")
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something")
            speechIntentResult.launch(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.i("info", "onQueryTextSubmit: $query")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.i("info", "onQueryTextChange: $newText")
        if (newText?.isEmpty() == true) {
//            adapter.submitList(viewModel.findAll().toMutableList())
        } else {
            adapter.filter.filter(newText)
        }

        return true
    }
}