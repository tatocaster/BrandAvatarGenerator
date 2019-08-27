package me.tatocaster.brandavatargeneratorsample

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import me.tatocaster.brandavatargenerator.BrandAvatarGenerator
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        GlideApp.with(this)
            .asBitmap()
            .load("https://api.adorable.io/avatars/285/useravatar.png")
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // Handle the successful result.

                    val generator = BrandAvatarGenerator.Builder(
                        BitmapFactory.decodeResource(resources, R.drawable.ic_em_logo),
                        resource
                    )
                        .withDownScalingFactor(6f)
                        .withOpacity(80)
                        .build()

                    generator.generate {
                        findViewById<ImageView>(R.id.image).setImageBitmap(it)
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })

    }
}
