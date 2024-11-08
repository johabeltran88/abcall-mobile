package com.example.test.ui

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.test.R
import com.example.test.common.SessionManager
import com.example.test.databinding.ActivityDetailPccBinding
import com.example.test.viewmodel.DetailPccViewModel
import java.net.URL

class DetailPccActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPccBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val pccId = intent.getStringExtra("pccId") ?: "Default Value"

        super.onCreate(savedInstanceState)
        binding = ActivityDetailPccBinding.inflate(layoutInflater)
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

        val viewModel = ViewModelProvider(
            this, DetailPccViewModel.Factory(this.application, pccId)
        )[DetailPccViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.pcc.observe(this) { pcc ->
            pcc?.let {
                binding.tvTitle.text = pcc.subject ?: "No Subject Available"
                binding.subject.text = Editable.Factory.getInstance().newEditable(
                    binding.subject.text.toString().plus(it.subject)
                )
                binding.company.text = Editable.Factory.getInstance().newEditable(
                    binding.company.text.toString().plus(it.company?.name ?: "Unknown Company")
                )
                binding.description.text = Editable.Factory.getInstance().newEditable(
                    binding.description.text.toString().plus(it.description)
                )
            } ?: run {
                // Maneja el caso cuando pcc es null, por ejemplo, mostrando un mensaje o manejando el error
                binding.subject.text = Editable.Factory.getInstance().newEditable("No data")
                binding.company.text = Editable.Factory.getInstance().newEditable("No data")
                binding.description.text = Editable.Factory.getInstance().newEditable("No data")
            }
        }

        viewModel.fetchAllPcc(sessionManager.getValue(sessionManager.keyToken) ?: "")
    }
}