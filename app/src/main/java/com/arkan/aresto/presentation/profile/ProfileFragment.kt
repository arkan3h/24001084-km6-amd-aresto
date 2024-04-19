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
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import coil.load
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
        observeEditMode()
        observeProfileData()
    }

    private fun observeProfileData() {
        viewModel.profileData.observe(viewLifecycleOwner) {
            binding.ivProfile.load(it.profileImg) {
                crossfade(true)
                error(R.drawable.ic_tab_profile)
            }
            binding.nameEditText.setText(it.name)
            binding.emailEditText.setText(it.email)
        }
    }

    private fun setClickListener() {
        binding.btnEdit.setOnClickListener {
            viewModel.changeEditMode()
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
            binding.emailEditText.isEnabled = it
            binding.nameEditText.isEnabled = it
        }
    }

    private fun setupProfileState() {
        if (viewModel.isUserLoggedIn()) {
            binding.btnLogout.isVisible = true
        } else {
            binding.btnLogin.isVisible = true
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
            viewModel.doLogout()
            backToHome()
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