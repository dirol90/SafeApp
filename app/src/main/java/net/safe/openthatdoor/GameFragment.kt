package net.safe.openthatdoor

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.game_fragment.*
import java.util.*


class GameFragment : Fragment() {

    companion object {
        fun newInstance() = GameFragment()
    }

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_fragment, container, false)
    }

    private lateinit var tvCode: TextView
    private lateinit var ivKeyad: ImageView
    private lateinit var ivTry: ImageView

    var tries = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startBannerAnimation()
        tvCode = view.findViewById<TextView>(R.id.tv_code)!!
        ivKeyad = view.findViewById<ImageView>(R.id.iv_keyad)!!
        ivTry = view.findViewById<ImageView>(R.id.iv_try)!!
        ivKeyad.setOnClickListener {
            if (tvCode.text.length < 4 || tvCode.text.equals("0 0 0 0")){
                if (tvCode.text.equals("0 0 0 0")){tvCode.text = ""}
                tvCode.text = tvCode.text.toString() + (Math.random()*9).toInt().toString()
            }
            if (tvCode.text.length == 4 ){
                tvCode.text = ""
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
                if (tries == 0){
                    tries++
                    ivTry.setImageDrawable(resources.getDrawable(R.drawable.try_2))
                } else if (tries == 1){
                    tries++
                    ivTry.setImageDrawable(resources.getDrawable(R.drawable.try_1))
                } else {
                    ivTry.visibility= View.GONE
                    ivKeyad.visibility= View.GONE
                    img_1.visibility= View.GONE
                    img_2.visibility= View.GONE
                    img_3.visibility= View.GONE

                    img_4.visibility= View.VISIBLE
                    img_5.visibility= View.VISIBLE
                    btn_claim.visibility= View.VISIBLE

                }
            }
        }

        btn_claim.setOnClickListener {
            val termsIntent = Intent(
                    Intent.ACTION_VIEW,
            Uri.parse("https://www.google.com/")
            )
            startActivity(termsIntent)
        }
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
}