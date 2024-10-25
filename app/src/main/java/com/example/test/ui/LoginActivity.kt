package com.example.test.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.test.R
import com.example.test.common.SessionManager
import com.example.test.common.validateEmail
import com.example.test.common.validateValue
import com.example.test.databinding.ActivityLoginBinding
import com.example.test.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        viewModel = ViewModelProvider(
            this, LoginViewModel.Factory(this.application, sessionManager)
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

        viewModel.token.observe(this) {
            it.apply {
                sessionManager.addValue(sessionManager.keyToken, it)
            }
        }

        viewModel.error.observe(this) {
            it.apply {
                if (it) {
                    val builder = AlertDialog.Builder(binding.root.context)
                    builder.setMessage(R.string.error4)
                    builder.setPositiveButton(R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                    }
                    val dialog = builder.create()
                    dialog.show()
                } else {
                    val builder = AlertDialog.Builder(binding.root.context)
                    builder.setMessage(R.string.bienvenido)
                    builder.setPositiveButton(R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                        startActivity(Intent(binding.root.context, OptionsActivity::class.java))
                        finish()
                    }
                    val dialog = builder.create()
                    dialog.show()
                }
            }
        }
    }
}