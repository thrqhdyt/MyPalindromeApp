package dev.thorcode.mypalindromeapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import dev.thorcode.mypalindromeapplication.R
import dev.thorcode.mypalindromeapplication.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = ProfileFragmentArgs.fromBundle(arguments as Bundle).name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.materialToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.nameText.text = name

        binding.btnChooseUser.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_profileFragment_to_listUserNameFragment)
        )

        val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<String>(ListUserNameFragment.ON_SELECTED_ITEM)
            ?.observe(viewLifecycleOwner) {
                if(it.isNotBlank()){
                    binding.selectedUserName.text = it
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}