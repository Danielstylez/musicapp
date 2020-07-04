package com.mobile.musicapp.splash.view

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobile.musicapp.R
import com.mobile.musicapp.home.view.HomeActivity
import kotlinx.android.synthetic.main.splash_activity.*

/**
 * Primera actividad de la aplicación. Muestra una imagen de splash por un determinado tiempo y luego
 * invoca la actividad Home.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showSplashScreen()
    }

    override fun onStop() {
        finish()
        super.onStop()
    }

    /**
     * Muestra la actividad Home
     */
    private fun showSplashScreen() {
        setContentView(R.layout.splash_activity)

        splashAnimation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }
        })
        splashAnimation.playAnimation()
    }
}