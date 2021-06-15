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

class ColorsFragment : Fragment() {

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
            add(Word("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red))
            add(Word("green", "chokokki", R.drawable.color_green,
                    R.raw.color_green))
            add(Word("brown", "ṭakaakki", R.drawable.color_brown,
                    R.raw.color_brown))
            add(Word("gray", "ṭopoppi", R.drawable.color_gray,
                    R.raw.color_gray))
            add(Word("black", "kululli", R.drawable.color_black,
                    R.raw.color_black))
            add(Word("white", "kelelli", R.drawable.color_white,
                    R.raw.color_white))
            add(Word("dusty yellow", "ṭopiisә",
                    R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow))
            add(Word("mustard yellow", "chiwiiṭә",
                    R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow))
        }

        val adapter = WordAdapter(this.requireActivity(), words, R.color
                .category_colors)

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