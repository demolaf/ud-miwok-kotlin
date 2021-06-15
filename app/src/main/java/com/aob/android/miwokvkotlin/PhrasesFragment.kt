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

class PhrasesFragment : Fragment() {

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
            add(Word("Where are you going?", "minto wuksus",
                    R.raw.phrase_where_are_you_going))
            add(Word("What is your name?", "tinnә oyaase'nә",
                    R.raw.phrase_what_is_your_name))
            add(Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is))
            add(Word("How are you feeling?", "michәksәs?",
                    R.raw.phrase_how_are_you_feeling))
            add(Word("I’m feeling good.", "kuchi achit", R.raw
                    .phrase_im_feeling_good))
            add(Word("Are you coming?", "әәnәs'aa?", R.raw
                    .phrase_are_you_coming))
            add(Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw
                    .phrase_yes_im_coming))
            add(Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming))
            add(Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go))
            add(Word("Come here.", "әnni'nem", R.raw.phrase_come_here))
        }

        val adapter = WordAdapter(this.requireActivity(), words, R.color
                .category_phrases)

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