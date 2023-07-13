package dev.thorcode.mypalindromeapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dev.thorcode.mypalindromeapplication.adapter.LoadingStateAdapter
import dev.thorcode.mypalindromeapplication.adapter.UserListAdapter
import dev.thorcode.mypalindromeapplication.databinding.FragmentListUserNameBinding
import dev.thorcode.mypalindromeapplication.model.MainViewModel
import dev.thorcode.mypalindromeapplication.model.ViewModelFactory
import kotlinx.coroutines.launch

class ListUserNameFragment : Fragment() {
    private var _binding: FragmentListUserNameBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(requireContext())
    }
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListUserNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle

        binding.materialToolbar2.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val layoutManager =  LinearLayoutManager(requireContext())
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        swipeRefreshLayout = binding.swipeRefreshLayout

        swipeRefreshLayout.setOnRefreshListener {
            getData()
        }

        getData()
    }

    private fun getData() {
        val adapter = UserListAdapter(::onSelectedItem)
        lifecycleScope.launch {
            adapter.loadStateFlow.collect{
                val loadState = it.refresh
                binding.progressBar.isVisible = loadState is LoadState.Error
                if(loadState is LoadState.Error){
                    Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT)
                        .show()
                }
                swipeRefreshLayout.isRefreshing = loadState is LoadState.Loading
            }
        }
        binding.rvUsers.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        viewModel.user.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun onSelectedItem(item: String){
        savedStateHandle[ON_SELECTED_ITEM] = item
        findNavController().navigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ON_SELECTED_ITEM = "on_selected_item"
    }
}