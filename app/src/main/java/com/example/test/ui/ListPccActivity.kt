package com.example.test.ui

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.common.SessionManager
import com.example.test.databinding.ActivityListPccBinding
import com.example.test.ui.adapters.PccAdapter
import com.example.test.viewmodel.ListPccViewModel

class ListPccActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListPccBinding
    lateinit var pccAdapter: PccAdapter
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListPccBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val upArrow: Drawable? = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        upArrow?.setColorFilter(
            ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP
        )
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        sessionManager = SessionManager(this)

        // Initialize the adapter with an empty list
        pccAdapter = PccAdapter(emptyList()) // Initialize with an empty list


        // Setup RecyclerView with the LayoutManager and the Adapter
        binding.recyclerViewPcc.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPcc.adapter = pccAdapter // Set the adapter

        // Initialize your ViewModel here (assuming you have a ViewModel set up)
        val viewModel = ViewModelProvider(
            this, ListPccViewModel.Factory(this.application)
        )[ListPccViewModel::class.java]
        // Observe the album data from the ViewModel
        viewModel.pcc.observe(this) { pcc ->
            // When album data changes, update the adapter's dataset
            pccAdapter.updatePcc(pcc)
        }

        pccAdapter.setOnItemClickListener {
            viewModel.pccId.value = viewModel.pcc.value?.get(it)?.id
        }

        viewModel.fetchAllPcc(sessionManager.getValue(sessionManager.keyToken) ?: "")
    }
}