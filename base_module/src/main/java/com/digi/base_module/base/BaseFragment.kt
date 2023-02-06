package com.digi.base_module.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.digi.base_module.extensions.showAlertDialog
import com.digi.base_module.listeners.NetWorkListener
import com.digi.base_module.navigation.NavigationCommand
import com.digi.base_module.utils.NetWorkUtil
import com.digi.base_module.utils.observeNonNull

abstract class BaseFragment<BINDING : ViewDataBinding, VM : BaseViewModel>() : Fragment() {

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected abstract val viewModel: VM

    protected lateinit var binding: BINDING

    protected abstract fun onReady(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            NetWorkUtil.initialize(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            layoutId,
            container,
            false
        )

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavigation()
        onReady(savedInstanceState)
        onNetworkChange()
    }
    private val netWorkListener = object : NetWorkListener {
        override fun onConnectionAvailable() {
            onNetworkAvailable()
        }

        override fun onConnectionLost() {
            onNetworkLost()
            context?.showAlertDialog(
                "Internet Not Available",
                { dialog, _ ->
                    dialog.dismiss()
                },
                "OK",
                null,
                null,
                true
                )
        }
    }

    private fun onNetworkChange() {
        NetWorkUtil.onNetWorkChange(netWorkListener)
    }

    abstract fun onNetworkAvailable()
    abstract fun onNetworkLost()

    private fun observeNavigation() {
        viewModel.navigation.observeNonNull(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { navigationCommand ->
                handleNavigation(navigationCommand)
            }
        }
    }

    private fun handleNavigation(navCommand: NavigationCommand) {
        when (navCommand) {
            is NavigationCommand.ToDirection -> findNavController().navigate(navCommand.directions)
            is NavigationCommand.Back -> findNavController().navigateUp()
        }
    }

}