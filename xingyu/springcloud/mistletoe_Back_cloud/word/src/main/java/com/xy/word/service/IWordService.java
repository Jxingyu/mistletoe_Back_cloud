package com.xy.word.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.model.Word;

import java.util.Vector;

public interface IWordService extends IService<Word> {
    Vector<Word> findWordList(Word word);

    int findTotalCount(Word word);

    int updateNumber(Word word);

    int updateCollect(Word word);

    int wordAdd(Word word);

    Vector<Word> findCollectAll(Word word);
}
