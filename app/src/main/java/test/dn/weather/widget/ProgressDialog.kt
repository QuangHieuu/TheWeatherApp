package test.dn.weather.widget

import android.app.Dialog
import android.content.Context
import android.view.Window
import test.dn.weather.R

class ProgressDialog(context: Context) : Dialog(context) {

    init {
        initVIew()
    }

    private fun initVIew() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_progress_dialog)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }
}