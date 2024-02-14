package com.feature.registration.ui.screen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.core.ui.R
import com.core.ui.base.BaseFragment
import com.feature.registration.databinding.FragmentRegistrationBinding
import com.feature.registration.ui.viewmodel.RegistrationState
import com.feature.registration.ui.viewmodel.RegistrationViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>() {

    private val viewModel by viewModel<RegistrationViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegistrationBinding = FragmentRegistrationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPhoneMask()
        setNameWatcher()
        setSurnameWatcher()
        setupCrossButtons()
        if(viewModel.isAlreadyLogged()) {
            findNavController().navigate(com.feature.registration.R.id.action_registrationFragment_to_mainFragment)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.registrationState.collect { state ->
                handleRegistrationState(state)
            }
        }
    }

    private fun handleRegistrationState(state: RegistrationState) {
        when (state) {
            is RegistrationState.ButtonActive -> {
                when (state.isButtonActive) {
                    true -> {
                        with(binding.button) {
                            isClickable = true
                            setBackgroundResource(R.drawable.rounded_corners_button_active_background)
                            setOnClickListener {
                                findNavController().navigate(com.feature.registration.R.id.action_registrationFragment_to_mainFragment)
                                viewModel.saveCredentials()
                            }

                        }
                    }

                    false -> {
                        with(binding.button) {
                            isClickable = false
                            setBackgroundResource(R.drawable.rounded_corners_button_inactive_background)
                        }
                    }
                }
            }

            is RegistrationState.CorrectName -> {
                binding.nameEditText.apply {
                    if (state.isCorrectName) {
                        this.setBackgroundResource(R.drawable.rounded_corners_background)
                    } else this.setBackgroundResource(R.drawable.rounded_corners_error_background)
                }
            }

            is RegistrationState.CorrectSurname -> {
                binding.surnameEditText.apply {
                    if (state.isCorrectSurname) {
                        this.setBackgroundResource(R.drawable.rounded_corners_background)
                    } else this.setBackgroundResource(R.drawable.rounded_corners_error_background)
                }
            }

            RegistrationState.DefaultState -> {

            }

            is RegistrationState.AlreadyLogged -> {
//                if (state.isLogged) findNavController().navigate(com.feature.registration.R.id.action_registrationFragment_to_mainFragment)
            }
        }
    }

    private fun setupCrossButtons() {
        with(binding) {
            crossName.setOnClickListener {
                nameEditText.setText("")
            }
            crossSurname.setOnClickListener {
                surnameEditText.setText("")
            }
            crossPhoneNumber.setOnClickListener {
                phoneNumberEditText.setText("")
            }
        }

    }


    private fun setNameWatcher() {
        binding.nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(name: Editable?) {
                viewModel.isCorrectName(name.toString())
                binding.crossName.isVisible = name?.isNotBlank() ?: false
            }

        })
    }

    private fun setSurnameWatcher() {
        binding.surnameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(surname: Editable?) {
                viewModel.isCorrectSurname(surname.toString())
                binding.crossSurname.isVisible = surname?.isNotBlank() ?: false
            }

        })
    }


    private fun setPhoneMask() {

        binding.phoneNumberEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(text: Editable?) {
                if (!text.isNullOrEmpty()) {
                    val unmaskedText = binding.phoneNumberEditText.unMaskedText.toString()
                    if (unmaskedText.length > 11) {
                        return
                    }
                    viewModel.isCorrectPhone(unmaskedText)
                    binding.crossPhoneNumber.isVisible = true
                } else {
                    viewModel.isCorrectPhone("")
                    binding.crossPhoneNumber.isVisible = false
                }

            }
        })
    }

}

