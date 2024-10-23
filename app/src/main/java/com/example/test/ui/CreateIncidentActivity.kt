package com.example.test.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.test.R
import com.example.test.common.validateFieldString
import com.example.test.databinding.ActivityCreateIncidentBinding
import com.example.test.viewmodel.CreateIncidentViewModel

class CreateIncidentActivity : AppCompatActivity() {
    private var _binding: ActivityCreateIncidentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CreateIncidentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateIncidentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this, CreateIncidentViewModel.Factory(this.application)
        )[CreateIncidentViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val items = listOf("Bancolombia", "Constructora Bolivar", "Claro")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items)
        binding.companies.setAdapter(adapter)

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

        binding.asunto.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.errorSubject.postValue(
                    validateFieldString(viewModel.subject.value, 1, 250, binding.root.context)
                )
            }
        }

        binding.companies.setOnItemClickListener { parent, view, position, id ->
            val selectedOption = parent.getItemAtPosition(position) as String
            viewModel.company.postValue(selectedOption)
        }
        viewModel.company.observe(this){
            it.apply {
                viewModel.errorCompany.postValue(
                    validateFieldString(viewModel.company.value, 0, 1000, binding.root.context)
                )
            }
        }

        viewModel.errorCompany.observe(this){
            it.apply {
                viewModel.isValidCompany.postValue(true)
                if(!it.equals("")){
                    viewModel.isValidCompany.postValue(false)
                }
            }
        }

        binding.companies.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.errorCompany.postValue(
                    validateFieldString(viewModel.company.value, 0, 1000, binding.root.context)
                )
            }
        }

        viewModel.description.observe(this) {
            it.apply {
                viewModel.errorDescription.postValue(
                    validateFieldString(viewModel.description.value, 100, 1000, binding.root.context)
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

        binding.descripcion.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.errorDescription.postValue(
                    validateFieldString(viewModel.description.value, 100, 1000, binding.root.context)
                )
            }
        }

        binding.btnSave.setOnClickListener {
            viewModel.errorSubject.postValue(
                validateFieldString(viewModel.subject.value, 1, 250, binding.root.context)
            )
            viewModel.errorCompany.postValue(
                validateFieldString(viewModel.company.value, 0, 1000, binding.root.context)
            )
            viewModel.errorDescription.postValue(
                validateFieldString(viewModel.description.value, 100, 1000, binding.root.context)
            )
            viewModel.Create()
        }

        viewModel.error.observe(this) {
            it.apply {
                if (it) {
                    val builder = AlertDialog.Builder(binding.root.context)
                    builder.setTitle(R.string.notificacion)
                    builder.setMessage(R.string.error_general)
                    builder.setPositiveButton(R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                    }
                    val dialog = builder.create()
                    dialog.show()
                } else {
                    val builder = AlertDialog.Builder(binding.root.context)
                    builder.setTitle(R.string.notificacion)
                    builder.setMessage(R.string.incidente_creado)
                    builder.setPositiveButton(R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                        val intent =
                            Intent(binding.root.context, OptionsActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    val dialog = builder.create()
                    dialog.show()
                }
            }
        }
    }
}