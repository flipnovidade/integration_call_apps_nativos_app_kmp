package br.com.kmp.demo.demo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.kmp.demo.resources.Res
import br.com.kmp.demo.resources.image_not_found
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.resources.painterResource

@Composable
fun ImageFromUrl(url: String = "https://4.bp.blogspot.com/-I6KBH1NIJGk/XIccojn6YII/AAAAAAAADZ8/BeJ6uuutN5YoQe6Zboig_q5djnXS3hVpgCLcBGAs/s1600/Firebase%2BRealtime%2BDatabase%2B%25281-%2BIcon%252C%2BLight%2529.png",
                 sizeImage: Dp = 75.dp) {

    Box(
        modifier = Modifier.wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        CoilImage(
            modifier = Modifier.size(sizeImage)
                .clip(RoundedCornerShape(size = 12.dp)),
            imageModel = { url },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center
            ),
            loading = {
                Box(
                    modifier = Modifier.wrapContentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(28.dp)
                    )
                }
            },
            failure = {
                Box(
                    modifier = Modifier.wrapContentSize()
                ) {
                    Image(
                        modifier = Modifier.wrapContentSize(),
                        painter = painterResource(Res.drawable.image_not_found),
                        contentDescription = "404 Image Not Found",
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        )
    }
}