package com.videovoid.pro

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable

class MainActivity : Activity() {
    private val bg = Color.rgb(7, 9, 16)
    private val panel = Color.rgb(18, 22, 36)
    private val text = Color.rgb(245, 247, 255)
    private val muted = Color.rgb(158, 168, 193)
    private val purple = Color.rgb(124, 92, 246)
    private lateinit var urlInput: EditText
    private lateinit var status: TextView
    private lateinit var quality: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = bg
        window.navigationBarColor = bg
        window.decorView.systemUiVisibility = 0
        buildUi()
    }

    private fun rounded(color: Int, radius: Float, stroke: Int = Color.TRANSPARENT): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = radius
            if (stroke != Color.TRANSPARENT) setStroke(1, stroke)
        }
    }

    private fun buildUi() {
        val scroll = ScrollView(this).apply { setBackgroundColor(bg) }
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(22), dp(22), dp(22), dp(24))
        }
        scroll.addView(root)
        setContentView(scroll)

        val brand = TextView(this).apply {
            text = "◉  VideoVoidPro"
            textSize = 23f
            setTextColor(text)
            typeface = Typeface.create("sans-serif", Typeface.BOLD)
            gravity = Gravity.CENTER_VERTICAL
        }
        root.addView(brand, matchWrap())
        val badge = TextView(this).apply {
            text = "  MULTI-SOURCE VIDEO TOOL  "
            textSize = 11f
            setTextColor(Color.rgb(104, 230, 248))
            setPadding(0, dp(10), 0, dp(10))
        }
        root.addView(badge)

        val title = TextView(this).apply {
            text = "Save videos.\\nKeep the moments."
            textSize = 34f
            setTextColor(text)
            typeface = Typeface.create("sans-serif", Typeface.BOLD)
            setPadding(0, dp(14), 0, dp(10))
        }
        root.addView(title, matchWrap())
        val subtitle = TextView(this).apply {
            text = "Paste a video link below. Direct video files can be downloaded to your phone."
            textSize = 15f
            setTextColor(muted)
        }
        root.addView(subtitle, matchWrap())

        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = rounded(panel, dp(22).toFloat(), Color.rgb(48, 51, 78))
            setPadding(dp(16), dp(18), dp(16), dp(18))
        }
        val cardParams = LinearLayout.LayoutParams(-1, -2).apply { topMargin = dp(24) }
        root.addView(card, cardParams)

        card.addView(label("VIDEO URL"))
        urlInput = EditText(this).apply {
            hint = "https://example.com/video.mp4"
            setHintTextColor(Color.rgb(105, 115, 139))
            setTextColor(text)
            textSize = 15f
            singleLine = true
            inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_URI
            setPadding(dp(13), dp(12), dp(13), dp(12))
            background = rounded(bg, dp(12).toFloat(), Color.rgb(45, 52, 74))
        }
        val inputParams = LinearLayout.LayoutParams(-1, -2).apply { topMargin = dp(8) }
        card.addView(urlInput, inputParams)

        val qLabel = label("PREFERRED QUALITY")
        val qParams = LinearLayout.LayoutParams(-1, -2).apply { topMargin = dp(16) }
        card.addView(qLabel, qParams)
        quality = Spinner(this).apply {
            adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_dropdown_item,
                listOf("Best available (direct files)", "1080p preference", "720p preference", "480p preference", "360p preference"))
            setPadding(dp(6), dp(5), dp(6), dp(5))
            background = rounded(bg, dp(12).toFloat(), Color.rgb(45, 52, 74))
        }
        card.addView(quality, LinearLayout.LayoutParams(-1, dp(48)))

        val button = TextView(this).apply {
            text = "♡   CHECK LINK & DOWNLOAD"
            textSize = 15f
            gravity = Gravity.CENTER
            typeface = Typeface.create("sans-serif", Typeface.BOLD)
            setTextColor(Color.WHITE)
            background = rounded(purple, dp(14).toFloat())
            setPadding(dp(12), dp(16), dp(12), dp(16))
            isClickable = true
            isFocusable = true
            setOnClickListener { startDownload() }
        }
        val btnParams = LinearLayout.LayoutParams(-1, -2).apply { topMargin = dp(18) }
        card.addView(button, btnParams)

        status = TextView(this).apply {
            text = "Ready. Paste a direct video file URL to begin."
            textSize = 14f
            setTextColor(muted)
            setPadding(0, dp(14), 0, dp(2))
        }
        card.addView(status, matchWrap())

        val infoTitle = TextView(this).apply {
            text = "HOW IT WORKS"
            textSize = 12f
            setTextColor(Color.rgb(111, 222, 245))
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, dp(25), 0, dp(10))
        }
        root.addView(infoTitle)
        root.addView(infoCard("↗  Link detection", "Identifies common platform domains and direct media links."))
        root.addView(infoCard("⇩  Save to phone", "Direct MP4, WebM, MOV and M4V URLs can be sent to Android Downloads."))
        root.addView(infoCard("◈  Quality options", "Quality selection is a preference only; direct-file URLs usually contain one fixed quality."))

        val note = TextView(this).apply {
            text = "Important: platform page links (such as YouTube, Instagram, Facebook or TikTok) cannot be extracted by this starter app. Each platform needs a permitted, compliant integration. It does not bypass DRM, private access controls, or creator restrictions."
            textSize = 12f
            setTextColor(Color.rgb(225, 195, 135))
            setPadding(dp(14), dp(14), dp(14), dp(14))
            background = rounded(Color.rgb(31, 26, 17), dp(14).toFloat(), Color.rgb(91, 72, 35))
        }
        val noteParams = LinearLayout.LayoutParams(-1, -2).apply { topMargin = dp(18) }
        root.addView(note, noteParams)
    }

    private fun infoCard(heading: String, detail: String): View {
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = rounded(panel, dp(14).toFloat(), Color.rgb(39, 46, 66))
            setPadding(dp(14), dp(12), dp(14), dp(12))
        }
        box.addView(TextView(this).apply {
            text = heading; textSize = 15f; setTextColor(text)
            typeface = Typeface.DEFAULT_BOLD
        })
        box.addView(TextView(this).apply {
            text = detail; textSize = 13f; setTextColor(muted); setPadding(0, dp(4), 0, 0)
        })
        val params = LinearLayout.LayoutParams(-1, -2).apply { bottomMargin = dp(10) }
        box.layoutParams = params
        return box
    }

    private fun label(value: String) = TextView(this).apply {
        text = value; textSize = 12f; setTextColor(muted)
        typeface = Typeface.DEFAULT_BOLD
    }

    private fun startDownload() {
        val raw = urlInput.text.toString().trim()
        val uri = try { Uri.parse(raw) } catch (_: Exception) { null }
        if (uri == null || (uri.scheme != "https" && uri.scheme != "http") || uri.host.isNullOrBlank()) {
            status.setTextColor(Color.rgb(255, 145, 166))
            status.text = "Enter a valid URL starting with https:// or http://."
            return
        }
        val host = uri.host!!.lowercase()
        val pageDomains = listOf("youtube.com", "youtu.be", "instagram.com", "facebook.com", "fb.watch",
            "tiktok.com", "vimeo.com", "x.com", "twitter.com", "pinterest.com")
        val isPlatformPage = pageDomains.any { host == it || host.endsWith(".$it") }
        val path = (uri.path ?: "").lowercase()
        val direct = listOf(".mp4", ".webm", ".mov", ".m4v", ".3gp", ".mkv").any { path.endsWith(it) }

        if (isPlatformPage || !direct) {
            status.setTextColor(Color.rgb(255, 205, 133))
            status.text = "Detected: $host\\nThis is a platform/web page, not a direct video file. This app version cannot extract it. A permitted source-specific integration is required."
            return
        }

        try {
            val request = DownloadManager.Request(uri)
                .setTitle("VideoVoidPro video")
                .setDescription("Downloading video")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(false)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                    "VideoVoidPro_${System.currentTimeMillis()}${extensionFor(path)}")
            val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            manager.enqueue(request)
            status.setTextColor(Color.rgb(125, 244, 206))
            status.text = "Download started! Check your phone's Downloads folder or notification panel."
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(urlInput.windowToken, 0)
        } catch (e: Exception) {
            status.setTextColor(Color.rgb(255, 145, 166))
            status.text = "Could not start download: ${e.message ?: "unknown error"}"
        }
    }

    private fun extensionFor(path: String): String {
        return listOf(".mp4", ".webm", ".mov", ".m4v", ".3gp", ".mkv").firstOrNull { path.endsWith(it) } ?: ".mp4"
    }
    private fun matchWrap() = LinearLayout.LayoutParams(-1, -2)
    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}
