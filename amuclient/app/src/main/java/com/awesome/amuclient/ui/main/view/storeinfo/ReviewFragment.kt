package com.awesome.amuclient.ui.main.view.storeinfo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.Constants
import com.awesome.amuclient.data.api.response.ClientResponse
import com.awesome.amuclient.data.api.response.ReviewListResponse
import com.awesome.amuclient.data.api.service.GetClientService
import com.awesome.amuclient.data.api.service.GetReviewListService
import com.awesome.amuclient.data.model.*
import com.awesome.amuclient.ui.main.adapter.MenuAdapter
import com.awesome.amuclient.ui.main.adapter.ReviewAdapter
import com.awesome.amuclient.ui.main.viewmodel.*
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_review.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReviewFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var reviewAdapter: ReviewAdapter? = null
    private var storeId: String? = ""
    private var clientId : String? = ""

    private lateinit var reviewViewModel : ReviewViewModel
    private lateinit var firebaseViewModel : FirebaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_review, container, false)

        auth = FirebaseAuth.getInstance()
        storeId = arguments?.getString("store_id")

        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        clientId = firebaseViewModel.getUid()
        var factory = ReviewViewModelFactory(clientId.toString())
        reviewViewModel = ViewModelProvider(this, factory).get(ReviewViewModel::class.java)

        reviewViewModel.getReview(storeId.toString())


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reviewViewModel.reviewLists.observe(viewLifecycleOwner, Observer<ArrayList<ReviewList>> {reviewLists ->
            if (reviewAdapter == null) {
                reviewAdapter = ReviewAdapter(arrayListOf() , Glide.with(this))
                review_list.adapter = reviewAdapter
            }
            reviewAdapter!!.update(reviewLists)
        })
    }
}