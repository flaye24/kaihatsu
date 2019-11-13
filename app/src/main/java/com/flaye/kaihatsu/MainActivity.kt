package com.flaye.kaihatsu

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.setContent
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.FlexRow
import androidx.ui.layout.Padding
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProviders.of(this)[HiraganasListViewModel::class.java]
        viewModel.hiraganaItemsLiveData.observe(this, Observer {
            setContent {
                createListView(it)
            }
        })

    }
}

@Composable
private fun createListView(hiraganaItems: List<HiraganaItem>) {
    MaterialTheme {
        VerticalScroller {
            Column {
                hiraganaItems.forEach {
                    createHiraganaItem(it)
                    Divider(color = Color.Gray, height = 1.dp)
                }
            }
        }
    }
}

@Composable
private fun createHiraganaItem(hiraganaItem: HiraganaItem) {
    Padding(left = 8.dp, right = 8.dp, top = 8.dp, bottom = 8.dp) {
        FlexRow {
            expanded(1.0f) {
                Text("${hiraganaItem.kana} ${hiraganaItem.hepburnRomanization} ${hiraganaItem.ipaTranscription}")
            }
        }

    }
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        val hiraganaItems = listOf(HiraganaItem("あ", "a"), HiraganaItem("い", "i"))
        createListView(hiraganaItems)
    }
}

data class HiraganaItem(
    val kana: String = "",
    val hepburnRomanization: String = "",
    val ipaTranscription: String = ""
)
