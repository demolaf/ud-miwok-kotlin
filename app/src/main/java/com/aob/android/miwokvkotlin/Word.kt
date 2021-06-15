package com.aob.android.miwokvkotlin

class Word {

    // Fields or States or Instance Variables
    /**
     * Constant value that represents no image was provided for this word
     */
    private val NO_IMAGE_PROVIDED = -1

    /**
     * Default translation for the word
     */
    private var _mDefaultTranslation: String? = null
    val defaultTranslation: String?
        get() = _mDefaultTranslation


    /**
     * Miwok translation for the word
     */
    private var _mMiwokTranslation: String? = null
    val miwokTranslation: String?
        get() = _mMiwokTranslation

    /**
     * Drawable resource ID
     */
    private var _mImageResourceId = NO_IMAGE_PROVIDED
    val imageResourceId: Int
        get() = _mImageResourceId

    /**
     * Audio resource ID for the word
     */
    private var _mAudioResourceId = 0
    val audioResourceId: Int
        get() = _mAudioResourceId


    // the "init{}" block is used to initialize and execute any code when an
    // an object of this class is instantiated
    /*init {
        this._mMiwokTranslation = miwokTranslation
        this._mDefaultTranslation = defaultTranslation
        if (imageResourceId != null) {
            this._mImageResourceId = imageResourceId
        }
        //_mImageResourceId = (null ?: imageResourceId) as Int
        this._mAudioResourceId = audioResourceId

    }*/


    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is
     * already familiar (such as English)
     * @param miwokTranslation   is the word in the Miwok language
     * @param audioResourceId    is the resource ID for the audio file
     * associated with the word
     */
    constructor(defaultTranslation: String?, miwokTranslation: String?,
             audioResourceId: Int) {
        _mDefaultTranslation = defaultTranslation
        _mMiwokTranslation = miwokTranslation
        _mAudioResourceId = audioResourceId
        _mImageResourceId = imageResourceId

    }

    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is
     * already familiar (such as English)
     * @param miwokTranslation   is the word in the Miwok language
     * @param imageResourceId    is the drawable resource ID for the image
     * associated with the word
     * @param audioResourceId    is the resource ID for the audio file
     * associated with the word
     */
    constructor(defaultTranslation: String?, miwokTranslation: String?,
             imageResourceId: Int, audioResourceId: Int) {
        _mDefaultTranslation = defaultTranslation
        _mMiwokTranslation = miwokTranslation
        _mImageResourceId = imageResourceId
        _mAudioResourceId = audioResourceId
    }

    fun hasImage(): Boolean {
        return _mImageResourceId != NO_IMAGE_PROVIDED
    }

}