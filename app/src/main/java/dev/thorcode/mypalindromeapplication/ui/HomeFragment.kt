package dev.thorcode.mypalindromeapplication.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dev.thorcode.mypalindromeapplication.R
import dev.thorcode.mypalindromeapplication.databinding.FragmentHomeBinding
import dev.thorcode.mypalindromeapplication.utils.isPalindrome

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCheck.setOnClickListener {
            val query = binding.palindromeEditText.text.toString()
            val isPalindrome = query.isPalindrome()
            val message = if(!isPalindrome) {
                "Not Palindrome"
            }  else "IsPalindrome"

            AlertDialog.Builder(requireContext()).apply {
                setTitle(message)
                setMessage("$query $message")
                setPositiveButton("Ok"){_, _ ->
                    findNavController().navigate(R.id.homeFragment)
                }
                create()
                show()
            }
        }

        binding.btnNext.setOnClickListener{
            val toProfileFragment = HomeFragmentDirections.actionHomeFragmentToProfileFragment(
                binding.nameEditText.text.toString()
            )

            if (binding.nameEditText.text.toString().isBlank()) {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle("Name is Empty")
                    setMessage("Please Input your Name")
                    setPositiveButton("Ok"){_, _ ->
                        findNavController().navigate(R.id.homeFragment)
                    }
                    create()
                    show()
                }
            } else {
                findNavController().navigate(toProfileFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}