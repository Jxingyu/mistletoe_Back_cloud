package com.xy.word.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.model.Word;
import com.xy.word.mapper.WordMapper;
import com.xy.word.service.IWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Vector;

@Service
public class WordServiceImpl extends ServiceImpl<WordMapper, Word> implements IWordService {
    @Autowired
    WordMapper wordMapper;

    @Override
    public Vector<Word> findWordList(Word word) {
        Vector<Word> words = wordMapper.findWordList(word);
        return words;
    }

    @Override
    public int findTotalCount(Word word) {
        return wordMapper.findTotalCount(word);
    }

    @Override
    public int updateNumber(Word word) {
        return wordMapper.updateNumber(word);
    }

    @Override
    public int updateCollect(Word word) {
        return wordMapper.updateCollect(word);
    }

    @Override
    public int wordAdd(Word word) {
        return wordMapper.wordAdd(word);
    }

    @Override
    public Vector<Word> findCollectAll(Word word) {
        Vector<Word> words = wordMapper.findCollectAll(word);
        return words;
    }
}
