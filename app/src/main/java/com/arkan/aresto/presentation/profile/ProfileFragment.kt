package com.arkan.aresto.presentation.profile

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.arkan.aresto.R
import com.arkan.aresto.data.datasource.auth.AuthDataSource
import com.arkan.aresto.data.datasource.auth.FirebaseAuthDataSource
import com.arkan.aresto.data.repository.UserRepository
import com.arkan.aresto.data.repository.UserRepositoryImpl
import com.arkan.aresto.data.source.firebase.FirebaseService
import com.arkan.aresto.data.source.firebase.FirebaseServiceImpl
import com.arkan.aresto.databinding.FragmentProfileBinding
import com.arkan.aresto.presentation.login.LoginActivity
import com.arkan.aresto.presentation.main.MainActivity
import com.arkan.aresto.utils.GenericViewModelFactory
import com.arkan.aresto.utils.proceedWhen

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels {
        val s: FirebaseService = FirebaseServiceImpl()
        val ds: AuthDataSource = FirebaseAuthDataSource(s)
        val rp: UserRepository = UserRepositoryImpl(ds)
        GenericViewModelFactory.create(ProfileViewModel(rp))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProfileState()
        setClickListener()
        showUserData()
        observeEditMode()
    }

    private fun setClickListener() {
        binding.btnEdit.setOnClickListener {
            viewModel.changeEditMode()
        }
        binding.btnSave.setOnClickListener {
            doUpdateEmail()
        }
        binding.tvChangePassword.setOnClickListener {
            requestChangePasswordDialog()
        }
        binding.btnLogin.setOnClickListener {
            navigateToLogin()
        }
        binding.btnLogout.setOnClickListener {
            logoutDialog()
        }
    }

    private fun observeEditMode() {
        viewModel.isEditMode.observe(viewLifecycleOwner) {
            binding.nameEditText.isEnabled = it
            binding.btnSave.isVisible = it
            binding.btnLogout.isVisible = !it
            binding.llEdit.isVisible = !it
        }
    }

    private fun doUpdateEmail() {
        if (isFormValid()) {
            val fullName = binding.nameEditText.text.toString().trim()
            updateProfile(fullName)
        }
    }

    private fun isFormValid(): Boolean {
        val fullName = binding.nameEditText.text.toString().trim()
        return checkNameValidation(fullName)
    }

    private fun updateProfile(fullName: String) {
        viewModel.updateProfile(fullName).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnSave.isVisible = true
                    Toast.makeText(requireContext(), getString(R.string.text_edit_pofile_success), Toast.LENGTH_SHORT).show()
                    viewModel.changeEditMode()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnSave.isVisible = true
                    Toast.makeText(requireContext(), getString(R.string.text_edit_pofile_failed), Toast.LENGTH_SHORT).show()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnSave.isVisible = false
                }
            )
        }
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

    private fun setupProfileState() {
        if (viewModel.isUserLoggedIn()) {
            binding.btnLogout.isVisible = true
        } else {
            binding.nameInputLayout.isVisible = false
            binding.emailInputLayout.isVisible = false
            binding.tvChangePassword.isVisible = false
            binding.llEdit.isVisible = false
            binding.btnLogin.isVisible = true
        }
    }

    private fun showUserData() {
        viewModel.getCurrentUser()?.let {
            binding.nameEditText.setText(it.fullName)
            binding.emailEditText.setText(it.email)
        }
    }

    private fun logoutDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_logout_confirmation)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val backBtn: Button = dialog.findViewById(R.id.btn_cancel)
        val confirmBtn: Button = dialog.findViewById(R.id.btn_logout)
        backBtn.setOnClickListener {
            dialog.dismiss()
        }
        confirmBtn.setOnClickListener {
            dialog.dismiss()
            viewModel.doLogout()
            backToHome()
        }
        dialog.show()
    }

    private fun requestChangePasswordDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_change_password_request)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.findViewById<TextView?>(R.id.tv_title_password_dialog).text = getString(
            R.string.text_change_password_dialog,
            viewModel.getCurrentUser()?.email
        )
        viewModel.createChangePwdRequest()
        val backBtn: Button = dialog.findViewById(R.id.btn_back)
        backBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun backToHome() {
        startActivity(Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
    }
}