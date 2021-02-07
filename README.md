# Android_Review_19
Intent using ShareCompat to share info via Gmail/ SMS


1. code 


          import androidx.core.app.ShareCompat


          ShareIntent = ShareCompat.IntentBuilder.from(this)
                                   .setText(getString(R.string.share_text, itemSold, revenue))
                                   .setType("text/plain")
                                   .intent
