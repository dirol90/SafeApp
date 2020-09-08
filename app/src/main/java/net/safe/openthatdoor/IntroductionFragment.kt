
package net.safe.openthatdoor

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


class IntroductionFragment : Fragment() {

    companion object {
        fun newInstance() = IntroductionFragment()
    }

    private lateinit var viewModel: IntroductionViewModel
    private lateinit var wv : WebView
    private lateinit var btn : Button
    private lateinit var pgb : ProgressBar
    private lateinit var tvp : TextView
    private lateinit var tvi : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.introduction_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        wv = view.findViewById<WebView>(R.id.vw_player)!!
        btn = view.findViewById<Button>(R.id.btn_click)!!
        pgb = view.findViewById<ProgressBar>(R.id.pgb)!!
        tvp = view.findViewById<TextView>(R.id.tv_priv)!!
        tvi  = view.findViewById<TextView>(R.id.tv_tos)!!

        prepareBtns()
        prepareVideo()

        super.onViewCreated(view, savedInstanceState)
    }


    fun prepareBtns(){
        tvp.setOnClickListener {
            val privacyIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://rewards.im/game/privacy.html")
            )
            startActivity(privacyIntent)
        }
        tvi.setOnClickListener {
            val termsIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://rewards.im/game/terms.html")
            )
            startActivity(termsIntent)
        }
        btn.setOnClickListener {
            val fragmentManager = fragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.add(R.id.fragment_placeholder, GameFragment(), "gameFragment")
            fragmentTransaction.commit()
        }
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
                pgb.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        }
        wv.loadUrl("https://rewards.im/app/cl1")
    }

}