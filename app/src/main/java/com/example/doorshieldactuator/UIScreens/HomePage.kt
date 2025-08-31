package com.example.doorshieldactuator.UIScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var isClickedUnlockDoor by remember { mutableStateOf(false) }
    var isClickedlockDoor by remember { mutableStateOf(false) }
    val venomNoirBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF29323C),
            Color(0xFF485563)
        )
    )
    val carbonPulseBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF3F281A), // Dark coffee brown
            Color(0xFF9A8478)  // Warm beige bronze
        )
    )
    val clickedBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF616161), // Gray when clicked
            Color(0xFF9E9E9E)  // Lighter gray shade
        )
    )
    val ivorySmokeBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFAF9F6), // Soft Ivory White
            Color(0xFFDCD7CE), // Warm Beige Gray
            Color(0xFFACA69A)  // Taupe Smoke
        )
    )

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFA29B56),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                title = {
                    Text(
                        text = "Home Screen",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        modifier = Modifier.padding(start = 40.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {

                    }, modifier = Modifier.size(50.dp)) {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            tint = Color.White,
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {

        }, floatingActionButton = {
            FloatingActionButton(onClick = {}, modifier = Modifier.size(80.dp)) {
                Icon(
                    imageVector = Icons.Filled.Call,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    ) { innerPadding ->

        Box(
            contentAlignment = androidx.compose.ui.Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE3C289))
        ) {

            Row {
                Button(
                    onClick = { isClickedUnlockDoor = !isClickedUnlockDoor },
                    modifier = Modifier
                        .size(width = 150.dp, height = 150.dp),
                    shape = RoundedCornerShape(27),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    ),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = if (isClickedUnlockDoor) clickedBrush else carbonPulseBrush,
                                shape = RoundedCornerShape(27)
                            ),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(
                            if (isClickedUnlockDoor) "Door\nUnlocked" else "Unlock Door",
                            fontSize = 25.sp,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.width(20.dp))

                Button(
                    onClick = { isClickedlockDoor = !isClickedlockDoor },
                    modifier = Modifier
                        .size(width = 150.dp, height = 150.dp),
                    shape = RoundedCornerShape(27),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    ),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = if (isClickedlockDoor) clickedBrush else carbonPulseBrush,
                                shape = RoundedCornerShape(27)
                            ),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(
                            if (isClickedlockDoor) "Door\nLocked" else "Lock Door",
                            fontSize = 25.sp,
                            color = Color.White
                        )
                    }
                }



            }
        }
    }
}