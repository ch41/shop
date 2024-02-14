package com.feature.profile.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.core.ui.base.BaseFragment
import com.feature.profile.R
import com.feature.profile.databinding.FragmentProfileBinding
import com.feature.profile.ui.ProfileState
import com.feature.profile.ui.viewmodel.ProfileViewModel
import com.feature.registration.ui.viewmodel.RegistrationViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel by viewModel<ProfileViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profileState.collect { state ->
                when (state) {
                    ProfileState.Exit -> {
//                        findNavController().navigate(R.id.action_profileFragment_to_registrationFragment2)
                    }

                    ProfileState.Default -> {}
                    is ProfileState.UserCredentials -> {
                        with(binding) {
                            userNumberTextView.text = state.phoneNumber
                            userNameTextView.text = "${state.name} ${state.surname}"
                        }
                    }
                }
            }
        }
        binding.logOutButton.setOnClickListener {
            viewModel.onButtonExitClick()
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

}