package com.example.imgurimagesearch.ui.imageactivity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.imgurimagesearch.R
import com.example.imgurimagesearch.data.network.networkmodel.ImageDataResponse
import com.example.imgurimagesearch.ui.base.BaseActivity
import com.example.imgurimagesearch.util.Constants
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detailed_image.*
import java.util.*

/*
    Detailed Image Activity is launched when use clicks on a particular image

 */
class DetailImageActivity : BaseActivity<DetailImageActivityViewModel>() {


    private var imageDetails : ImageDataResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_image)
        initViews()
    }

    private fun initViews() {


        imageDetails = intent.getSerializableExtra(Constants.IMAGE_DETAILS) as ImageDataResponse

        Objects.requireNonNull(supportActionBar)!!.title = imageDetails!!.title
        Objects.requireNonNull(supportActionBar)!!.setDefaultDisplayHomeAsUpEnabled(true)


        loadImage()

        btn_submit.setOnClickListener { onSubmitClick() }

        loadComment()
    }


    /*
    Method used to load image using picasso library.
    firstly it checks images from cache then tries to download from network
    proper error image is shown in case of failure
     */

    private fun loadImage() {

        val context = imgur_detailed_view.context
        val url = imageDetails!!.link
        Picasso.with(context)
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imgur_detailed_view, object : Callback {
                    override fun onSuccess() { //..image loaded from cache
                    }

                    override fun onError() { //Try again online if cache failed
                        Picasso.with(context)
                                .load(url)
                                .error(R.drawable.ic_error_red_24dp)
                                .into(imgur_detailed_view, object : Callback {
                                    override fun onSuccess() { }
                                    override fun onError() {}
                                })
                    }
                })
    }


    private fun onSubmitClick() {

        val comment = comment_editText.text.toString().trim()
        val lastComment = comment_textView.text.toString()
        viewModel.addComment(imageDetails!!.id,comment,lastComment)

    }

    private fun loadComment() {

        viewModel.fetchComment(imageDetails!!.id)!!.observe(this, Observer { commentsEntity ->
            if (commentsEntity != null) {
                val comment = commentsEntity.comment
                if (comment != null) comment_textView.text = commentsEntity.comment
            }
        })
    }

    override fun createViewModel(): DetailImageActivityViewModel {
        return ViewModelProviders.of(this,DetailImageActivityViewModelFactory(application)).get(DetailImageActivityViewModel::class.java)
    }
}
