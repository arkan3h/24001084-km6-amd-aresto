package com.arkan.aresto.presentation.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.arkan.aresto.R
import com.arkan.aresto.databinding.ActivityRegisterBinding
import com.arkan.aresto.presentation.login.LoginActivity
import com.arkan.aresto.presentation.main.MainActivity
import com.arkan.aresto.utils.proceedWhen
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnRegister.setOnClickListener {
            doRegister()
        }
        binding.tvNavToLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun doRegister() {
        if (isFormValid()) {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val fullName = binding.nameEditText.text.toString().trim()
            proceedRegister(email, password, fullName)
        }
    }

    private fun proceedRegister(
        email: String,
        password: String,
        fullName: String,
    ) {
        viewModel.doRegister(email, fullName, password).observe(this) { it ->
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnRegister.isVisible = true
                    navigateToMain()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnRegister.isVisible = true
                    Toast.makeText(
                        this,
                        "Login Failed : ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnRegister.isVisible = false
                },
            )
        }
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
        )
    }

    private fun navigateToMain() {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }

    private fun isFormValid(): Boolean {
        val password = binding.passwordEditText.text.toString().trim()
        val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()
        val fullName = binding.nameEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()

        return checkNameValidation(fullName) && checkEmailValidation(email) &&
            checkPasswordValidation(password, binding.passwordInputLayout) &&
            checkPasswordValidation(confirmPassword, binding.confirmPasswordInputLayout) &&
            checkPwdAndConfirmPwd(password, confirmPassword)
    }

    private fun checkNameValidation(fullName: String): Boolean {
        return if (fullName.isEmpty()) {
            binding.nameInputLayout.isErrorEnabled = true
            binding.nameInputLayout.error = getString(R.string.text_error_name_cannot_empty)
            false
        } else {
            binding.nameInputLayout.isErrorEnabled = false
            true
        }
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
        textInputLayout: TextInputLayout,
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

    private fun checkPwdAndConfirmPwd(
        password: String,
        confirmPassword: String,
    ): Boolean {
        return if (password != confirmPassword) {
            binding.passwordInputLayout.isErrorEnabled = true
            binding.passwordInputLayout.error =
                getString(R.string.text_password_does_not_match)
            binding.confirmPasswordInputLayout.isErrorEnabled = true
            binding.confirmPasswordInputLayout.error =
                getString(R.string.text_password_does_not_match)
            false
        } else {
            binding.passwordInputLayout.isErrorEnabled = false
            binding.confirmPasswordInputLayout.isErrorEnabled = false
            true
        }
    }
}
