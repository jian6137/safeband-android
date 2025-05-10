package com.inclusitech.safeband.ui.views.onboarding.register

import android.util.Patterns
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.inclusitech.safeband.R
import com.inclusitech.safeband.core.vms.SetupVMService
import com.inclusitech.safeband.ui.core.SetupRoutes

@Composable
fun RegisterAccountView(
    navHostController: NavHostController,
    setupVMService: SetupVMService,
) {

    var isPasswordToggled by rememberSaveable { mutableStateOf(false) }

    val nameInitial = if (setupVMService.name.value.isNotEmpty()) setupVMService.name.value.first()
        .toString() else "?"

    var isNameError by rememberSaveable { mutableStateOf(false) }
    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }

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
                    .height(132.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (setupVMService.registerType.value == "CAREGIVER") {
                    Card(
                        modifier = Modifier.size(100.dp),
                        shape = CircleShape,
                        colors = CardDefaults.cardColors()
                            .copy(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = nameInitial,
                                style = MaterialTheme.typography.displayLarge,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }
                } else {
                    Image(
                        painter = painterResource(R.drawable.princess),
                        contentDescription = "Patient User Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .aspectRatio(1f)
                            .clip(CircleShape)
                    )
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Create your SafeBand ${setupVMService.registerType.value.lowercase()} account now.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 24.dp, end = 24.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = setupVMService.name.value,
                        onValueChange = { setupVMService.updateName(it) },
                        label = { Text("Full Name", style = MaterialTheme.typography.bodySmall) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next,
                            capitalization = KeyboardCapitalization.Words
                        ),
                        shape = CircleShape,
                        isError = isNameError
                    )
                    if (isNameError) {
                        Text(
                            "Please make sure account name is not empty, is at least 3 characters long and does not exceed 50 characters.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 2.dp)
                        )
                    }
                    OutlinedTextField(
                        value = setupVMService.email.value,
                        onValueChange = { setupVMService.updateEmail(it) },
                        label = { Text("Email", style = MaterialTheme.typography.bodySmall) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                        ),
                        shape = CircleShape,
                        isError = isEmailError
                    )
                    if (isEmailError) {
                        Text(
                            "Please make sure account email is valid, and is not empty.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 2.dp)
                        )
                    }
                    OutlinedTextField(
                        value = setupVMService.password.value,
                        onValueChange = { setupVMService.updatePassword(it) },
                        label = { Text("Password", style = MaterialTheme.typography.bodySmall) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Password,

                            ),
                        shape = CircleShape,
                        visualTransformation = if (isPasswordToggled) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val (icon, iconColor) = if (isPasswordToggled) {
                                Pair(
                                    Icons.AutoMirrored.Filled.Send,
                                    MaterialTheme.colorScheme.onSurface
                                )
                            } else {
                                Pair(
                                    Icons.AutoMirrored.Filled.Send,
                                    MaterialTheme.colorScheme.onSurface
                                )
                            }

                            IconButton(onClick = { isPasswordToggled = !isPasswordToggled }) {
                                Icon(
                                    icon,
                                    contentDescription = "Visibility",
                                    tint = iconColor
                                )
                            }
                        },
                        isError = isPasswordError
                    )
                    if (isPasswordError) {
                        Text(
                            "Please make sure account password is not empty, and must be at least 8 characters.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 2.dp)
                        )
                    }
                    Button(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
                        onClick = {
                            isEmailError = false
                            isNameError = false
                            isPasswordError = false

                            if (setupVMService.name.value.isEmpty() || setupVMService.name.value.length <= 3 || setupVMService.name.value.length > 50) {
                                isNameError = true
                            } else {
                                if (setupVMService.email.value.isEmpty() || !isValidEmail(
                                        setupVMService.email.value
                                    )
                                ) {
                                    isEmailError = true
                                } else {
                                    if (setupVMService.password.value.isEmpty() && setupVMService.password.value.length < 9) {
                                        isPasswordError = true
                                    } else {
                                        navHostController.navigate(SetupRoutes.RegisterInitiate.route)
                                    }
                                }
                            }
                        },
                    ) {
                        Text("Register")
                    }
                }
            }
        }
    }
}

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
