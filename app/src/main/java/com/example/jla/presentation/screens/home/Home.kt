package com.example.jla.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jla.R
import com.example.jla.domain.model.User
import com.example.jla.presentation.screens.home.composable.BottomItemOptions
import com.example.jla.presentation.screens.home.composable.BottomRowItem
import com.example.jla.presentation.screens.my_apps.chat.ChatViewModel
import kotlinx.coroutines.flow.drop
import org.koin.androidx.compose.koinViewModel
import java.sql.Timestamp

@Composable
fun Home(
    navController: NavController,
    modifier: Modifier = Modifier,
    navigate: (String) -> Unit,
) {

    LaunchedEffect(Unit) {
        snapshotFlow { }
            .drop(1)
            .collect {
                navController.popBackStack()
            }

    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(0.dp, 0.dp, 100.dp, 0.dp))
                    .background(Color(0xFF3F8EFC)),
            ) {
                Text(
                    modifier = modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 30.dp, bottom = 150.dp),
                    text = "Hi!",
                    fontSize = 60.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Black
                )

                Image(
                    modifier = modifier
                        .fillMaxSize()
                        .align(Alignment.CenterEnd),
                    painter = painterResource(id = R.drawable.sample_profile_no_bg),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, bottom = 14.dp),
                    text = "James Lambert O. Asis",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Black

                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp)
            ) {
                Text(
                    text = "What does this contains?",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "This app's contents are achievements, project handled and personal information about the developer. " +
                            "This also contains the information on tech stack, tools and architecture used in developing this app.",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Justify,
                    fontStyle = FontStyle.Italic,
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            Column(
                modifier = Modifier
                    .weight(1.3f)
                    .padding(start = 14.dp, end = 14.dp)
            ) {
                Text(
                    text = "About Me",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(12.dp))

                BottomItemOptions {
                    navigate(it)
                }
            }
        }//end of outer column 
    }//end of box

}



