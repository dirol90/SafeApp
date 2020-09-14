package net.safe.openthatdoor

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Adapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.game_fragment.*
import java.util.*
import kotlin.collections.ArrayList


class GameFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_fragment, container, false)
    }

    private lateinit var tvCode: EditText
    private lateinit var ivKeyad: ImageView
    private lateinit var ivTry: ImageView
    private lateinit var wv : WebView

    var tries = 0

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        startBannerAnimation()
        wv = view.findViewById<WebView>(R.id.vw_player)!!
        tvCode = view.findViewById<EditText>(R.id.tv_code)!!
        ivKeyad = view.findViewById<ImageView>(R.id.iv_keyad)!!
        ivTry = view.findViewById<ImageView>(R.id.iv_try)!!
        tvCode.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (tvCode.text.length == 4) {
                    tvCode.editableText.clear()
                    var iv = view?.findViewById<ImageView>(R.id.img_3)!!
                    val rotate = RotateAnimation(
                        0f, 360f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f
                    )

                    rotate.duration = 900
                    rotate.repeatCount = Animation.INFINITE
                    iv.startAnimation(rotate)

                    val handler = Handler()
                    val timer = Timer()
                    val task = object : TimerTask() {
                        override fun run() {
                            handler.post {
                                iv.clearAnimation()
                            }
                        }
                    }
                    timer.schedule(task, 1000) //Every 1 second
                    if (tries == 0) {
                        tries++
                        ivTry.setImageDrawable(resources.getDrawable(R.drawable.try_2))
                    } else if (tries == 1) {
                        tries++
                        ivTry.setImageDrawable(resources.getDrawable(R.drawable.try_1))
                    } else {
                        ivTry.visibility = View.GONE
                        ivKeyad.visibility = View.GONE
                        img_1.visibility = View.GONE
                        img_2.visibility = View.GONE
                        img_3.visibility = View.GONE
                        tv_code.visibility = View.GONE

                        img_4.visibility = View.VISIBLE
//                        img_5.visibility = View.VISIBLE
                        btn_claim.visibility = View.VISIBLE
                        wv.visibility = View.VISIBLE
                        prepareVideo();
                    }
                }
            }
        })
        ivKeyad.setOnClickListener {
            tvCode.requestFocus()

            Handler().postDelayed({
                tvCode.dispatchTouchEvent(
                    MotionEvent.obtain(
                        SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_DOWN,
                        0f,
                        0f,
                        0
                    )
                )
                tvCode.dispatchTouchEvent(
                    MotionEvent.obtain(
                        SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_UP,
                        0f,
                        0f,
                        0
                    )
                )
            }, 200)
        }

        btn_claim.setOnClickListener {
            val termsIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://rewards.im/app/cl3")
            )
            startActivity(termsIntent)
        }

        rv.layoutManager = LinearLayoutManager(view.context, LinearLayout.VERTICAL, false)
        rv.adapter = MyAdapter(arrayListOf(
            AdapterClass("What does it cost to play this game?\n", "Nothing, it’s 100% FREE. No purchase is necessary."),
            AdapterClass("Am I eligible to participate?\n", "You must be 18 years old or above and live in the USA or Canada. All other countries will have their rewards void.\n"),
                    AdapterClass("What can I win?\n", "The prizes and rewards vary depending on availability in your region, but all rewards are 100% free.\n"),
                AdapterClass("What are my chances of cracking the vault?\n", "There is a 1 in 10,000 chance you will guess the code on each attempt. The number of codes that will unlock the vault may increase which will increase your chances. Also, don’t forget to look out for secret hints and codes\n"),
        AdapterClass("How can you give away free rewards?\n", "We work closely with various advertisers and they usually will sponsor the reward. Depending on the product we may receive either a referral fee or we will get paid for just showing their advertisement.\n"),
        AdapterClass("How often can I play?\n", "You can only try the game when you click through the ad and maximum once per day. Any abuse will not be tolerated and rewards will be void.\n"),
            AdapterClass("Must I play today or will this be around for a while?\n", "We will continue to run this game as long as we have advertisers. The game will go offline if there are no rewards to be unlocked.\n"),
            AdapterClass("Can you give me some examples of what I can unlock?\n", "The rewards will vary but you could, for example, find a random USD amount from UserTestingClub.com, a discount voucher from TravelReward.net."),
            AdapterClass("Are the celebrities on the website getting paid to promote you?\n", "Yes, the actors are paid.\n"),
            ), view.context)
    }

    var ivState = 0
    fun startBannerAnimation(){
        var iv = view?.findViewById<ImageView>(R.id.iv_banner)!!
        val handler = Handler()
        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                handler.post {
                    if (ivState == 0 ) {
                        iv.setImageDrawable(resources.getDrawable(R.drawable.banner))
                        ivState = 1
                    } else {
                        iv.setImageDrawable(resources.getDrawable(R.drawable.banner_disable))
                        ivState = 0
                    }
                }
            }
        }
        timer.schedule(task, 0, 300) //Every 1 second

    }

    fun prepareVideo(){
        wv.settings.javaScriptEnabled = true
        wv.settings.domStorageEnabled = true
        wv.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                pgb_game.visibility = View.GONE
                Handler().postDelayed({
                    view!!.dispatchTouchEvent(
                        MotionEvent.obtain(
                            SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis(),
                            MotionEvent.ACTION_DOWN,
                            0f,
                            0f,
                            0
                        )
                    )
                    view!!.dispatchTouchEvent(
                        MotionEvent.obtain(
                            SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis(),
                            MotionEvent.ACTION_UP,
                            0f,
                            0f,
                            0
                        )
                    )
                }, 200)
                super.onPageFinished(view, url)
            }
        }
        wv.loadUrl(" https://rewards.im/app/cl2")
    }
}