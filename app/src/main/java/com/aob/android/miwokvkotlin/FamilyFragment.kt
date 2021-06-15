package com.aob.android.miwokvkotlin

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.aob.android.miwokvkotlin.databinding.WordListBinding

class FamilyFragment : Fragment() {

    /**
     * Handles playback of all the sound files
     */
    private var mMediaPlayer : MediaPlayer? = null

    /**
     * Handles audio focus when playing a sound file
     */
    private lateinit var mAudioManager: AudioManager

    private var mCompletionListener : MediaPlayer
    .OnCompletionListener? = MediaPlayer.OnCompletionListener {
        releaseMediaPlayer()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //inflater.inflate(R.layout.word_list, container, false)
        val binding = DataBindingUtil.inflate<WordListBinding>(inflater, R
                .layout.word_list, container, false)

        val words: ArrayList<Word> = ArrayList<Word>()

        mAudioManager = requireActivity().getSystemService(Context
                .AUDIO_SERVICE) as AudioManager

        words.apply {
            add(Word("father", "әpә", R.drawable.family_father,
                    R.raw.family_father))
            add(Word("mother", "әṭa", R.drawable.family_mother,
                    R.raw.family_mother))
            add(Word("son", "angsi", R.drawable.family_son,
                    R.raw.family_son))
            add(Word("daughter", "tune", R.drawable.family_daughter,
                    R.raw.family_daughter))
            add(Word("older brother", "taachi",
                    R.drawable.family_older_brother, R.raw.family_older_brother))
            add(Word("younger brother", "chalitti",
                    R.drawable.family_younger_brother, R.raw.family_younger_brother))
            add(Word("older sister", "teṭe",
                    R.drawable.family_older_sister, R.raw.family_older_sister))
            add(Word("younger sister", "kolliti",
                    R.drawable.family_younger_sister, R.raw.family_younger_sister))
            add(Word("grandmother", "ama",
                    R.drawable.family_grandmother, R.raw.family_grandmother))
            add(Word("grandfather", "paapa",
                    R.drawable.family_grandfather, R.raw.family_grandfather))
        }

        val adapter = WordAdapter(this.requireActivity(), words, R.color
                .category_family)

        val listView: ListView = binding.list

        listView.adapter = adapter


        // Set a click listener to play the audio when the list item is
        // clicked on
        listView.onItemClickListener = AdapterView.OnItemClickListener {
            parent,
            view, position, id -> // Release the media player if it currently exists because we
            // are about to play a different sound file.
            releaseMediaPlayer()
            val word = words[position]
            Log.v("NumbersActivity", "Current word: $word") // this

            // is the same as this Log.v("NumbersActivity", "Current
            // word: " + word.toString());

            // Get the {@link Word} object at the given position the
            // user clicked on
            // Create and setup the {@link MediaPlayer} for the audio
            // resource associated with the current word


            // Request audio focus for playback
            val result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,  // Use the music stream.
                    AudioManager.STREAM_MUSIC,  // Request temporary focus.
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                // We now have audio focus
                mMediaPlayer = MediaPlayer.create(activity,
                        word.audioResourceId)

                // Start the audio file
                mMediaPlayer!!.start()

                mMediaPlayer!!.setOnCompletionListener(mCompletionListener)
                // Setup a listener on the media player, so that we can stop
                // and release the media player once the sounds has finished
                // playing.
            }
            Toast.makeText(activity, "Working",
                    Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        releaseMediaPlayer()
    }

    private val mOnAudioFocusChangeListener : AudioManager.OnAudioFocusChangeListener
            = AudioManager.OnAudioFocusChangeListener {
        fun onAudioFocusChange(focusChange: Int) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager
                            .AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer?.pause()
                mMediaPlayer?.seekTo(0)
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer?.start()
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer()
            }
        }
    }

    private fun releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer?.release()

            mMediaPlayer = null
        }

        mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener)
    }
}