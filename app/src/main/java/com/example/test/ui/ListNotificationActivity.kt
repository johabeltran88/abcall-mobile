package com.example.test.ui

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.common.SessionManager
import com.example.test.databinding.ActivityListNotificationBinding
import com.example.test.ui.adapters.NotificationAdapter
import com.example.test.ui.adapters.PccAdapter
import com.example.test.viewmodel.ListNotificationViewModel

class ListNotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListNotificationBinding
    lateinit var notificationAdapter: NotificationAdapter
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListNotificationBinding.inflate(layoutInflater)
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
        val pccId = sessionManager.getValue(sessionManager.keyIdPcc).orEmpty()
        // Initialize the adapter with an empty list
        notificationAdapter = NotificationAdapter(emptyList()) // Initialize with an empty list


        // Setup RecyclerView with the LayoutManager and the Adapter
        binding.recyclerViewPcc.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPcc.adapter = notificationAdapter // Set the adapter

        // Initialize your ViewModel here (assuming you have a ViewModel set up)
        val viewModel = ViewModelProvider(
            this, ListNotificationViewModel.Factory(this.application, pccId)
        )[ListNotificationViewModel::class.java]

        binding.viewModel = viewModel

        // Observe the album data from the ViewModel
        viewModel.notification.observe(this) { notification ->
            // When album data changes, update the adapter's dataset
            notificationAdapter.updateNotification(notification)
        }

        viewModel.fetchAllNotification(sessionManager.getValue(sessionManager.keyToken) ?: "")
    }
}