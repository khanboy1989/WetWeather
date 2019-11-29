package com.jayway.techtest.wetweather.ui


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.jayway.techtest.wetweather.R
import com.jayway.techtest.wetweather.util.extractCityNameShortCountryName
import com.jayway.techtest.wetweather.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_weather_list.view.*
import java.util.*


class WeatherListFragment : Fragment(),
    SearchView.OnQueryTextListener, View.OnFocusChangeListener {

    private lateinit var rootView: View
    private var TAG:String = WeatherListFragment::class.java.simpleName
    private lateinit var viewModel: ListViewModel


    //onCreateView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_weather_list, container, false)
        addSearchViewListeners()
        initializePlaces()
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.getCurrentWeather()
        return rootView
    }


    private fun addSearchViewListeners() {
        rootView.citySearchView.setOnQueryTextListener(this)
        rootView.citySearchView.setOnQueryTextFocusChangeListener(this)
    }

    override fun onFocusChange(view: View?, focus: Boolean) {
        when (view) {
            rootView.citySearchView -> {
                if(focus){
                    onSearchFocused()
                }else {
                    Log.d(TAG,"focused again")
                }
            }
        }
    }

    /**
     * onQueryTextChange in order to get submitted text from searchview
     * */
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            Log.d(TAG,query)

        }
        return false
    }

    /**
     * onQueryTextChange in order to get the text change of searchview
     * */
    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    /**
     * Initializes the places api for auto-filling the search of city
     * */
    private fun initializePlaces() {
        activity?.applicationContext?.let {
            if (!Places.isInitialized()) {
                Places.initialize(it, getString(R.string.api_key))
            }
        }
    }

    private fun onSearchFocused() {
        activity?.applicationContext?.let {
            //set the fields to specify which type of place data to return.
            val fields = listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.ADDRESS_COMPONENTS)
            //start the autocomplete intent.
            val intent =
                Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                    .setTypeFilter(TypeFilter.CITIES)
                    .build(it)
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            AUTOCOMPLETE_REQUEST_CODE -> {
                //capture the result of search
                handleSuccessSearchResult(data)
            }

            AutocompleteActivity.RESULT_ERROR -> {
                //capture if error occurred
                Toast.makeText(activity,getString(R.string.place_error),Toast.LENGTH_LONG).show()

            }

            AutocompleteActivity.RESULT_CANCELED -> {
                //capture if user canceled
            }
        }
    }

    override fun onResume() {
        super.onResume()
        rootView.citySearchView.clearFocus()
    }

    private fun handleSuccessSearchResult(data: Intent?) {
        if (data!=null && data.hasExtra(getString(R.string.hasSelectedPlace))) {
            val place = Autocomplete.getPlaceFromIntent(data)
            val countryCity = place.extractCityNameShortCountryName()
            rootView.citySearchView.setQuery(countryCity,false)
        } else {

        }
    }

    companion object {
        const val AUTOCOMPLETE_REQUEST_CODE = 22
    }
}
