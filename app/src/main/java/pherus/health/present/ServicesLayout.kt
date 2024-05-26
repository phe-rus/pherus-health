package pherus.health.present

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowRightAlt
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pherus.health.R
import pherus.health.ui.theme.PherusTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesLayout(
    router: NavHostController,
    extra: String?
) {
    val coroutine = rememberCoroutineScope()
    val lazyScrollState = rememberLazyListState()

    var searchQuery by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = extra.toString(),
                        fontWeight = FontWeight.Black,
                        fontSize = 23.sp,
                        maxLines = 1,
                        lineHeight = 23.sp
                    )
                },
                navigationIcon = {
                    BackButton(
                        coroutine = coroutine,
                        router = router
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    ) { pdv ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = pdv)
        ) {
            val parentHeight = constraints.maxHeight
            val parentWidth = constraints.maxWidth
            if (extra.toString().contains("doctors", true)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { sq ->
                            searchQuery = sq
                        },
                        placeholder = {
                            Text(
                                text = "Search",
                                fontWeight = FontWeight.Light,
                                fontSize = 12.sp,
                                lineHeight = 12.sp
                            )
                        },
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            lineHeight = 12.sp
                        ),
                        leadingIcon = {
                            Icon(
                                Icons.Rounded.Search,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        shape = RoundedCornerShape(30),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                    )
                    DoctorsList(lazyScrollState = lazyScrollState)
                }
            }
            if (extra.toString().contains("pharmacies", true)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { sq ->
                            searchQuery = sq
                        },
                        placeholder = {
                            Text(
                                text = "Search",
                                fontWeight = FontWeight.Light,
                                fontSize = 12.sp,
                                lineHeight = 12.sp
                            )
                        },
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            lineHeight = 12.sp
                        ),
                        leadingIcon = {
                            Icon(
                                Icons.Rounded.Search,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        shape = RoundedCornerShape(30),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Medicines",
                            fontWeight = FontWeight.Bold,
                            fontSize = 23.sp,
                            lineHeight = 23.sp,
                            maxLines = 1,
                            modifier = Modifier.padding(5.dp)
                        )

                        Text(
                            text = "12",
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp,
                            lineHeight = 18.sp,
                            maxLines = 1,
                            modifier = Modifier.padding(5.dp)
                        )
                    }

                    PharmaciesList(parentHeight)
                }
            }
            if (extra.toString().contains("hospitals", true)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { sq ->
                            searchQuery = sq
                        },
                        placeholder = {
                            Text(
                                text = "Search",
                                fontWeight = FontWeight.Light,
                                fontSize = 12.sp,
                                lineHeight = 12.sp
                            )
                        },
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            lineHeight = 12.sp
                        ),
                        leadingIcon = {
                            Icon(
                                Icons.Rounded.Search,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        shape = RoundedCornerShape(30),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Hospitals",
                            fontWeight = FontWeight.Bold,
                            fontSize = 23.sp,
                            lineHeight = 23.sp,
                            maxLines = 1,
                            modifier = Modifier.padding(5.dp)
                        )

                        Text(
                            text = "12",
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp,
                            lineHeight = 18.sp,
                            maxLines = 1,
                            modifier = Modifier.padding(5.dp)
                        )
                    }

                    HospitalsList()
                }
            }
        }
    }
}

@Composable
fun HospitalsList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        items(7) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.5f)
                            .height(150.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Text(
                                text = "norvik hospital uganda",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                lineHeight = 18.sp
                            )

                            Text(
                                text = "Norvik Hospital . Plot 13 Bombo Rd, Kampala",
                                fontWeight = FontWeight.Light,
                                fontSize = 13.sp,
                                lineHeight = 13.sp
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Button(
                                onClick = {},
                                shape = RoundedCornerShape(100),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "See more",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        lineHeight = 12.sp
                                    )
                                    Icon(
                                        Icons.AutoMirrored.Rounded.ArrowRightAlt,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.weight(0.5f)
                    ) {
                        ElevatedCard(
                            onClick = {},
                            shape = RoundedCornerShape(20),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_background),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            )
                        }
                    }
                }
            }
        }
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(20.dp)
            )
        }
    }
}

@Composable
fun BackButton(
    coroutine: CoroutineScope,
    router: NavHostController
) {
    IconButton(
        onClick = {
            coroutine.launch {
                router.popBackStack()
            }
        }, modifier = Modifier
    ) {
        Icon(
            Icons.Rounded.ArrowBackIosNew,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun PharmaciesList(parentHeight: Int) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .heightIn(max = parentHeight.dp)
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(7) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {

            }
        }
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(20.dp)
            )
        }
    }
}

@Composable
fun DoctorsList(lazyScrollState: LazyListState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        state = lazyScrollState,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Medical Professionals",
                    fontWeight = FontWeight.Bold,
                    fontSize = 23.sp,
                    lineHeight = 23.sp,
                    maxLines = 1,
                    modifier = Modifier.padding(5.dp)
                )

                Text(
                    text = "12",
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp,
                    lineHeight = 18.sp,
                    maxLines = 1,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
        items(12) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Column(
                            modifier = Modifier
                        ) {
                            FilledIconButton(
                                onClick = {},
                                shape = RoundedCornerShape(100),
                                modifier = Modifier.size(60.dp)
                            ) {

                            }
                        }

                        Column(
                            modifier = Modifier
                        ) {
                            Text(
                                text = "Doctors Name",
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp,
                                lineHeight = 18.sp
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "doctors@email.com",
                                    fontWeight = FontWeight.Light,
                                    fontSize = 10.sp,
                                    lineHeight = 10.sp
                                )
                                Text(
                                    text = "DOC-10THJ-768HUG",
                                    fontWeight = FontWeight.Light,
                                    fontSize = 10.sp,
                                    lineHeight = 10.sp
                                )
                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = "Specialist",
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp,
                                lineHeight = 12.sp
                            )
                            Spacer(modifier = Modifier.size(2.dp))
                            Text(
                                text = "Am doctor Almon a General Doctor in the filed of medicine toher to the pathonolog the blah notpe tope cone Am doctor Almon a General Doctor in the filed of medicine toher to the pathonolog the blah notpe tope cone",
                                fontWeight = FontWeight.Light,
                                fontSize = 10.sp,
                                lineHeight = 10.sp,
                                maxLines = 3,
                                softWrap = true,
                                overflow = TextOverflow.Ellipsis
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                ElevatedFilterChip(
                                    selected = false,
                                    onClick = {},
                                    label = {
                                        Text(
                                            text = "profile",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp,
                                            lineHeight = 10.sp
                                        )
                                    },
                                    shape = RoundedCornerShape(100)
                                )
                                ElevatedFilterChip(
                                    selected = false,
                                    onClick = {},
                                    label = {
                                        Text(
                                            text = "Request Appointment",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp,
                                            lineHeight = 10.sp
                                        )
                                    },
                                    shape = RoundedCornerShape(100)
                                )
                            }
                        }
                    }
                }
            }
        }
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(20.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewDoctors() {
    PherusTheme {
        DoctorsList(lazyScrollState = rememberLazyListState())
    }
}