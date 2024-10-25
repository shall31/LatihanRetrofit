package com.dicoding.picodiploma.latihanretrofit.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.latihanretrofit.R
import com.dicoding.picodiploma.latihanretrofit.ui.main.UsersAdapter


class FollowersFragment : Fragment() {

    private var position: Int = 0
    private var username: String = ""

    private lateinit var adapter: UsersAdapter
    lateinit var rvFollowers: RecyclerView
    lateinit var progressBarFragment: ProgressBar

    //followersviemodel
    private lateinit var followViewModel: FollowViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarFragment = view.findViewById(R.id.fragment_progressBar)
        rvFollowers = view.findViewById(R.id.rvFollowers)
        followViewModel = ViewModelProvider(requireActivity()).get(FollowViewModel::class.java)
        showLoading(isLoading = true)

        arguments?.let {
            position = it.getInt(ARG_POSITION, 0)
            username = it.getString(ARG_USERNAME, "")                                     //mengisi variabel username dengan isi dari ARG_USERNAME (yg berisi username dari SectionsPagerAdapter
        }

        followViewModel.setListFollowers(username)
        followViewModel.setListFollowings(username)

        if(position == 1){
            followViewModel.getListFollowers().observe(viewLifecycleOwner){
                showLoading(isLoading = false)
                adapter = UsersAdapter(it)
                Log.d("masuk", "masuk")
                rvFollowers.adapter = adapter
            }

        }else{
            followViewModel.getListFollowings().observe(viewLifecycleOwner){
                showLoading(isLoading = false)
                adapter = UsersAdapter(it)
                rvFollowers.adapter = adapter

            }

        }
        rvFollowers.layoutManager = LinearLayoutManager(requireActivity())

    }

    //loading
    private fun showLoading(isLoading: Boolean) {
        progressBarFragment.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    companion object {
        const val ARG_POSITION = "ARG_POSITION"
        const val ARG_USERNAME = "ARG_USERNAME"
    }
}
