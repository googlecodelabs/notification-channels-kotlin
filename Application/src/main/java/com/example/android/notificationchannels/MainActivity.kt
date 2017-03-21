/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.notificationchannels

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

/** Display main screen for sample. Displays controls for sending test notifications.  */
class MainActivity : Activity() {

    /*
     * A view model for interacting with the UI elements.
     */
    private lateinit var mUIModel: MainUi

    /*
     * A helper class for initializing notification channels and sending notifications.
     */
    private lateinit var mNotificationHelper: NotificationHelper

    companion object {
        private val TAG = MainActivity::class.java.simpleName

        private val NOTIFICATION_FOLLOW = 1100
        private val NOTIFICATION_UNFOLLOW = 1101
        private val NOTIFICATION_DM_FRIEND = 1200
        private val NOTIFICATION_DM_COWORKER = 1201
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mNotificationHelper = NotificationHelper(this)
        mUIModel = MainUi()
    }

    /**
     * Send activity notifications.

     * @param id The ID of the notification to create
     */
    private fun sendNotification(id: Int) {
        when (id) {
            NOTIFICATION_FOLLOW -> mNotificationHelper.notify(id, mNotificationHelper.getNotificationFollower(
                    getString(R.string.follower_title_notification),
                    getString(R.string.follower_added_notification_body,
                            mNotificationHelper.randomName)))

            NOTIFICATION_UNFOLLOW ->  mNotificationHelper.notify(id, mNotificationHelper.getNotificationFollower(
                    getString(R.string.follower_title_notification),
                    getString(R.string.follower_removed_notification_body,
                            mNotificationHelper.randomName)))

            NOTIFICATION_DM_FRIEND ->  mNotificationHelper.notify(id, mNotificationHelper.getNotificationDM(
                    getString(R.string.direct_message_title_notification),
                    getString(R.string.dm_friend_notification_body,
                            mNotificationHelper.randomName)))

            NOTIFICATION_DM_COWORKER ->  mNotificationHelper.notify(id, mNotificationHelper.getNotificationDM(
                    getString(R.string.direct_message_title_notification),
                    getString(R.string.dm_coworker_notification_body,
                            mNotificationHelper.randomName)))
        }

    }

    /** Send Intent to load system Notification Settings for this app.  */
    private fun goToNotificationSettings() {
        val i = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        i.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        startActivity(i)
    }

    /**
     * Send intent to load system Notification Settings UI for a particular channel.

     * @param channel Name of channel to configure
     */
    private fun goToNotificationChannelSettings(channel: String) {
        // Skeleton method to be completed later
    }

    /**
     * View model for interacting with Activity UI elements. (Keeps core logic for sample separate.)
     */
    internal inner class MainUi : View.OnClickListener {

        init {
            // Setup the buttons
            follow_button.setOnClickListener(this)
            un_follow_button.setOnClickListener(this)
            follower_channel_settings_button.setOnClickListener(this)
            friend_dm_button.setOnClickListener(this)
            coworker_dm_button.setOnClickListener(this)
            dm_channel_settings_button.setOnClickListener(this)
            go_to_settings_button.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            when (view.id) {
                R.id.follow_button -> sendNotification(NOTIFICATION_FOLLOW)
                R.id.un_follow_button -> sendNotification(NOTIFICATION_UNFOLLOW)
                R.id.follower_channel_settings_button -> goToNotificationChannelSettings("")
                R.id.friend_dm_button -> sendNotification(NOTIFICATION_DM_FRIEND)
                R.id.coworker_dm_button -> sendNotification(NOTIFICATION_DM_COWORKER)
                R.id.dm_channel_settings_button -> goToNotificationChannelSettings("")
                R.id.go_to_settings_button -> goToNotificationSettings()
                else -> Log.e(TAG, getString(R.string.error_click))
            }
        }
    }


}
