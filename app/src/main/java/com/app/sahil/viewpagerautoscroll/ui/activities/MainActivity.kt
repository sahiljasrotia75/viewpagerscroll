package com.app.atsz7.viewpagerautoscroll.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.app.atsz7.viewpagerautoscroll.Constants
import com.app.atsz7.viewpagerautoscroll.R
import com.app.atsz7.viewpagerautoscroll.data.api.ApiService
import com.app.atsz7.viewpagerautoscroll.data.model.Data
import com.app.atsz7.viewpagerautoscroll.extensions.autoScroll
import com.app.atsz7.viewpagerautoscroll.ui.activities.viewmodel.MainViewModel
import com.app.atsz7.viewpagerautoscroll.ui.adapters.ViewPagerAdapter
import com.app.atsz7.viewpagerautoscroll.utils.GsonPConverterFactory
import com.app.atsz7.viewpagerautoscroll.utils.Status
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class MainActivity : AppCompatActivity() {
    private var dataSlide: List<Data>? = null
    private var countSize: Int=0
    private lateinit var adapter: ViewPagerAdapter
    val mViewModel by viewModel<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      //  myApi()
        setupObserver()

    }

    /**
     * This method is used to set the [adapter] with
     * [ViewPagerAdapter] and "images" list.
     */
    private fun setAdapter() {
        this.adapter = ViewPagerAdapter()

    }

    /**
     * This method set the [viewPager] with [adapter].
     */
    private fun setViewPager() {
        viewPager.adapter = adapter

        /**
         * Start automatic scrolling with an
         * interval of 3 seconds.
         */
        viewPager.autoScroll(3000,tvSize,countSize)
    }

    private fun setupObserver() {
        mViewModel.users?.observe(this, Observer {
            when(it.status){
                Status.LOADING -> mViewModel.loader.postValue(true)
                Status.ERROR -> {
                    mViewModel.loader.postValue(false)
                    progressBar.visibility=View.GONE
                    it.message?.let {msg->
                        Toast.makeText(this,msg.message,Toast.LENGTH_SHORT).show()
                    }?:Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS->{
                    mViewModel.loader.postValue(false)
                    it.data?.apply {

                        if(it.data.data.isNullOrEmpty()){
                            Toast.makeText(applicationContext,"empty list",Toast.LENGTH_SHORT).show()
                        }else{
                            dataSlide=it.data.data
                            countSize =  dataSlide!!.size
                            Log.d("ArraySize",countSize.toString())
                            setAdapter()
                            setViewPager()
                            adapter.addData(it.data.data)
                            adapter.notifyDataSetChanged()
                        }
                        Toast.makeText(applicationContext,"list fetched",Toast.LENGTH_SHORT).show()

                    }
                }
            }
        })
    }



    private fun myApi(){
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create()) //important
           // .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonPConverterFactory(gson))
            .build()

        val request: ApiService = retrofit.create(ApiService::class.java)
        val call: Call<JsonObject?>? = request.getJson()
        call?.enqueue(object : Callback<JsonObject?> {
            override fun onResponse(call: Call<JsonObject?>, response: Response<JsonObject?>) {
          //      progressDialog.dismiss()
                Log.e("response",response.toString())
                if (response.isSuccessful){

                }
              //  val s: String = java.lang.String.valueOf(response.get("username"))
             //   val user_array: JsonArray = response.getAsJsonArray("user_array")
                Toast.makeText(this@MainActivity, response.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
         //       progressDialog.dismiss()
                Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }


}

