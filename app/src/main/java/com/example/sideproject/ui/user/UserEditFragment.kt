package com.example.sideproject.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.sideproject.R
import com.example.sideproject.ui.login.afterTextChanged
import kotlinx.android.synthetic.main.fragment_user_edit.*

class UserEditFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProviders.of(this, UserViewModelFactory())
            .get(UserViewModel::class.java)

        userViewModel.userFormState.observe(this, Observer {
            val state = it ?: return@Observer
            edit_submit.isEnabled = state.isDataValid
        })

        userViewModel.userEditResult.observe(this, Observer {
            val state = it ?: return@Observer
            if (!state.result) {
                Toast.makeText(activity, state.errorMsg, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, getString(R.string.edit_user_success), Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            }
        })

        val safeArgs: UserEditFragmentArgs by navArgs()
        val userInfo = safeArgs.userInfo
        edit_name.setText(userInfo.name)

        edit_name.afterTextChanged {
            userViewModel.userInfoChanged(edit_name.text.toString())
        }

        edit_submit.setOnClickListener {
            userInfo.name = edit_name.text.toString()
            userViewModel.putUser(userInfo)
        }
    }
}