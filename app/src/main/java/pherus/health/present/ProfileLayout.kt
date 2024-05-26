package pherus.health.present

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.ContactPhone
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.MarkEmailRead
import androidx.compose.material.icons.rounded.MedicalInformation
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pherus.health.R
import pherus.health.components.Footer
import pherus.health.components.LoadingState
import pherus.health.components.WideCalendar
import pherus.health.components.WideEditor
import pherus.health.config.Config.PatientInformation
import pherus.health.config.Config.ProfInformation
import pherus.health.viewModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileLayout(router: NavHostController, viewmodel: MainViewModel) {
    val profileInformtion = viewmodel.usrCollection.collectAsState().value
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    var calenderShow by rememberSaveable { mutableStateOf(false) }

    var profileEditor by rememberSaveable { mutableStateOf(false) }
    var profileTitle by rememberSaveable { mutableStateOf("") }
    var profileKeyMap by rememberSaveable { mutableStateOf("") }
    var profileValue by rememberSaveable { mutableStateOf("") }

    var imgpickerLoader by rememberSaveable { mutableStateOf(false) }

    val patientsInformation = mutableListOf(
        "Preferred Name", "Date Of Birth", "Email Address", "Phone Number", "Full Name"
    )

    val profileDetails = mutableListOf(
        ProfInformation(
            title = "Preferred Name",
            value = profileInformtion?.basicInformations?.preferedName,
            keyMap = "basicInformations/preferedName",
            icon = Icons.Rounded.Person
        ), ProfInformation(
            title = "Email Address",
            value = profileInformtion?.contactInformation?.email,
            keyMap = "contactInformation/email",
            icon = Icons.Rounded.MarkEmailRead
        ), ProfInformation(
            title = "Phone Number",
            value = profileInformtion?.contactInformation?.phoneNumber,
            keyMap = "contactInformation/phoneNumber",
            icon = Icons.Rounded.ContactPhone
        ), ProfInformation(
            title = "Local Address",
            value = profileInformtion?.contactInformation?.localAddress,
            keyMap = "contactInformation/localAddress",
            icon = Icons.Rounded.LocationOn
        ), ProfInformation(
            title = "Date Of Birth",
            value = profileInformtion?.contactInformation?.dateOfbirth,
            keyMap = "contactInformation/dateOfbirth",
            icon = Icons.Rounded.DateRange
        )
    )

    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewmodel.uploadImage(context = context,
                    imageUri = uri,
                    uploadUrl = "basicInformations/avatarHolder",
                    onUploadState = {
                        imgpickerLoader = it
                    })
            } else {
                println("PhotoPicker No media selected")
            }
        }

    LaunchedEffect(Unit, pickMedia) {
        delay(100)
        viewmodel.initail()
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Settings", fontWeight = FontWeight.Bold, fontSize = 23.sp
                    )
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = {
                        coroutine.launch {
                            router.popBackStack()
                        }
                    }, modifier = Modifier.size(35.dp)
                ) {
                    Icon(
                        Icons.Rounded.ArrowBackIosNew,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        )
    }) { pdv ->
        LazyColumn(
            contentPadding = pdv,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            item {
                ProfileCover(
                    coroutine = coroutine,
                    profileInformtion = profileInformtion,
                    pickMedia = pickMedia
                )
            }

            item {
                ElevatedCard(
                    shape = RoundedCornerShape(100), colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            profileInformtion?.basicInformations?.run {
                                Text(
                                    text = patientsId.toString(),
                                    fontWeight = FontWeight.Black
                                )
                            }
                            Icon(
                                Icons.Rounded.Info,
                                contentDescription = null,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                        profileInformtion?.basicInformations?.run {
                            Text(
                                text = createdAt.toString().replace("-", "/"),
                                fontWeight = FontWeight.Light
                            )
                        }
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "Contact Information",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(5.dp)
                    )

                    profileDetails.forEachIndexed { _, profInformation ->
                        ProfileComponents(profInformation = profInformation, onClickListener = {
                            if (profInformation.title == "Date Of Birth") {
                                calenderShow = true
                                profileKeyMap = profInformation.keyMap.toString()
                            }
                            if (!profInformation.title!!.contains(
                                    "Email Address", true
                                ) && !profInformation.title.contains("Date Of Birth", true)
                            ) {
                                profileEditor = true
                                profileValue = profInformation.value.toString()
                                profileTitle = profInformation.title
                                profileKeyMap = profInformation.keyMap.toString()
                            }
                        })
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "Clinical Profile",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(5.dp)
                    )

                    patientsInformation.forEach { item ->
                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(34)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp
                                    ), horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    Icons.Rounded.MedicalInformation, contentDescription = null
                                )
                                Column {
                                    Text(
                                        text = item, fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = item, fontWeight = FontWeight.Light
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Your Videos & Audios",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }

            items(1) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(34)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(Icons.Rounded.MedicalInformation, contentDescription = null)
                        Text(text = "No emergency videos")
                    }
                }
            }

            item {
                Footer()
            }
        }

        if (imgpickerLoader) {
            LoadingState(closeSelection = {
                imgpickerLoader = false
            })
        }

        if (profileEditor) {
            WideEditor(title = profileTitle, defaultValue = profileValue, closeSelection = {
                profileEditor = false
            }, onValueListener = { initValue ->
                coroutine.launch {
                    viewmodel.writeToDatabase(
                        key = profileKeyMap,
                        value = initValue,
                        onComplete = { success, _ ->
                            if (initValue.isNotEmpty()) {
                                if (success) {
                                    profileEditor = false
                                    profileKeyMap = ""
                                    profileTitle = ""
                                    profileValue = ""
                                }
                            }
                        })
                }
            })
        }

        if (calenderShow) {
            WideCalendar(
                closeSelection = {
                    calenderShow = false
                }, selectedValue = { dob ->
                    coroutine.launch {
                        viewmodel.writeToDatabase(
                            key = profileKeyMap,
                            value = dob,
                            onComplete = { success, _ ->
                                if (dob.isNotEmpty()) {
                                    if (success) {
                                        calenderShow = false
                                        profileKeyMap = ""
                                    }
                                }
                            })
                    }
                }
            )
        }
    }
}


