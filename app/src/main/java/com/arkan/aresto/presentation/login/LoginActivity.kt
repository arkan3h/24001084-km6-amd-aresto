package com.arkan.aresto.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.arkan.aresto.R
import com.arkan.aresto.data.datasource.auth.AuthDataSource
import com.arkan.aresto.data.datasource.auth.FirebaseAuthDataSource
import com.arkan.aresto.data.repository.UserRepository
import com.arkan.aresto.data.repository.UserRepositoryImpl
import com.arkan.aresto.data.source.firebase.FirebaseService
import com.arkan.aresto.data.source.firebase.FirebaseServiceImpl
import com.arkan.aresto.databinding.ActivityLoginBinding
import com.arkan.aresto.presentation.main.MainActivity
import com.arkan.aresto.presentation.register.RegisterActivity
import com.arkan.aresto.utils.GenericViewModelFactory
import com.arkan.aresto.utils.proceedWhen
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val viewModel: LoginViewModel by viewModels {
        val s: FirebaseService = FirebaseServiceImpl()
        val ds: AuthDataSource = FirebaseAuthDataSource(s)
        val r: UserRepository = UserRepositoryImpl(ds)
        GenericViewModelFactory.create(LoginViewModel(r))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setClickListener()
        observeResult()
    }

    private fun setClickListener() {
        binding.btnLogin.setOnClickListener {
            doLogin()
        }
        binding.tvNavToLogin.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
    }

    private fun observeResult() {
        viewModel.loginResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isVisible = true
                    navigateToMain()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isVisible = true
                    Toast.makeText(
                        this,
                        "Login Failed : ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnLogin.isVisible = false
                }
            )
        }
    }

    private fun doLogin() {
        if (isFormValid()) {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            viewModel.doLogin(email, password)
        }
    }

    private fun isFormValid(): Boolean {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        return checkEmailValidation(email) &&
                checkPasswordValidation(password, binding.passwordInputLayout)
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.emailInputLayout.isErrorEnabled = true
            binding.emailInputLayout.error = getString(R.string.text_error_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInputLayout.isErrorEnabled = true
            binding.emailInputLayout.error = getString(R.string.text_error_email_invalid)
            false
        } else {
            binding.emailInputLayout.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordValidation(
        confirmPassword: String,
        textInputLayout: TextInputLayout
    ): Boolean {
        return if (confirmPassword.isEmpty()) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error =
                getString(R.string.text_error_password_empty)
            false
        } else if (confirmPassword.length < 8) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error =
                getString(R.string.text_error_password_less_than_8_char)
            false
        } else {
            textInputLayout.isErrorEnabled = false
            true
        }
    }
}