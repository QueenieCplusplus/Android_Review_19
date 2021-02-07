package com.katepatty.katesshopcart

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

/**
 *
 * Handler and Runnable deal with threading.
 * https://developer.android.com/guide/components/processes-and-threads
 *
 */
class KTimer(lifecycle: Lifecycle) : LifecycleObserver {

    // The number of seconds counted since the timer started
    var secondsCount = 0

    /**
     * [Handler] is a class meant to process a queue of messages (known as [android.os.Message]s)
    actions (known as [Runnable]s)
     */
    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable


    init {
        // Add this as a lifecycle Observer, which allows for the class to react to changes in this
        // activity's lifecycle state.
        lifecycle.addObserver(this)
    }



    @OnLifecycleEvent(Lifecycle.Event.ON_START)

    fun startTimer() {
        // Create the runnable action, which prints out a log and increments the seconds counter
        runnable = Runnable {
            secondsCount++
            Timber.i("Timer is at : $secondsCount")
            // postDelayed re-adds the action to the queue of actions the Handler is cycling
            // through. The delayMillis param tells the handler to run the runnable in
            // 1 second (1000ms)
            handler.postDelayed(runnable, 1000)
        }

        // This is what initially starts the timer
        handler.postDelayed(runnable, 1000)

        // Note that the Thread the handler runs on is determined by a class called Looper.
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)

    fun stopTimer() {
        // Removes all pending posts of runnable from the handler's queue, effectively stopping the
        // timer
        handler.removeCallbacks(runnable)
    }
}
