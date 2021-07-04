package com.moonjew.mochiclicker.io;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.files.FileHandle;

public class SoundEffect {

    Sound sound;
    // Effects
    private float volume; //0 - 1
    private float pitch; // 0.5 - 2
    private float pan; // -1 - 1

    public SoundEffect(String fileName, float volume, float pitch, float pan) {
        this.volume = volume;
        this.pitch = pitch;
        this.pan = pan;
        this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/" + fileName));
    }
    public void play(){
        sound.play(volume, pitch, pan);
    }
    public void pause(){
        sound.pause();
    }


}
