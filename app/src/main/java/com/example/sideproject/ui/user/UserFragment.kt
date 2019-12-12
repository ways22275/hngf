package com.example.sideproject.ui.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.sideproject.R
import com.example.sideproject.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {

    companion object {
        const val USER_INFO_BUNDLE_KEY = "USER_INFO_BUNDLE_KEY"
    }

    private lateinit var userViewModel: UserViewModel
    private lateinit var mContext : Context

    // TODO Add BaseFragment
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        logout.setOnClickListener {
            val builder = AlertDialog.Builder(mContext).apply {
                setTitle(getString(R.string.confirm_logout))
                setPositiveButton(getString(R.string.confirm)) { _, _ ->
                    userViewModel.logout()
                    startActivity(
                        Intent(context, LoginActivity::class.java)
                    )
                    requireActivity().finish()
                }
                setNegativeButton(getString(R.string.cancel)) { _, _ ->}
            }

            val dialog = builder.create()
            dialog.show()
        }

        val navController = findNavController(this)
        edit.setOnClickListener {
            val bundle = bundleOf(USER_INFO_BUNDLE_KEY to user_name.text)
            navController.navigate(R.id.action_userFragment_to_userEditFragment, bundle)
        }

        userViewModel = ViewModelProviders.of(this, UserViewModelFactory())
            .get(UserViewModel::class.java)

        userViewModel.userInfo.observe(this, Observer {
            val userInfo = it ?: return@Observer
            user_name.text = if (userInfo.name.isNullOrEmpty()) getString(R.string.name_undefined) else userInfo.name
            user_email.text = userInfo.email
            user_gender.text = userInfo.gender ?: getString(R.string.gender_undefined)
            user_isVIP.text = if (userInfo.isVIP) getString(R.string.isVip) else getString(R.string.isNotVip)
        })

        userViewModel.getUser()
    }
}