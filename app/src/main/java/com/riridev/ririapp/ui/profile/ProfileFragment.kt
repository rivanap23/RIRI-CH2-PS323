package com.riridev.ririapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.riridev.ririapp.R
import com.riridev.ririapp.data.remote.response.UserResponse
import com.riridev.ririapp.data.result.Result
import com.riridev.ririapp.databinding.FragmentProfileBinding
import com.riridev.ririapp.ui.ViewModelFactory
import com.riridev.ririapp.ui.dialog.LogoutDialogFragment
import com.riridev.ririapp.ui.editprofile.EditProfileActivity
import com.riridev.ririapp.ui.editprofilepicture.EditProfilePictureActivity

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
    private val profileViewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var userProfile: UserResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        profileViewModel.getUserProfile()
    }

    override fun onResume() {
        super.onResume()
        profileViewModel.getUserProfile()
        getProfile()
    }

    private fun getProfile() {
        profileViewModel.profileDetail.observe(viewLifecycleOwner){ result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    userProfile = result.data
                    binding?.profileLayout?.tvEmail?.text = result.data.email
                    binding?.profileLayout?.tvUsername?.text = result.data.username
                    Glide.with(requireActivity())
                        .load(userProfile.profileImageUrl)
                        .placeholder(R.drawable.baseline_account_circle_24)
                        .into(binding?.profileLayout?.ivProfile as ImageView)
                }

                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupAction() {
        logout()
        editProfileData()
        editProfilePicture()
    }

    private fun editProfilePicture() {
        binding?.profileLayout?.btnEditProfilePic?.setOnClickListener {
            val intent = Intent(requireContext(), EditProfilePictureActivity::class.java)
            intent.putExtra("profile_url", userProfile.profileImageUrl)
            startActivity(intent)
        }
    }

    private fun editProfileData() {
        binding?.profileLayout?.cardEditProfile?.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            intent.putExtra(EditProfileActivity.USERNAME, userProfile.username)
            intent.putExtra(EditProfileActivity.EMAIL, userProfile.email)
            intent.putExtra(EditProfileActivity.DATE, userProfile.dateOfBirth)
            intent.putExtra(EditProfileActivity.PLACE, userProfile.placeOfBirth)
            startActivity(intent)
        }
    }

    private fun logout() {
        binding?.profileLayout?.logout?.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        LogoutDialogFragment().show(
            parentFragmentManager, LogoutDialogFragment.TAG)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.svLayout?.visibility = View.GONE
            binding?.loadingIndicator?.visibility = View.VISIBLE
        } else {
            binding?.svLayout?.visibility = View.VISIBLE
            binding?.loadingIndicator?.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}