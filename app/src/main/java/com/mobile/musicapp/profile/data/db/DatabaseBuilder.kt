package com.mobile.musicapp.profile.data.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mobile.musicapp.MusicAppApplication

object DatabaseBuilder {
    private var INSTANCE: AppDatabase? = null

    fun getInstance(): AppDatabase {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = buildRoomDB()
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB() = Room.databaseBuilder(
        MusicAppApplication.INSTANCE!!.applicationContext,
        AppDatabase::class.java,
        "app.db"
    ).addCallback(object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val id = 0
            val userName = "jfoe"
            val image = "sdsdsdsds"
            val name = "Joe"
            val lastName = "Foe"
            val description =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis vel neque libero. Curabitur non posuere arcu, sit amet euismod nunc. Aenean mi est, tempus et enim vel, tincidunt pharetra felis. Integer ultricies imperdiet ante. Donec efficitur faucibus felis a egestas. Donec metus lectus, eleifend ut augue id, lacinia mattis sem. Quisque et auctor sapien. Nullam condimentum lectus odio, in porta dolor tincidunt nec. Proin at pharetra justo. Aliquam hendrerit elementum dapibus. Phasellus at lorem vel nulla porta pharetra.\n" +
                        "\n" +
                        "Etiam efficitur felis quam, vitae ullamcorper erat aliquam scelerisque. Suspendisse fringilla risus eget urna iaculis finibus. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Proin libero arcu, semper non eros quis, malesuada pretium dui. Integer non viverra magna. Phasellus mattis justo odio, vel dignissim tellus pharetra quis. Cras vel nisi ut ante vehicula tincidunt non eu augue. Integer hendrerit elementum ante eu eleifend. Nam vel arcu orci. Duis sem augue, euismod pulvinar dolor elementum, volutpat auctor nunc. Sed sed nulla dolor.\n" +
                        "\n" +
                        "Fusce est mauris, tincidunt eget volutpat ac, maximus eget sapien. In tincidunt maximus malesuada. Nam tempus mattis justo at scelerisque. Maecenas convallis, nisl id cursus ullamcorper, augue sapien feugiat lectus, in pharetra mauris justo vel ipsum. In ornare at ligula vitae egestas. Curabitur at orci a orci convallis dignissim nec commodo nunc. Etiam quis posuere libero. Maecenas ornare sem sed dictum elementum. In venenatis maximus felis ac facilisis. Phasellus accumsan volutpat quam eget dapibus. Etiam consequat a urna eu tempor. Donec ultricies orci id iaculis hendrerit.\n" +
                        "\n" +
                        "Ut eu orci sed sapien imperdiet pretium in volutpat enim. Vestibulum luctus pharetra tincidunt. Morbi feugiat justo euismod, vehicula magna ut, consequat mi. Cras rutrum ante vel eleifend vehicula. Sed at molestie urna, vel gravida quam. Fusce sit amet velit ut tortor ultrices ullamcorper at placerat dolor. Vestibulum eu dapibus massa.\n" +
                        "\n" +
                        "Maecenas dui neque, vulputate at arcu ut, sagittis aliquet purus. Nunc elit ex, auctor id maximus a, luctus sit amet arcu. Nulla pellentesque ipsum sit amet elit venenatis tincidunt. Aliquam faucibus congue nibh nec porttitor. Praesent vestibulum, tortor imperdiet tincidunt finibus, orci orci pretium est, non venenatis nulla quam ac odio. Duis quis leo purus. Pellentesque eu quam vitae tellus sagittis posuere. Vivamus semper consequat massa. Morbi id tortor felis. Fusce ultricies sit amet nibh interdum pellentesque. Aenean imperdiet luctus turpis vel maximus. Integer ut cursus quam, ac mattis tortor."

            val contentValues = ContentValues()
            contentValues.put("id", id)
            contentValues.put("userName", userName)
            contentValues.put("image", image)
            contentValues.put("name", name)
            contentValues.put("lastName", lastName)
            contentValues.put("description", description)
            db.insert("User", SQLiteDatabase.CONFLICT_REPLACE, contentValues)
        }
    }).build()

}