@Composable
fun ProfileComponents(
    profInformation: ProfInformation, onClickListener: () -> Unit
) {
    ElevatedCard(modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(34),
        onClick = { onClickListener() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp
                ), horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            profInformation.run {
                Icon(
                    icon!!, contentDescription = null
                )
            }
            Column {
                profInformation.run {
                    Text(
                        text = title.toString(), fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = value.toString(), fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileCover(
    profileInformtion: PatientInformation?,
    pickMedia: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    coroutine: CoroutineScope
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp), shape = RoundedCornerShape(12)
    ) {
        val coverImage = profileInformtion?.basicInformations?.coverBackground
        Box(modifier = Modifier.fillMaxSize()) {
            val painter = coverImage?.let {
                rememberAsyncImagePainter(it)
            } ?: painterResource(id = R.drawable.cover_image)

            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surfaceContainer.copy(
                                    alpha = 0.0f
                                ), MaterialTheme.colorScheme.surfaceContainer.copy(
                                    alpha = 0.2f
                                ), MaterialTheme.colorScheme.surfaceContainer.copy(
                                    alpha = 0.6f
                                ), MaterialTheme.colorScheme.surfaceContainer.copy(
                                    alpha = 1.0f
                                )
                            )
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilledIconButton(
                        onClick = {
                            coroutine.launch {
                                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            }
                        }, modifier = Modifier.size(55.dp)
                    ) {
                        profileInformtion?.basicInformations?.run {
                            Image(
                                painter = rememberAsyncImagePainter(avatarHolder),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(0.dp)
                            )
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        profileInformtion?.basicInformations?.run {
                            Text(
                                text = preferedName.toString(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                        profileInformtion?.contactInformation?.run {
                            Text(
                                text = email.toString(),
                                fontWeight = FontWeight.Light,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}