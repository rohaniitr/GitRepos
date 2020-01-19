package com.appstreet.myapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.appstreet.myapplication.R
import kotlinx.android.synthetic.main.fragment_base_layout.*

abstract class BaseFragment : Fragment() {
    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = layoutInflater.inflate(R.layout.fragment_base_layout, null)
        val activityContainer = rootView.findViewById(R.id.content_view) as RelativeLayout;
        layoutInflater.inflate(getLayoutId(), activityContainer, true)

        return rootView
    }

    fun showProgressView() {
        if (!isAdded) {
            return
        }
        content_view.visibility = View.GONE
        progress_view.visibility = View.VISIBLE
        server_error_view.visibility = View.GONE
        network_error_view.visibility = View.GONE
    }

    fun showContentView() {
        if (!isAdded) {
            return
        }
        content_view.visibility = View.VISIBLE;
        progress_view.visibility = View.GONE;
        server_error_view.visibility = View.GONE
        network_error_view.visibility = View.GONE
    }

    fun showNetworkErrorView(@Nullable clickListener: View.OnClickListener) {
        if (!isAdded) {
            return
        }
        content_view.visibility = View.GONE;
        progress_view.visibility = View.GONE;
        server_error_view.visibility = View.GONE
        network_error_view.visibility = View.VISIBLE

        network_error_view.findViewById<View>(R.id.retry_btn).setOnClickListener(clickListener)
    }

    fun showServerErrorView(@Nullable clickListener: View.OnClickListener?) {
        if (!isAdded) {
            return
        }
        content_view.visibility = View.GONE;
        progress_view.visibility = View.GONE;
        server_error_view.visibility = View.VISIBLE
        network_error_view.visibility = View.GONE

        server_error_view.findViewById<View>(R.id.retry_btn).setOnClickListener(clickListener)
    }
}