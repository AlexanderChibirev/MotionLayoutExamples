package com.example.swipecards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelCard : ViewModel() {

    val color: LiveData<Int> = MutableLiveData<Int>()
}