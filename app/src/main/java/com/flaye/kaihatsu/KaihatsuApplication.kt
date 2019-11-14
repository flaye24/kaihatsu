package com.flaye.kaihatsu

import android.app.Application
import com.flaye.kaihatsu.hiragana.presentation.HiraganasListViewModel
import com.flaye.kaihatsu.hiragana.source.HiraganaRemoteDataSource
import com.flaye.kaihatsu.hiragana.source.HiraganaRemoteRemoteDataSourceImpl
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KaihatsuApplication : Application() {

    private val hiraganasModule = module {
        viewModel {
            HiraganasListViewModel(
                get()
            )
        }
        factory {
            HiraganaRemoteRemoteDataSourceImpl(
                get()
            ) as HiraganaRemoteDataSource
        }
        single { FirebaseFirestore.getInstance() }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@KaihatsuApplication)
            modules(hiraganasModule)
        }
    }
}