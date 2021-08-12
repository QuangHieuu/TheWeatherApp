package test.dn.weather.utils.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import test.dn.weather.R
import test.dn.weather.constant.Constants.EXTRA_ARGS
import kotlin.reflect.KClass

/**
 * Various extension functions for AppCompatActivity.
 */

enum class AnimateType {
    FADE,
    SLIDE_TO_RIGHT,
    SLIDE_TO_LEFT,
    BOTTOM_UP,
    NO_ANIMATION
}

fun <T : Activity> AppCompatActivity.goTo(
    cls: KClass<T>, bundle: Bundle? = null,
    parcel: Parcelable? = null
) {
    intent = Intent(this, cls.java)
    if (bundle != null) intent.putExtra(EXTRA_ARGS, bundle)
    if (parcel != null) intent.putExtra(EXTRA_ARGS, parcel)
    startActivity(intent)
}


fun AppCompatActivity.replaceFragmentInActivity(
    @IdRes containerId: Int, fragment: Fragment,
    addToBackStack: Boolean = true, tag: String = fragment::class.java.simpleName,
    animateType: AnimateType = AnimateType.FADE
) {
    supportFragmentManager.transact({
        if (addToBackStack) {
            addToBackStack(tag)
        }
        replace(containerId, fragment, tag)
    }, animateType = animateType)
}

fun AppCompatActivity.clearAllFragment() {
    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}

fun AppCompatActivity.addFragmentToActivity(
    @IdRes containerId: Int, fragment: Fragment,
    addToBackStack: Boolean = true, tag: String = fragment::class.java.simpleName,
    animateType: AnimateType = AnimateType.FADE
) {
    supportFragmentManager.transact({
        if (addToBackStack) {
            addToBackStack(tag)
        }
        add(containerId, fragment, tag)
    }, animateType = animateType)
}

fun AppCompatActivity.addFragment(
    @IdRes containerResId: Int, fragment: Fragment,
    fragmentManager: FragmentManager,
    addToBackStack: Boolean = true,
    tag: String = fragment::class.java.simpleName,
    animateType: AnimateType = AnimateType.FADE
) {
    fragmentManager.transact({
        if (addToBackStack) {
            addToBackStack(tag)
        }
        add(containerResId, fragment, tag)
    }, animateType = animateType)
}


fun AppCompatActivity.currentFragment(containerId: Int): Fragment? {
    return supportFragmentManager.findFragmentById(containerId)
}

fun AppCompatActivity.goBackFragment(): Boolean {
    val isShowPreviousPage = supportFragmentManager.backStackEntryCount > 0
    if (isShowPreviousPage) {
        supportFragmentManager.popBackStackImmediate()
    }
    return isShowPreviousPage
}

fun AppCompatActivity.startActivity(
    @NonNull intent: Intent,
    flags: Int? = null
) {
    flags.notNull {
        intent.flags = it
    }
    startActivity(intent)
}

fun AppCompatActivity.startActivityForResult(
    @NonNull intent: Intent,
    requestCode: Int, flags: Int? = null
) {
    flags.notNull {
        intent.flags = it
    }
    startActivityForResult(intent, requestCode)
}

fun AppCompatActivity.isExistFragment(fragment: Fragment): Boolean {
    return supportFragmentManager.findFragmentByTag(fragment::class.java.simpleName) != null
}

fun AppCompatActivity.switchFragment(
    @IdRes containerId: Int, currentFragment: Fragment,
    newFragment: Fragment, addToBackStack: Boolean = true,
    tag: String = newFragment::class.java.simpleName,
    animateType: AnimateType = AnimateType.FADE
) {
    supportFragmentManager.transact(
        {
            setCustomAnimations(
                R.anim.fade_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.fade_out
            )
            if (isExistFragment(newFragment)) {
                hide(currentFragment).show(newFragment)
            } else {
                hide(currentFragment)
                if (addToBackStack) {
                    addToBackStack(tag)
                }
                add(containerId, newFragment, tag)
            }
        }, animateType = animateType
    )
}

fun AppCompatActivity.showDialogFragment(
    fragment: DialogFragment,
    tag: String = fragment::class.java.simpleName
) {
    val transaction = supportFragmentManager.beginTransaction()
    fragment.show(transaction, tag)
}

fun dismissDialogFragment(fragmentManager: FragmentManager, tag: String) {
    val fragment = fragmentManager.findFragmentByTag(tag)
    val df = fragment as DialogFragment
    df.dismiss()
}

fun <T : Parcelable> AppCompatActivity.getParcelable(
    key: String
): T? = intent?.extras?.getParcelable(key)


fun AppCompatActivity.startActivityAtRoot(
    context: Context,
    @NonNull clazz: Class<out Activity>, args: Bundle? = null
) {
    val intent = Intent(context, clazz)
    args.notNull {
        intent.putExtras(it)
    }
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}

