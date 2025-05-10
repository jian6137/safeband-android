package com.inclusitech.safeband.ui.views.onboarding.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.inclusitech.safeband.R
import com.inclusitech.safeband.core.vms.SetupVMService
import com.inclusitech.safeband.ui.core.SetupRoutes

@Composable
fun CaregiverAccountSetupView(navHostController: NavHostController, setupVMService: SetupVMService) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(124.dp)
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.princess),
                    contentDescription = "Patient User Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                )
                if(setupVMService.registerType.value === "CAREGIVER"){
                    Image(
                        painter = painterResource(R.drawable.long_arrow),
                        contentDescription = "Arrow Right",
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                    Card(
                        modifier = Modifier.size(100.dp),
                        shape = CircleShape,
                        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                    ) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            Text(text = setupVMService.name.value.first().toString(), style = MaterialTheme.typography.displayLarge, color = MaterialTheme.colorScheme.onTertiaryContainer)
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = "Account Management",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Center,
            )
            if(setupVMService.registerType.value === "CAREGIVER"){
                Text(
                    text = "As ${if(setupVMService.patientName.value == "") "Patient" else setupVMService.patientName.value}'s Caregiver, only you and your account can access ${if(setupVMService.patientGender.value.uppercase() == "FEMALE") "her" else "his"} information and manage ${if(setupVMService.patientGender.value.uppercase() == "FEMALE") "her" else "his"} medical records including setting up SafeBand products.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = "By signing up as the patient, you’ll only be the one that has secure access to your personal medical information. You’ll also be able to configure any connected SafeBand devices directly from your own account.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(64.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 24.dp, end = 24.dp)
            ) {
                Button(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    onClick = {
                        navHostController.navigate(SetupRoutes.RegisterAccountView.route)
                    },
                ) {
                    Text("Continue")
                }
                OutlinedButton(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    onClick = {
                        navHostController.popBackStack()
                    },
                ) {
                    Text("Back")
                }
            }
        }
    }
}