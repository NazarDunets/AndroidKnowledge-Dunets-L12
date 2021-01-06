package com.example.androidknowledgedunetsl12

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.afollestad.materialdialogs.MaterialDialog
import com.example.androidknowledgedunetsl12.databinding.ActivityMainFirstFrameBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainFirstFrameBinding
    private lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupBottomSheet()
        setupListeners()
    }

    private fun setupListeners() {
        binding.startButton.setOnClickListener {
            animateToKeyframeTwo()
        }
        binding.adButton.setOnClickListener {
            showSimpleAlertDialog()
        }
        binding.bsButton.setOnClickListener {
            showBottomSheet()
        }
        binding.adfButton.setOnClickListener {
            showFragmentAlertDialog()
        }
        binding.bsfButton.setOnClickListener {
            showModalBottomSheet()
        }
    }

    private fun showModalBottomSheet() {
        supportFragmentManager
            .beginTransaction()
            .add(MdBtSheetFragment.newInstance(12), "MBS")
            .commitAllowingStateLoss()
    }

    private fun setupBottomSheet() {
        val bs = binding.includedBS.colorsBottomSheet
        sheetBehavior = BottomSheetBehavior.from(bs)
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun showBottomSheet() {
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun showSimpleAlertDialog() {
        MaterialDialog(this).show {
            title(text = "Title")
            message(text = "Message")
            positiveButton(text = "OK") {
                it.dismiss()
            }
        }
    }

    private fun showFragmentAlertDialog() {
        val dialog = CustomDialogFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .add(dialog, "DF")
            .commitAllowingStateLoss()
    }

    private fun setupBinding() {
        binding = ActivityMainFirstFrameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun animateToKeyframeTwo() {
        val transition = ChangeBounds()
        transition.interpolator =
            AnticipateOvershootInterpolator(1.0f)

        val constraintSet = ConstraintSet()
        constraintSet.load(this, R.layout.activity_main_second_frame)
        TransitionManager.beginDelayedTransition(binding.root, transition)
        constraintSet.applyTo(binding.containerConstraint)
    }
}