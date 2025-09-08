package test.android.paycontrol

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import tech.paycon.sdk.v5.PCUsersManager

internal class MainActivity : ComponentActivity() {
    private fun importUser() {
        val source: String = """
            {}
        """.trimIndent()
        val user = PCUsersManager.importUser(source)
        if (user == null) {
            showToast("Failed to import PCUser!")
        } else {
            TODO("user: $user")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context: Context = this
        val contentView = FrameLayout(context).also {
            it.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
        }
        val root = LinearLayout(context).also {
            it.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            it.orientation = LinearLayout.VERTICAL
            it.gravity = Gravity.CENTER_VERTICAL
            contentView.addView(it)
        }
        Button(context).also {
            it.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
            it.text = "import user"
            it.setOnClickListener { _ ->
                importUser()
            }
            root.addView(it)
        }
        setContentView(contentView)
    }
}