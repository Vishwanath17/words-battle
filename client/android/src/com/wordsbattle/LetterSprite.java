﻿package com.wordsbattle;

import static com.wordsbattle.WordsBattleActivity.SCALE;
import static com.wordsbattle.WordsBattleActivity.SPRITE_SIZE;
import static com.wordsbattle.WordsBattleActivity.client;
import static com.wordsbattle.WordsBattleActivity.fieldGrid;
import static com.wordsbattle.WordsBattleActivity.letterIsPressed;
import static com.wordsbattle.WordsBattleActivity.myWord;
import static com.wordsbattle.WordsBattleActivity.pressedLetter;
import static com.wordsbattle.WordsBattleActivity.pressedLetterX;
import static com.wordsbattle.WordsBattleActivity.pressedLetterY;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.apache.log4j.Logger;

import com.wordsbattle.common.domain.Letter;
import com.wordsbattle.util.TexturesBase;

public class LetterSprite extends Sprite {
    private final static Logger LOGGER = Logger.getLogger(LetterSprite.class);
    private Letter letter;
    public WordSprite word;
    public boolean available;
    
    public Letter getLetter() {
        return letter;
    }
    
    public LetterSprite(Letter pLetter, float pX, float pY, TexturesBase pTexBase) {
        super(pX - (SPRITE_SIZE - SPRITE_SIZE * SCALE) * 0.5f, 
              pY - (SPRITE_SIZE - SPRITE_SIZE * SCALE) * 0.5f, 
              pTexBase.getCharTexture(pLetter.getValue())); 
        this.letter = pLetter;
        this.available = true;
        this.word = null;
        this.setScale(SCALE);
    }

    // Метод, обрабатывающий нажатия на спрайт буквы.
    @Override
    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
        // Если буква в пуле и доступна для нажатия и если никакая буква ещё не нажата.
        // Случай перемещения буквы из пула в слово. Место в сетке при этом должно освободиться.
        if (pSceneTouchEvent.isActionUp() && this.available && !letterIsPressed && this.word == null) {
            int firstEmptyPlace = myWord.getFirstEmptyPlaceIndex();
                
            if (firstEmptyPlace != -1) {
                this.setPosition(myWord.cells.get(firstEmptyPlace).getX() + (SPRITE_SIZE - SPRITE_SIZE * SCALE) * 0.5f, 
                                 myWord.cells.get(firstEmptyPlace).getY() + (SPRITE_SIZE - SPRITE_SIZE * SCALE) * 0.5f);
                myWord.setLetter(firstEmptyPlace, this.letter);
            }
            
            // Удаляем букву из сетки.
            LOGGER.info("deleting letter: " + letter);
            fieldGrid.deleteLetter(this);
            this.word = myWord;
            // TODO(acbelter): здесь нужно как-то вызывать client.pickLetter(letter);
            client.pickLetter(letter);
            // TODO(acbelter): Чёрт знает, почему, если тут поставить return true, то всё перестаёт работать!
            // TODO(acbelter): Сделать, чтобы спрайты перемещались по самому верхнему слою.
            //return true;
        } 
            
//        // Случай нажатия на ту же букву.
//        if (this.available && letterIsPressed && this == pressedLetter) {
//            letterIsPressed = false;
//            pressedLetter = null;
//            this.setAlpha(1);
//            return false;
//        }
//        
//        // Случай нажатия на другую букву. Выделение переносится на другую букву.
//        if (this.available && letterIsPressed && this != pressedLetter) {
//            pressedLetter.setAlpha(1);
//            pressedLetter = this;
//            pressedLetter.setAlpha(0);
//            return false;
//        }
        
        // Случай перемещения буквы в слове.
        else if (pSceneTouchEvent.isActionUp() && this.available && !letterIsPressed && this.word == myWord) {
            letterIsPressed = true;
            pressedLetter = this;
            
            this.setColor(255f/255f, 0f/255f, 0f/255f, 0f);
                
            pressedLetterX = this.getX() + (SPRITE_SIZE - SPRITE_SIZE * SCALE) * 0.5f;
            pressedLetterY = this.getY() + (SPRITE_SIZE - SPRITE_SIZE * SCALE) * 0.5f;
                
            // Удаляем символ из слова.
            for (int i = 0; i < word.cells.size(); i++) {
                // Здесь НУЖНО использовать координаты центров.
                if (word.cells.get(i).containPoint(pressedLetterX + SPRITE_SIZE * SCALE * 0.5f,
                                                   pressedLetterY + SPRITE_SIZE * SCALE * 0.5f)) {
                    word.setLetter(i, null);
                    break;
                }
            }
            //return true;
            
        }

        return true;
    }
    
    @Override
    public void setPosition(float pX, float pY) {
	    // TODO(acbelter): кажется здесь лишние поправки к коррдинатам...
        super.setPosition(pX - (SPRITE_SIZE - SPRITE_SIZE * SCALE) * 0.5f, pY - (SPRITE_SIZE - SPRITE_SIZE * SCALE) * 0.5f);
    }
}