package com.example.test.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.test.R
import com.example.test.common.validateEmail
import com.example.test.common.validateValue
import com.example.test.databinding.ActivityLoginBinding
import com.example.test.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this, LoginViewModel.Factory(this.application)
        )[LoginViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.email.observe(this) {
            it.apply {
                viewModel.errorEmail.postValue(
                    validateEmail(viewModel.email.value, binding.root.context)
                )
            }
        }

        viewModel.errorEmail.observe(this) {
            it.apply {
                viewModel.isValidEmail.postValue(true)
                if (!it.equals("")) {
                    viewModel.isValidEmail.postValue(false)
                }
            }
        }

        binding.email.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.errorEmail.postValue(
                    validateEmail(viewModel.email.value, binding.root.context)
                )
            }
        }

        viewModel.password.observe(this) {
            it.apply {
                viewModel.errorPassword.postValue(
                    validateValue(viewModel.password.value, -1, binding.root.context)
                )
            }
        }

        viewModel.errorPassword.observe(this) {
            it.apply {
                viewModel.isValidPassword.postValue(true)
                if (!it.equals("")) {
                    viewModel.isValidPassword.postValue(false)
                }
            }
        }

        binding.password.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.errorPassword.postValue(
                    validateValue(viewModel.password.value, -1, binding.root.context)
                )
            }
        }

        binding.btnSubmit.setOnClickListener {
            viewModel.errorEmail.postValue(
                validateEmail(viewModel.email.value, binding.root.context)
            )
            viewModel.errorPassword.postValue(
                validateValue(viewModel.password.value, -1, binding.root.context)
            )
            viewModel.login()
        }

        viewModel.error.observe(this) {
            it.apply {
                if (it) {
                    Toast.makeText(
                        binding.root.context,
                        R.string.error4,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        binding.root.context,
                        R.string.bienvenido,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}