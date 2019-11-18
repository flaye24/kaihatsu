package com.flaye.kaihatsu.hiragana.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.lifecycle.Observer
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.setContent
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Padding
import androidx.ui.layout.Row
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import com.flaye.kaihatsu.R
import com.flaye.kaihatsu.VectorImageButton
import com.flaye.kaihatsu.hiragana.source.model.HiraganaDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = getViewModel<HiraganasListViewModel>()
        viewModel.hiraganaItemsLiveData.observe(this, Observer {
            setContent {
                createListView(it) {
                    viewModel.speakHiragana(it.kana)
                }
            }
        })



    }
}


@Composable
private fun createListView(
    hiraganaItems: List<HiraganaItem>,
    onHiraganaItemClick: (HiraganaItem) -> Unit
) {
    MaterialTheme {
        VerticalScroller {
            Column {
                hiraganaItems.forEach {
                    createHiraganaItem(
                        it, onHiraganaItemClick
                    )
                    Divider(color = Color.Gray, height = 1.dp)
                }
            }
        }
    }
}

@Composable
private fun createHiraganaItem(
    hiraganaItem: HiraganaItem,
    onHiraganaItemClick: (HiraganaItem) -> Unit
) {
    Padding(left = 8.dp, right = 8.dp, top = 8.dp, bottom = 8.dp) {
        Row {
            Text("${hiraganaItem.kana} ${hiraganaItem.hepburnRomanization} ${hiraganaItem.ipaTranscription}")
            VectorImageButton(
                id = R.drawable.ic_volume_up,
                onClick = {
                    onHiraganaItemClick(hiraganaItem)
                })
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        val hiraganaItems = listOf(
            HiraganaItem(
                "あ",
                "a"
            ), HiraganaItem("い", "i")
        )
        createListView(hiraganaItems) {}
    }
}

data class HiraganaItem(
    val kana: String = "",
    val hepburnRomanization: String = "",
    val ipaTranscription: String = ""
)
