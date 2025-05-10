package com.inclusitech.safeband.ui.views.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemGestures
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.inclusitech.safeband.R
import com.inclusitech.safeband.core.vms.MainVMService
import com.inclusitech.safeband.ui.core.MainRoutes

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddNewDeviceView(
    navHostController: NavHostController,
    mainVMService: MainVMService
) {
    var selectedItem by rememberSaveable { mutableIntStateOf(-1) }
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(124.dp))
                Text(
                    text = "Select Device",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "To know what type of SafeBand Device you have, please refer to the product's manual or visit https://safeband.zaide.online/products/identify.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))
                FlowRow(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    maxItemsInEachRow = 2,

                    ) {
                    val items = listOf(
                        ("SafeBand v1" to "Basic Smart Wristband with Smart Tagging") to ("sb-v1" to "p1"),
                        ("SafeBand Pro \n(Coming Soon)" to "Advanced Smart Wristband with Smart Tagging, Tracking (Via BLE) and Geofencing") to ("sb-pro" to "p2")
                    )

                    items.forEachIndexed { index, (productInfo, metadata) ->
                        Card(
                            modifier = Modifier
                                .width(160.dp)
                                .fillMaxRowHeight(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            enabled = index != 1,
                            onClick = {
                                selectedItem = if (selectedItem != index) index else -1
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            ) {
                                Column {
                                    Image(
                                        painter = painterResource(if (metadata.first == "sb-v1") R.drawable.safeband_v1 else R.drawable.safeband_pro),
                                        contentDescription = productInfo.first,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(1f)
                                            .clip(RoundedCornerShape(12.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(
                                        text = productInfo.first,
                                        style = MaterialTheme.typography.titleSmall,
                                        modifier = Modifier.padding(
                                            start = 16.dp,
                                            end = 16.dp,
                                            top = 16.dp
                                        )
                                    )
                                    Text(
                                        text = productInfo.second,
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(
                                            start = 16.dp,
                                            end = 16.dp,
                                            top = 2.dp,
                                            bottom = 16.dp
                                        )
                                    )
                                }
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.End

                                ) {
                                    Box(
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .padding(8.dp)
                                            .clip(CircleShape)
                                            .background(
                                                MaterialTheme.colorScheme.surfaceContainerHigh.copy(
                                                    alpha = 0.65f
                                                )
                                            )
                                    ) {
                                        Checkbox(
                                            checked = selectedItem == index,
                                            onCheckedChange = {
                                                selectedItem = if (it) index else -1
                                            },
                                            enabled = index != 1
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
            HorizontalDivider()
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = WindowInsets.systemGestures
                            .asPaddingValues()
                            .calculateBottomPadding() + 16.dp
                    ),
                onClick = {
                    navHostController.navigate(MainRoutes.ConnectDevicePicker.route)
                },
                enabled = selectedItem >= 0
            ) {
                Text("Next")
            }
        }
    }

}