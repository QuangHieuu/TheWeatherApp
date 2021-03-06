package test.dn.weather.utils.extension

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import test.base.architecture.utils.extension.withClickableSpan
import test.dn.weather.R
import test.dn.weather.utils.InternetManager
import java.util.concurrent.TimeUnit

fun View.show(isShow: Boolean = true) {
    visibility = if (isShow) View.VISIBLE else View.INVISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.gone(isShow: Boolean = true) {
    visibility = if (isShow) View.VISIBLE else View.GONE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun Context.showToast(message: String? = "") =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.showSnackBar(message: String?, mainView: View) =
    Snackbar.make(mainView, message ?: "", Snackbar.LENGTH_LONG).show()

@SuppressLint("SetJavaScriptEnabled")
fun WebView.loadWebViewUrl(url: String?, progressBar: ProgressBar?) {
    if (url.isNullOrEmpty()) return
    if (progressBar == null) {
        webViewClient = WebViewClient()
    } else {
        webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }
        }
    }
    with(settings) {
        javaScriptEnabled = true
        scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
    }
    loadUrl(url)
}

fun ImageView.loadImageUrl(url: String?) {
    url.notNull {
        //        GlideApp.with(this.context).load(it)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                .dontTransform()
//                .into(this)
    }
}

fun ImageView.loadImageUri(uri: Uri?) {
    uri.notNull {
        Glide.with(this.context)
            .load(uri)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(this)
    }
}

fun View.OnClickListener.listenToViews(vararg views: View) {
    views.forEach { it.setOnClickListener(this) }
}

fun View.setIsSelected(isSelect: Boolean = true) {
    this.isSelected = isSelect
}

@SuppressLint("ClickableViewAccessibility")
fun View.setOnVeryLongClickListener(timeDelay: Long = 3000, listener: () -> Unit) {
    setOnTouchListener(object : View.OnTouchListener {

        private val handler = Handler(Looper.getMainLooper())

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            if (event?.action == MotionEvent.ACTION_DOWN) {
                handler.postDelayed({
                    listener.invoke()
                }, timeDelay)
            } else if (event?.action == MotionEvent.ACTION_UP) {
                handler.removeCallbacksAndMessages(null)
            }
            return true
        }
    })
}

fun TextView.setTextWithSpan(color: Int, clickablePart: String, onClick: () -> Unit) {
    SpannableString(this.text).also {
        it.withClickableSpan(
            color,
            this,
            clickablePart
        ) {
            onClick.invoke()
        }
        setText(it, TextView.BufferType.SPANNABLE)
    }
}

fun List<EditText>.showHidePassword(isChecked: Boolean) {
    for (ediText in this) {
        if (isChecked) {
            ediText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            ediText.setSelection(ediText.length())
            continue
        }
        ediText.transformationMethod = PasswordTransformationMethod.getInstance()
        ediText.setSelection(ediText.length())
    }
}

fun List<EditText>.validateContent(): Boolean {
    for (ediText in this) {
        if (ediText.getText().isBlank()) {
            return false
        }
    }
    return true
}

fun View.clicks(isCheckNetwork: Boolean): Observable<View> {
    val source: ObservableOnSubscribe<View> = ObservableOnSubscribe { emitter ->
        emitter.setCancellable {
            setOnClickListener(null)
            emitter.onComplete()
        }

        setOnClickListener {
            val isConnected = InternetManager.isConnected()
            if (isCheckNetwork && !isConnected) {
                val errorMessage = context.getString(R.string.text_internet_error)
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            emitter.onNext(this)
        }
    }
    return Observable.create(source).throttleFirst(
        1, TimeUnit.SECONDS,
        AndroidSchedulers.mainThread()
    )
}