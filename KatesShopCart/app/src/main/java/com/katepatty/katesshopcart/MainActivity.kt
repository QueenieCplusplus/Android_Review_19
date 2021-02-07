package com.katepatty.katesshopcart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import timber.log.Timber
import android.content.ActivityNotFoundException
import android.widget.Toast

import androidx.core.app.ShareCompat

import androidx.databinding.DataBindingUtil
import com.katepatty.katesshopcart.databinding.ActivityMainBinding



/** onSaveInstanceState Bundle Keys **/
val KEY_REVENUE = "revenue_key"
val KEY_DESSERT_SOLD = "dessert_sold_key"
val KEY_TIMER_SECONDS = "timer_seconds_key"


class MainActivity : AppCompatActivity() {

    // Text UI
    private var revenue = 0
    private var itemSold = 0


    // Lifecycle
    private lateinit var ktimer : KTimer;

    // Contains all the views
    private lateinit var binding: ActivityMainBinding



    // List<>
    private val allItems = listOf(

        Item(R.drawable.lollipop,55,1),
        Item(R.drawable.marchmello, 45,1),
        Item(R.drawable.oreo, 38, 1)

    )

    private val allItemFromZero = listOf(

        Item(R.drawable.lollipop,55,0),
        Item(R.drawable.marchmello, 45,0),
        Item(R.drawable.oreo, 38, 0)

    )


    private var nowItem = allItems[0]


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.i("onCreate() method called")


        // Use Data Binding to get reference to the views
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Interaction
        binding.dessertButton.setOnClickListener {
            onItemClicked()
        }

        // Bundle

        if (savedInstanceState != null) {

            revenue = savedInstanceState.getInt(KEY_REVENUE, 0)
            itemSold = savedInstanceState.getInt(KEY_DESSERT_SOLD, 0)

            ktimer.secondsCount =
                savedInstanceState.getInt(KEY_TIMER_SECONDS, 0)

            // Show the next Item
            showNowItem()
        }


        // UI

        binding.revenue = this.revenue
        binding.amountSold = this.itemSold
        binding.dessertButton.setImageResource(nowItem.imageId)
        binding.share.setOnClickListener {
            onShare()
        }
        binding.shuffle.text = "Click on me to Shuffle!"
        binding.imgBtn.setOnClickListener {
            onSuffle()
        }

    }


    private fun onItemClicked(){

        // Update the amount & price
        revenue += nowItem.price
        itemSold++

        // UI update
        binding.revenue = revenue
        binding.amountSold = itemSold


        // show Item now
        showNowItem()
    }

    private fun showNowItem(){

        // = allItems[0]
        // var neItem = allItems.shuffled()

        var newItem = allItems[0]

        for (indexItem in allItems) {

            if (itemSold >= indexItem.startProductionAmount) {
                newItem = indexItem

            }
            // The list of desserts is sorted by startProductionAmount.
            // As you sell more items,
            // you'll start producing more expensive item as determined by startProductionAmount
            // We know to break as soon as we see
            // a item who's "startProductionAmount" is greater than the amount sold.
            else break
        }

        // If the new dessert is actually different than the current dessert,
        // update the image & item show
        if (newItem != nowItem) {

            nowItem = newItem

            binding.dessertButton.setImageResource(newItem.imageId)
            binding.amountSold = newItem.startProductionAmount
            binding.revenue = newItem.price
        }

    }



    /**
     * Called when the user navigates away from the app but might come back
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.i("onSaveInstanceState Called")

        outState.putInt(KEY_REVENUE, revenue)
        outState.putInt(KEY_DESSERT_SOLD, itemSold)
        outState.putInt(KEY_TIMER_SECONDS, ktimer.secondsCount)
    }

    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
                .setText(getString(R.string.share_text, itemSold, revenue))
                .setType("text/plain")
                .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.sharing_not_available),
                    Toast.LENGTH_LONG).show()
        }
    }

    private fun onSuffle(){

        var newItem = allItemFromZero[0]


        // .first()
        newItem = allItemFromZero.shuffled().last()

        binding.dessertButton.setImageResource(newItem.imageId)
        binding.amountSold = newItem.startProductionAmount
        binding.revenue = newItem.price


    }



}