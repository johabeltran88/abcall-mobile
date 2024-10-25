package com.example.test.ui

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.test.R
import com.example.test.common.SessionManager
import com.example.test.common.validateFieldString
import com.example.test.common.validateValue
import com.example.test.databinding.ActivityCreatePccBinding
import com.example.test.viewmodel.CreatePccViewModel

class CreatePccActivity : AppCompatActivity() {
    private var _binding: ActivityCreatePccBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CreatePccViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreatePccBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val upArrow: Drawable? = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        upArrow?.setColorFilter(
            ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP
        )
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        viewModel = ViewModelProvider(
            this, CreatePccViewModel.Factory(this.application, sessionManager)
        )[CreatePccViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.subject.observe(this) {
            it.apply {
                viewModel.errorSubject.postValue(
                    validateFieldString(viewModel.subject.value, 1, 250, binding.root.context)
                )
            }
        }

        viewModel.errorSubject.observe(this) {
            it.apply {
                viewModel.isValidSubject.postValue(true)
                if (!it.equals("")) {
                    viewModel.isValidSubject.postValue(false)
                }
            }
        }

        binding.subject.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.errorSubject.postValue(
                    validateValue(viewModel.subject.value, 250, binding.root.context)
                )
            }
        }

        viewModel.description.observe(this) {
            it.apply {
                viewModel.errorDescription.postValue(
                    validateFieldString(
                        viewModel.description.value,
                        100,
                        1000,
                        binding.root.context
                    )
                )
            }
        }

        viewModel.errorDescription.observe(this) {
            it.apply {
                viewModel.isValidDescription.postValue(true)
                if (!it.equals("")) {
                    viewModel.isValidDescription.postValue(false)
                }
            }
        }

        binding.description.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.errorDescription.postValue(
                    validateFieldString(
                        viewModel.description.value,
                        100,
                        1000,
                        binding.root.context
                    )
                )
            }
        }

        viewModel.errorCompany.observe(this) {
            it.apply {
                viewModel.isValidCompany.postValue(true)
                if (!it.equals("")) {
                    viewModel.isValidCompany.postValue(false)
                }
            }
        }

        binding.company.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.errorCompany.postValue(
                    validateValue(viewModel.companyId.value, -1, binding.root.context)
                )
            }
        }

        sessionManager.getValue(sessionManager.keyToken)?.let { viewModel.getConsumer(it) }

        viewModel.consumer.observe(this) { consumer ->
            val adapter =
                ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, consumer.companies)
            binding.company.setAdapter(adapter)
        }

        binding.company.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val selectedCompany =
                    viewModel.consumer.value?.companies?.find { it.name == s.toString() }
                selectedCompany?.let {
                    viewModel.companyId.value = it.id
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.btnSubmit.setOnClickListener {
            viewModel.errorCompany.postValue(
                validateValue(viewModel.companyId.value, -1, binding.root.context)
            )
            viewModel.errorSubject.postValue(
                validateValue(viewModel.subject.value, 250, binding.root.context)
            )
            viewModel.errorDescription.postValue(
                validateFieldString(viewModel.description.value, 100, 1000, binding.root.context)
            )
            viewModel.createPcc()
        }

        viewModel.errorCreatePcc.observe(this) {
            it.apply {
                if (it) {
                    val builder = AlertDialog.Builder(binding.root.context)
                    builder.setMessage(R.string.error6)
                    builder.setPositiveButton(R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                    }
                    val dialog = builder.create()
                    dialog.show()
                } else {
                    val builder = AlertDialog.Builder(binding.root.context)
                    builder.setMessage(R.string.incidente_creado)
                    builder.setPositiveButton(R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                        finish()
                    }
                    val dialog = builder.create()
                    dialog.show()
                }
            }
        }

    }
}
