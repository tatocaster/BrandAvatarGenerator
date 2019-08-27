# BrandAvatarGenerator

[![](https://jitpack.io/v/tatocaster/BrandAvatarGenerator.svg)](https://jitpack.io/#tatocaster/BrandAvatarGenerator)

### Preview
<img src="https://raw.githubusercontent.com/tatocaster/BrandAvatarGenerator/master/art/art.jpg" width="240" height="400" />

### How To Use 

- Add `BrandAvatarGenerator` to your app.

 Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

 Add the dependency

```groovy
dependencies {
	        implementation 'com.github.tatocaster:BrandAvatarGenerator:1.0.0'
	}
```
- more examples in the sample app

```
  GlideApp.with(this)
           .asBitmap()
           .load("https://api.adorable.io/avatars/285/useravatar.png")
           .into(object : CustomTarget<Bitmap>() {
               override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                   // Handle the successful result.

                   val generator = BrandAvatarGenerator.Builder(
                       BitmapFactory.decodeResource(resources, R.drawable.ic_em_logo), // our brand logo
                       resource // avatar
                   )
                       .withDownScalingFactor(6f) // Optional, scaling down factor
                       .withOpacity(80) // Optional, opacity 0-100
                       .build()

                   generator.generate {
                       findViewById<ImageView>(R.id.image).setImageBitmap(it)
                   }
               }

               override fun onLoadCleared(placeholder: Drawable?) {
               }
           })

```

That's all. You can customize it the way you want.

### Customization

- `withDownScalingFactor` Float, default: 1.0f
- `withOpacity` Int, default: 80


# Quick contributing guide
 - Fork and clone locally
 - Create a topic specific branch. Add some nice feature.
 - Send a Pull Request to spread the fun!


License
-------

    MIT License
    
    Copyright (c) 2019 Merab Tato Kutalia
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.