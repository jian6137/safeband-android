package com.inclusitech.safeband.ui.views.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.material3.MaterialTheme
import com.inclusitech.safeband.core.vms.MainVMService
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.inclusitech.safeband.R
import com.inclusitech.safeband.ui.components.EmptyDevices
import com.inclusitech.safeband.ui.core.BottomNavigationItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeDashboard(
    navHostController: NavHostController,
    pageNavigationItem: NavHostController,
    mainVMService: MainVMService,
    parentScrollState: ScrollState,
    scaffoldPadding: Dp
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(parentScrollState)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(scaffoldPadding)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
            )

            if (mainVMService.deviceData == null) {
                EmptyDevices()
            } else {
                Text(
                    text = "Manage SafeBand Devices",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp)
                )

                FlowRow(
                    modifier = Modifier.wrapContentWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    maxItemsInEachRow = 2
                ) {
                    mainVMService.deviceData?.forEachIndexed { index, deviceData ->
                        Card(
                            modifier = Modifier
                                .width(160.dp)
                                .height(150.dp)
                                .fillMaxRowHeight(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                        ) {
                            Column(verticalArrangement = Arrangement.Bottom) {
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = deviceData.deviceName,
                                    style = MaterialTheme.typography.titleSmall,
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 16.dp
                                    )
                                )
                                Text(
                                    text = deviceData.deviceModel,
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 2.dp,
                                        bottom = 16.dp
                                    )
                                )
                            }
                        }
                    }
                }
            }

        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1000.dp)
        )
    }
}