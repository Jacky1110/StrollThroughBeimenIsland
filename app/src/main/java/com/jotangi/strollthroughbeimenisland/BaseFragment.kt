package com.jotangi.strollthroughbeimenisland

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jotangi.strollthroughbeimenisland.databinding.AppBarMainBinding
import com.jotangi.strollthroughbeimenisland.utility.AppUtility

abstract class BaseFragment : Fragment() {

    abstract fun getToolBar(): AppBarMainBinding?

    val TAG: String = "(TAG)${javaClass.simpleName}"

    var mActivity: MainActivity? = null
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        try {

            mActivity = (activity as MainActivity)

        } catch (e: Exception) {

            e.printStackTrace()

        }
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    private fun setupToolBarBtn(
        imageButton: ImageButton?,
        res: Int?,
        onClick: () -> Unit
    ) {
        imageButton?.apply {

            res?.let {
                setImageResource(it)
            }

            setOnClickListener {
                onClick()
            }
        }
    }

    fun setupLoginTitle() {
        getToolBar()?.apply {
            toolTitleTextView.text = getString(R.string.menu_login)
            toolBackImageButton.visibility = View.VISIBLE

            setupToolBarBtn(
                toolBackImageButton,
                R.drawable.ic_baseline_chevron_left
            ) {
                onBackPressed()
            }
        }
    }

    fun setupMemberDataTitle() {
        getToolBar()?.apply {
            toolTitleTextView.text = getString(R.string.menu_member)
            toolBackImageButton.visibility = View.VISIBLE

            setupToolBarBtn(
                toolBackImageButton,
                R.drawable.ic_baseline_chevron_left
            ) {
                onBackPressed()
            }
        }
    }

    fun setupTermsUseTitle() {
        getToolBar()?.apply {
            toolTitleTextView.text = getString(R.string.menu_terms)
            toolBackImageButton.visibility = View.VISIBLE

            setupToolBarBtn(
                toolBackImageButton,
                R.drawable.ic_baseline_chevron_left
            ) {
                onBackPressed()
            }
        }
    }

    fun setupQATitle() {
        getToolBar()?.apply {
            toolTitleTextView.text = getString(R.string.menu_question)
            toolBackImageButton.visibility = View.VISIBLE

            setupToolBarBtn(
                toolBackImageButton,
                R.drawable.ic_baseline_chevron_left
            ) {
                onBackPressed()
            }
        }
    }

    fun setupForgetPasswordTitle() {
        getToolBar()?.apply {
            toolTitleTextView.text = getString(R.string.menu_forgetPassword)
            toolBackImageButton.visibility = View.VISIBLE

            setupToolBarBtn(
                toolBackImageButton,
                R.drawable.ic_baseline_chevron_left
            ) {
                onBackPressed()
            }
        }
    }


    fun setupSignupTitle() {
        getToolBar()?.apply {
            toolTitleTextView.text = getString(R.string.menu_register)
            toolBackImageButton.visibility = View.VISIBLE

            setupToolBarBtn(
                toolBackImageButton,
                R.drawable.ic_baseline_chevron_left
            ) {
                onBackPressed()
            }
        }
    }

    fun setupCouponListTitle() {
        getToolBar()?.apply {
            toolTitleTextView.text = getString(R.string.menu_coupon)
            toolBackImageButton.visibility = View.VISIBLE

            setupToolBarBtn(
                toolBackImageButton,
                R.drawable.ic_baseline_chevron_left
            ) {
                onBackPressed()
            }
        }
    }

    fun setupStopCarTitle() {
        getToolBar()?.apply {
            toolTitleTextView.text = getString(R.string.menu_parking)
            toolBackImageButton.visibility = View.VISIBLE

            setupToolBarBtn(
                toolBackImageButton,
                R.drawable.ic_baseline_chevron_left
            ) {
                onBackPressed()
            }
        }
    }

    fun setupNavigationShopTitle() {
        getToolBar()?.apply {
            toolTitleTextView.text = getString(R.string.menu_digital)
            toolBackImageButton.visibility = View.VISIBLE

            setupToolBarBtn(
                toolBackImageButton,
                R.drawable.ic_baseline_chevron_left
            ) {
                onBackPressed()
            }
        }
    }

    fun setupNavigationViewTitle() {
        getToolBar()?.apply {
            toolTitleTextView.text = getString(R.string.menu_digital)
            toolBackImageButton.visibility = View.VISIBLE

            setupToolBarBtn(
                toolBackImageButton,
                R.drawable.ic_baseline_chevron_left
            ) {
                onBackPressed()
            }
        }
    }

    fun setupGoodFoodListTitle() {
        getToolBar()?.apply {
            toolTitleTextView.text = getString(R.string.menu_goodFood)
            toolBackImageButton.visibility = View.VISIBLE

            setupToolBarBtn(
                toolBackImageButton,
                R.drawable.ic_baseline_chevron_left
            ) {
                onBackPressed()
            }
        }
    }

    fun setupGoodFoodListViewTitle() {
        getToolBar()?.apply {
            toolTitleTextView.text = getString(R.string.menu_goodFood)
            toolBackImageButton.visibility = View.VISIBLE

            setupToolBarBtn(
                toolBackImageButton,
                R.drawable.ic_baseline_chevron_left
            ) {
                onBackPressed()
            }
        }
    }


    fun setupStoreIntroductionListTitle() {
        getToolBar()?.apply {
            toolTitleTextView.text = getString(R.string.menu_introduction)
            toolBackImageButton.visibility = View.VISIBLE

            setupToolBarBtn(
                toolBackImageButton,
                R.drawable.ic_baseline_chevron_left
            ) {
                onBackPressed()
            }
        }
    }

    fun setupStoreIntroductionListViewTitle() {
        getToolBar()?.apply {
            toolTitleTextView.text = getString(R.string.menu_introduction)
            toolBackImageButton.visibility = View.VISIBLE

            setupToolBarBtn(
                toolBackImageButton,
                R.drawable.ic_baseline_chevron_left
            ) {
                onBackPressed()
            }
        }
    }

    fun setupScanCouponTitle() {
        getToolBar()?.apply {
            toolTitleTextView.text = getString(R.string.menu_manager)
            toolBackImageButton.visibility = View.VISIBLE

            setupToolBarBtn(
                toolBackImageButton,
                R.drawable.ic_baseline_chevron_left
            ) {
                onBackPressed()
            }
        }
    }

    fun setupBeimenManagerTitle() {
        getToolBar()?.apply {
            toolTitleTextView.text = getString(R.string.menu_manager)
            toolBackImageButton.visibility = View.GONE

            setupToolBarBtn(
                toolBackImageButton,
                R.drawable.ic_baseline_chevron_left
            ) {
                onBackPressed()
            }
        }
    }

    open fun onBackPressed() {
        val navigationController = findNavController()
        if (navigationController.currentDestination?.id == R.id.action_to_main) {
            requireActivity().finish()
        } else {
            mActivity?.onBackPressed()
        }
    }
}