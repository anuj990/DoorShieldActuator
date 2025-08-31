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


    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF29313C),
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
                .background(venomNoirBrush)
        ) {

            Row {
                Button(
                    onClick = {
                        isClickedUnlockDoor = !isClickedUnlockDoor
                    },
                    modifier = Modifier
                        .size(width = 150.dp, height = 150.dp), shape = RoundedCornerShape(27),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isClickedUnlockDoor) Color(0xFF616161) else Color(
                            0xFF212121
                        ),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        if (isClickedUnlockDoor) "Door\nUnlocked" else "Unlock Door",
                        fontSize = if (isClickedUnlockDoor) 20.sp else 25.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Button(
                    onClick = {
                        isClickedlockDoor = !isClickedlockDoor
                    },
                    modifier = Modifier
                        .size(width = 150.dp, height = 150.dp), shape = RoundedCornerShape(27),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isClickedlockDoor) Color(0xFF616161) else Color(
                            0xFF212121
                        ),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        if (isClickedlockDoor) "Door Locked" else "Lock Door",
                        fontSize = if (isClickedlockDoor) 25.sp else 25.sp,
                        color = Color.White
                    )
                }


            }
        }
    }
}