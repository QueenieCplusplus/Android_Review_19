# Android_Review_19
Intent using ShareCompat to share info via Gmail/ SMS


1. code 


          import androidx.core.app.ShareCompat


          ShareIntent = ShareCompat.IntentBuilder.from(this)
                                   .setText(getString(R.string.share_text, itemSold, revenue))
                                   .setType("text/plain")
                                   .intent


2. output

![](https://raw.githubusercontent.com/QueenieCplusplus/Android_Review_19/main/2.png)

![](https://raw.githubusercontent.com/QueenieCplusplus/Android_Review_19/main/3.png)

![](https://raw.githubusercontent.com/QueenieCplusplus/Android_Review_19/main/4.png)

![](https://raw.githubusercontent.com/QueenieCplusplus/Android_Review_19/main/5.png)
